package com.dbc.curriculocv.security;

import com.dbc.curriculocv.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    private static final String CLAIM_PERMISSOES = "permissoes";

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Usuario usuario) {
        Date generateDate = new Date();

        //expiracao
        Date exp = new Date(generateDate.getTime() + Long.parseLong(expiration));

        List<String> permissoes = new ArrayList<>(Collections.singletonList("ROLE_CADASTRADOR"));

        String jwtToken = Jwts.builder()
                .setIssuer("curriculo-cv")
                .claim(CLAIM_PERMISSOES, permissoes)
                .setSubject(usuario.getIdUsuario().toString())
                .setIssuedAt(generateDate)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return TOKEN_PREFIX + " " + jwtToken;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            String user = claims.getSubject();
            List<String> permissoes = (List<String>) claims.get(CLAIM_PERMISSOES);

            List<GrantedAuthority> grantedAuthorities = permissoes.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        grantedAuthorities
                );
            }
        }
        return null;
    }
}
