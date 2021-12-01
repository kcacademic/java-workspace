package com.kchandrakant.learning;

import java.security.GeneralSecurityException;
import java.text.ParseException;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SymmetricJweHandler {

    private final Logger log = LogManager.getLogger(getClass());

    @Autowired
    private MyKeyGenerator myKeyGenerator;

    @Autowired
    private MyKeyLoader myKeyLoader;

    public String getJweToken() {

        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();

        String jweString = null;
        try {
            JWSSigner signer = new RSASSASigner(myKeyLoader.loadPrivateKey());
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.RS256);
            SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet.build());
            signedJWT.sign(signer);

            JWEHeader jweHeader = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A256GCM);
            JWEObject jweObject = new JWEObject(jweHeader, new Payload(signedJWT));
            jweObject.encrypt(new DirectEncrypter(myKeyLoader.loadSecretKey()));
            jweString = jweObject.serialize();
        } catch (JOSEException | GeneralSecurityException e) {
            log.error("Error creating a valid JWE Token: " + e.getMessage());
        }

        return jweString;

    }

    public boolean validateJweToken(String jweToken) {

        try {
            JWEObject jweObject = JWEObject.parse(jweToken);
            jweObject.decrypt(new DirectDecrypter(myKeyLoader.loadSecretKey()));
            SignedJWT signedJWT = SignedJWT.parse(jweObject.getPayload().toString());
            JWSVerifier verifier = new RSASSAVerifier(myKeyLoader.loadPublicKey());
            signedJWT.verify(verifier);

            log.info("Payload: " + signedJWT.getJWTClaimsSet());

        } catch (ParseException | JOSEException | GeneralSecurityException e) {
            log.error("Error validating the JWE Token: " + e.getMessage());
            throw new AuthenticationException(e.getMessage());
        }

        return true;

    }

}
