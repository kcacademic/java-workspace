package com.kchandrakant.learning;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class MyKeyLoader {

    private String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0q2OzAeZUstKDKI+sMBpNmlpmYyaReLUModL1Mlr4brWRaQRxSecmRKchKkGq8ooX4amcmDyp/cYZd6uvGnmUMBXf4UuaX+epXfiMmIPk5xip3hHbSztiBXngJIJxCLmhMqHmjlvQzeJUDTvU+vHb4yDNvHsrKfcgvImV5/5GyS7ZVeYnCHd97mceLNt662qxOtFmiGHWXhMRADijS4lzXmN+vNbGiofu4gd2vMgjdGRMsNwbNd2ayLhXExcPJMvBjj5ldnTtJVaxlaULWharquerNOt5MUGgvfupIHg2gbwACZg99NRlhxzWSEpL/mgQrc48UudHUj7yySLKAR7qQIDAQAB";

    private String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDSrY7MB5lSy0oMoj6wwGk2aWmZjJpF4tQyh0vUyWvhutZFpBHFJ5yZEpyEqQaryihfhqZyYPKn9xhl3q68aeZQwFd/hS5pf56ld+IyYg+TnGKneEdtLO2IFeeAkgnEIuaEyoeaOW9DN4lQNO9T68dvjIM28eysp9yC8iZXn/kbJLtlV5icId33uZx4s23rrarE60WaIYdZeExEAOKNLiXNeY3681saKh+7iB3a8yCN0ZEyw3Bs13ZrIuFcTFw8ky8GOPmV2dO0lVrGVpQtaFquq56s063kxQaC9+6kgeDaBvAAJmD301GWHHNZISkv+aBCtzjxS50dSPvLJIsoBHupAgMBAAECggEBAJrPimUHGHWsiIdsGaEMYsPnBgAdP6gnHELdBK4iLLI89wNJov07NGmPMZCSHCLUdIJtkZXvA6DFhBonXkN5lhK0pBdCqG5BpzR0ObVINaYEfsINMn9ZSlp7rhfeOGr2MmpV4PtFRSshfjeADnWehnjDyg/wCjh9SR64DdDJ7uUv7QMt/VLq1x2wBPWjPwrze86Vt5jQEyTdXiABv9EsAkp0SYfi+a0nMkA2d/pBvAQjx0fBBO1kAFztekBC+H6hiHUrGodW5YmXtx8cWnLPI2wOwOZ2zvSp7YDY+lHFnMfcGRh8YN0vZW8rzUsEdnoZFAThRoPTqmkk8DvMy9//kNkCgYEA8/ePZCxzJB/19sf+hadONwim8md54cLJnYYu/xEoLx5obm5elO6BFBbQOz5SKjmzgZRqwfUn8kDp8zYY7Z6dgbvIP2Va0PnKUD+VuEC8yj5QIU6E+Y27+pKSievJ7Fln1XEJoMHrvlS97ISCDZuLlQHnn6VspbIXgVIAPDnfsIcCgYEA3RGsr/ixJljLd/yNSkIVkY6QWeHjR7S5iEIW00bjGu91uF4r1MnVbNzRW5CCm3tL1VwGQty5XM0yNG0LOs+evGfBEowBh/Ef+//Gxcs26YwVduXaJwxCEvOrBlGn0DK1O9q0zvWB1mQub+o58zgQc+ajCLiW51xP+S1zEciFbk8CgYEA6kIvFMA9u1VZJ6K4OfwBdYTAUYiclvQkBjb/T3pY/I2VN1yRQy6VEQYq4ogf+122iNlLfXx64/oC6r9qRliE4zq9uGoJPxprcozrpaW74fLdnG2576px8tPlE7wuAt2Cf4fv98XdKMBUMhS2kHxoFidjEn+UiLIuMcXcZfgoW2cCgYB+XPz9tUTla3oagsTsfmu/wOLoTcNRmI84wLY1XF6yZdA/a6zYO+qACJSvJyJQ56BIPArkKzDVSAF7kB0MJ+NPRGtLQ7YYU475x5GCPA+OXeFjDi2thkc6eAf5W7F1w5ZuuSwC5LirqKYWmKc5TIGzDlGAyaqQYubfwsf0kU8b5wKBgGFbzpTEzZdzgKjSf24OEtz2X9cjZeqtGOh95G/mBmYOCukrDZKI8rwTw4vJkcR6cBW7f/eoepaGZrXpGAaFFLI1qjwdqvs34KLgpr5bZS2F9I+4s6tlNmcP/W36saZCv1zfx6SYM1kWtWdW/vH1e3RgMTvnUojvYMBnAyXshSW3";

    private String SECRET_KEY = "cy1wk7jGZOa3PiwHwgCwz9KYwGgxfa8KZ3voYrEvRRI";

    public RSAPrivateKey loadPrivateKey() throws GeneralSecurityException {
        PKCS8EncodedKeySpec keySpec =
                new PKCS8EncodedKeySpec(
                        Base64.getDecoder().decode(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8)));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey)kf.generatePrivate(keySpec);
    }

    public RSAPublicKey loadPublicKey() throws GeneralSecurityException {
        byte[] data = Base64.getDecoder().decode(PUBLIC_KEY.getBytes(StandardCharsets.UTF_8));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return (RSAPublicKey)fact.generatePublic(spec);
    }

    public SecretKey loadSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
    }

    public static void main(String[] args) throws GeneralSecurityException {

        MyKeyLoader myKeyLoader = new MyKeyLoader();
        System.out.println(myKeyLoader.loadPrivateKey());
        System.out.println(myKeyLoader.loadPublicKey());
        System.out.println(myKeyLoader.loadSecretKey());

    }
}
