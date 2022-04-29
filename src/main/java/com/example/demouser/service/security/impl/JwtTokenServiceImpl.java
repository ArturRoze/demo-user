package com.example.demouser.service.security.impl;

import com.example.demouser.config.security.JwtTokenConfig;
import com.example.demouser.model.domain.user.JwtUserDetails;
import com.example.demouser.model.domain.capability.UserCapabilityName;
import com.example.demouser.model.domain.user.UserRole;
import com.example.demouser.model.dto.authentication.AuthUserDto;
import com.example.demouser.service.security.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    private static final String ROLE_PREFIX = "ROLE_";

    private final JwtTokenConfig jwtTokenConfig;

    @Autowired
    public JwtTokenServiceImpl(JwtTokenConfig jwtTokenConfig) {
        this.jwtTokenConfig = jwtTokenConfig;
    }

    @Override
    public String generate(AuthUserDto authUserDto) {
        return Jwts.builder()
                .setSubject(authUserDto.getLogin())
                .claim("username", authUserDto.getLogin())
                .claim("role", authUserDto.getRole())
                .claim("capabilities", authUserDto.getCapabilities())
                .setExpiration(buildExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, buildDeserializeKey())
                .compact();
    }

    @Override
    public JwtUserDetails parse(String token) {
        Claims claims = getAllClaimsFromToken(token);

        String login = claims.getSubject();
        UserRole role = UserRole.valueOf(claims.get("role", String.class));
        Set<UserCapabilityName> capabilities = new HashSet<>(claims.get("capabilities", ArrayList.class));
        Collection<SimpleGrantedAuthority> grantedAuthorities = extractedGrantedAuthorities(role, capabilities);

        return new JwtUserDetails(login, "", grantedAuthorities, role, capabilities);
    }

    @Override
    public boolean valid(String token) {
        return !isTokenExpired(token);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(buildDeserializeKey()).parseClaimsJws(token).getBody();
    }

    private Collection<SimpleGrantedAuthority> extractedGrantedAuthorities(UserRole role, Set<UserCapabilityName> capabilities) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.name()));

        capabilities.forEach(userCapabilityName -> authorities.add(new SimpleGrantedAuthority(userCapabilityName.name())));

        return authorities;
    }

    private Date buildExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtTokenConfig.getExpires().toMillis());
    }

    private Boolean isTokenExpired(String token) {
        Claims claims = getAllClaimsFromToken(token);
        final Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    private Key buildDeserializeKey() {
        return new SecretKeySpec(
                Base64.getEncoder().encode(jwtTokenConfig.getSecret().getBytes(UTF_8)),
                SIGNATURE_ALGORITHM.getJcaName()
        );
    }

}
