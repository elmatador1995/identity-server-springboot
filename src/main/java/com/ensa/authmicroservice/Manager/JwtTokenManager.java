package com.ensa.authmicroservice.Manager;

import com.ensa.authmicroservice.Collections.JwtTokenUsersEntity;
import com.ensa.authmicroservice.Collections.UserEntity;
import com.ensa.authmicroservice.Dto.*;

import java.util.*;
import java.util.function.Function;

import com.ensa.authmicroservice.Interfaces.IJwtRepository;
import com.ensa.authmicroservice.Services.AccountService;
import com.ensa.authmicroservice.Services.ClaimsService;
import com.ensa.authmicroservice.Services.GlobalConfigurationService;
import com.ensa.authmicroservice.Services.JwtTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;

import static java.lang.Long.parseLong;


@Service
public class JwtTokenManager {
    @Autowired
    private GlobalConfigurationService _globalConfigService;
    @Autowired
    private ClaimsService _ClaimsService;
    @Autowired
    private JwtTokenService _jwtTokenService;
    @Autowired
    private AccountService _accountService;



    //generate token for user
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
         Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims = new DefaultClaims();
        try{
            String JWT_TOKEN_SECRET = _globalConfigService.getConfigByKey("JWT_TOKEN_SECRET").getConfigurationValue();
            SecretKeySpec secretKeySpec = new SecretKeySpec(JWT_TOKEN_SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());
            claims = Jwts.parser().setSigningKey(secretKeySpec).parseClaimsJws(token).getBody();
        }catch(ClaimJwtException ex){
            System.out.println(ex);
            claims = ex.getClaims();
        }
        return claims;
    }

    private String getClaimFromTokenByClaimName(String token, String ClaimName) {
        return getAllClaimsFromToken(token).get(ClaimName,String.class);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public JwtTokenUsersDto generateJwtToken(UserProfilDto _dto, Boolean SaveToken) {
        JwtTokenUsersDto jwtTokendto = new JwtTokenUsersDto();
        String ENABLE_JWT_TOKEN_REFRESH = _globalConfigService.getConfigByKey("ENABLE_JWT_TOKEN_REFRESH").getConfigurationValue();
        //Prepare Claims
        List<ClaimsDto> getClaimsByUserId = _ClaimsService.getClaimsByUserId(_dto.getUid());
        Map<String, Object> claims = new HashMap<>();
        String refreshToken = "";
        if(ENABLE_JWT_TOKEN_REFRESH.equals("true")){
            refreshToken = UUID.randomUUID().toString();
            claims.put("refresh_token", refreshToken);
        }
        for (ClaimsDto item : getClaimsByUserId){
            claims.put(item.getClaimType(),item.getClaimValue());
        }

        String jwtToken = doGenerateToken(claims, _dto.getUserName(), refreshToken, SaveToken);

        if(jwtToken.isBlank())
            return jwtTokendto;

        jwtTokendto.setRefreshToken(refreshToken);
        jwtTokendto.setJwtToken(jwtToken);
        return jwtTokendto;
    }

    private String doGenerateToken(Map<String, Object> claims, String username, String refreshToken, boolean SaveToken) {
        try{
            String JWT_TOKEN_VALIDITY = _globalConfigService.getConfigByKey("JWT_TOKEN_VALIDITY").getConfigurationValue();
            String JWT_TOKEN_SECRET = _globalConfigService.getConfigByKey("JWT_TOKEN_SECRET").getConfigurationValue();
            SecretKeySpec secretKeySpec = new SecretKeySpec(JWT_TOKEN_SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());

            String jwtToken = Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + parseLong(JWT_TOKEN_VALIDITY) * 60000))
                    .signWith(SignatureAlgorithm.HS512, secretKeySpec).compact();

            //Insert JwtToken
            if(SaveToken){
                if(!_jwtTokenService.InsertJwtToken(jwtToken, username, refreshToken))
                    return "";
            }


            return jwtToken;
        }catch (Exception ex){
            System.out.println(ex);
        }
        return "";
    }

    public JwtTokenValidationDto isValidateToken(String token) {
        JwtTokenValidationDto _dto = new JwtTokenValidationDto();
        String username = getUsernameFromToken(token);
        String[] chunks = token.split("\\.");
        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];
        String Payload = chunks[1];

        JwtTokenUsersDto getJwtTokenStored = _jwtTokenService.IsExistJwtTokenByUserId(token,username);
        if(getJwtTokenStored == null){
            _dto.setStatusLabel("Invalid Token");
            _dto.setStatusCode("001");
            return _dto;
        }

        //validate
        String JWT_TOKEN_SECRET = _globalConfigService.getConfigByKey("JWT_TOKEN_SECRET").getConfigurationValue();
        String ENABLE_JWT_TOKEN_REFRESH = _globalConfigService.getConfigByKey("ENABLE_JWT_TOKEN_REFRESH").getConfigurationValue();

        SecretKeySpec secretKeySpec = new SecretKeySpec(JWT_TOKEN_SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(SignatureAlgorithm.HS512, secretKeySpec);
        if (!validator.isValid(tokenWithoutSignature, signature)){
            _dto.setStatusLabel("Invalid Signature");
            _dto.setStatusCode("001");
            return _dto;
        }


        if(ENABLE_JWT_TOKEN_REFRESH.equals("false") && isTokenExpired(token)){
            _dto.setStatusLabel("Token Expired");
            _dto.setStatusCode("001");
            return _dto;
        }
        else if(ENABLE_JWT_TOKEN_REFRESH.equals("true") && isTokenExpired(token)){
            _dto.setStatusLabel("Token Expired, please use RefreshToken");
            _dto.setStatusCode("002");
            return _dto;
        }
        _dto.setStatusLabel("Valid Signature");
        _dto.setStatusCode("000");
        return _dto;
    }

    public RefreshTokenDto RefreshJwtToken(String RefreshToken){
        RefreshTokenDto _dto = new RefreshTokenDto();
        try{
            JwtTokenUsersDto currentJwtToken = _jwtTokenService.jwtTokenByRefreshToken(RefreshToken);
            if(currentJwtToken == null)
                return null;

            UserProfilDto userProfilDto = _accountService.getUserProfile(currentJwtToken.getUsername());
            JwtTokenUsersDto newJwtTokenDto = generateJwtToken(userProfilDto, false);
            currentJwtToken.setRefreshToken(newJwtTokenDto.getRefreshToken());
            currentJwtToken.setJwtToken(newJwtTokenDto.getJwtToken());
            _jwtTokenService.UpdateJwtToken(currentJwtToken);

            _dto.setAccessToken(newJwtTokenDto.getJwtToken());
            _dto.setRefreshToken(newJwtTokenDto.getRefreshToken());

            return _dto;

        }catch (Exception ex){
            return _dto;
        }
    }
}
