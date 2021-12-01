package com.kchandrakant.learning;

import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

@Component
public class MyKeyGenerator {

    private KeyFactory keyFactory;
    private RSAPublicKeySpec publicKeySpec;
    private RSAPrivateKeySpec privateKeySpec;
    private SecretKey secretKey;

    public MyKeyGenerator() throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        keyFactory = KeyFactory.getInstance("RSA");
        publicKeySpec = keyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
        privateKeySpec = keyFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        secretKey = keyGen.generateKey();
    }

    public RSAPublicKey getPublicKey() throws InvalidKeySpecException {
        return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
    }

    public RSAPrivateKey getPrivateKey() throws InvalidKeySpecException {
        return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

}
