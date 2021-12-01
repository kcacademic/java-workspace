package com.kchandrakant.learning;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;


@Component
public class AsymmetricJweHandler {

    private final Logger log = LogManager.getLogger(getClass());

    @Autowired
    private MyKeyGenerator myKeyGenerator;

    @Autowired
    private MyKeyLoader myKeyLoader;

    public String getJweToken() {

        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();
        // Standard claims
        claimsSet.issuer("test-user");
        claimsSet.subject("JWE-Authentication-Example");
        claimsSet.expirationTime(new Date(new Date().getTime() + 1000 * 60 * 10));
        claimsSet.notBeforeTime(new Date());
        claimsSet.jwtID(UUID.randomUUID().toString());
        // User specified claims
        claimsSet.claim("appId", "230919131512092005");
        claimsSet.claim("userId", "4431d8dc-2f69-4057-9b83-a59385d18c03");
        claimsSet.claim("role", "Admin");
        claimsSet.claim("applicationType", "WEB");
        claimsSet.claim("clientRemoteAddress", "192.168.1.2");
        log.info("Claim Set : \n" + claimsSet.build());

        String jwtString = null;
        try {
            // Create the JWE header and specify:
            // RSA-OAEP as the encryption algorithm
            // 128-bit AES/GCM as the encryption method
            JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);
            // Initialized the EncryptedJWT object
            EncryptedJWT jwt = new EncryptedJWT(header, claimsSet.build());
            // Create an RSA encrypted with the specified public RSA key
            RSAEncrypter encrypter = new RSAEncrypter(myKeyLoader.loadPublicKey());
            // Doing the actual encryption
            jwt.encrypt(encrypter);
            // Serialize to JWT compact form
            jwtString = jwt.serialize();
        } catch (GeneralSecurityException | JOSEException e) {
            log.error("Error creating a valid JWE Token: " + e.getMessage());
        }

        return jwtString;

    }

    public boolean validateJweToken(String jweToken) {

        try {
            EncryptedJWT jwt = EncryptedJWT.parse(jweToken);

            // Create a decrypter with the specified private RSA key
            RSADecrypter decrypter = new RSADecrypter(myKeyLoader.loadPrivateKey());
            // Doing the decryption
            jwt.decrypt(decrypter);

            log.debug("======================== Decrypted payload values ===================================");
            log.debug("Issuer: [ " + jwt.getJWTClaimsSet().getIssuer() + "]");
            log.debug("Subject: [" + jwt.getJWTClaimsSet().getSubject() + "]");
            log.debug("Expiration Time: [" + jwt.getJWTClaimsSet().getExpirationTime() + "]");
            log.debug("Not Before Time: [" + jwt.getJWTClaimsSet().getNotBeforeTime() + "]");
            log.debug("JWT ID: [" + jwt.getJWTClaimsSet().getJWTID() + "]");
            log.debug("Application Id: [" + jwt.getJWTClaimsSet().getClaim("appId") + "]");
            log.debug("User Id: [" + jwt.getJWTClaimsSet().getClaim("userId") + "]");
            log.debug("Role type: [" + jwt.getJWTClaimsSet().getClaim("role") + "]");
            log.debug("Application Type: [" + jwt.getJWTClaimsSet().getClaim("applicationType") + "]");
            log.debug("Client Remote Address: [" + jwt.getJWTClaimsSet().getClaim("clientRemoteAddress") + "]");

        } catch (GeneralSecurityException | ParseException | JOSEException e) {
            log.error("Error validating the JWE Token: " + e.getMessage());
            log.info("Try using this JWE Token: " + getJweToken());
            throw new AuthenticationException(e.getMessage());
        }

        return true;

    }

}
