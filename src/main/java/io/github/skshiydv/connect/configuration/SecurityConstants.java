package io.github.skshiydv.connect.configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecurityConstants {

    public static final long JWT_TOKEN_VALIDITY_SECONDS = 70000;
    public static final SecretKey JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

}
