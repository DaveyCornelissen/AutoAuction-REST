package com.dcorn.api.utils.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dcorn.api.utils.handler.BadRequestHandler;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.dcorn.api.utils.security.SecurityConstants.*;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        System.out.println(token);
        if (token != null) {
            //TODO need to check if te Token is valid.

            DecodedJWT decodeToken = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build().verify(token.replace(TOKEN_PREFIX, ""));

            if(!decodeToken.getIssuer().equals(ISSUER))
                throw new BadRequestHandler("Token issuer is not the same!");

            if(decodeToken.getExpiresAt().before(new Date()))
                throw new BadRequestHandler("Token date is expired!");


      //      JsonArray object = decodeToken.getClaim(TOKEN_CLAIM).as(JsonArray.class);

           // ClaimDetail claimDetail = new Gson().fromJson(decodeToken.getClaim(TOKEN_CLAIM).asString(), ClaimDetail.class);



            if (decodeToken != null) {
                return new UsernamePasswordAuthenticationToken(decodeToken.getSubject(), null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
