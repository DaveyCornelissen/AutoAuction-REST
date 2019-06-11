package com.dcorn.api.utils.security;

class SecurityConstants {
    static final String SECRET = "AutoAuction_Key";
    static final String ISSUER = "AutoAuction.com";
    static final long EXPIRATION_TIME = 864_000_000; // 10 days
    static final String TOKEN_PREFIX = "Bearer ";
    static final String TOKEN_CLAIM = "TokenDetails";
    static final String HEADER_STRING = "Authorization";
    static final String AUTH_URL_SIGNUP = "/api/auth/signUp";
    static final String AUTH_URL_SIGNIN = "/api/auth/signIn";
}
