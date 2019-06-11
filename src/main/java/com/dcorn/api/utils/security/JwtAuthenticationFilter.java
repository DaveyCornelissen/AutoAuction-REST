package com.dcorn.api.utils.security;

import com.auth0.jwt.JWT;
import com.dcorn.api.user.User;
import com.dcorn.api.utils.handler.InternalServerErrorHandler;
import com.dcorn.api.utils.handler.ResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.dcorn.api.utils.security.SecurityConstants.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public static String MakeFakeAuthentication() {
        return JWT.create()
                .withSubject("test")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            if (creds.equals(null))
                throw new InternalServerErrorHandler("something went wrong! No credentials found in the request header! please try again.");

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    ));
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();

        Date now = new Date(System.currentTimeMillis());

        String token = JWT.create()
                .withSubject((principal.getClaimDetail().getEmail()))
                .withIssuedAt(now)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withIssuer(ISSUER)
                .withClaim(TOKEN_CLAIM, new Gson().toJson(principal.getClaimDetail()))
                .sign(HMAC512(SECRET.getBytes()));

        //to get the token to the body instead of the header.
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(new Gson().toJson(new ResponseHandler(HttpStatus.OK, TOKEN_PREFIX + token)));
    }
}

