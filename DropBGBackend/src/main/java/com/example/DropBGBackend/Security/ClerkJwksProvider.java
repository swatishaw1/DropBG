package com.example.DropBGBackend.Security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class ClerkJwksProvider {

    @Value("${clerk.jwks-url}")
    private String jwksUrl;
    private final Map<String, PublicKey> keyCache = new HashMap<>();
    private long lastFetchedTime = 0;
    private static final long CACHE_TTL = 3600000;
    public PublicKey getPublicKey(String keyId) throws Exception{
        if (keyCache.containsKey(keyId) && System.currentTimeMillis() - lastFetchedTime < CACHE_TTL){
            return keyCache.get(keyId);
        }
        refreshKeys();
        return keyCache.get(keyId);
    }

    //If keys are Expired then fetch new keys from Server
    private void refreshKeys() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jwks = mapper.readTree(new URL(jwksUrl));//From JwksUrl we get the key
        JsonNode keys = jwks.get("keys");
        for (JsonNode keyNode: keys){
            String kid = keyNode.get("kid").asText();//Key id
            String kty = keyNode.get("kty").asText();//Key Type
            String alg = keyNode.get("alg").asText();//Key algorithm
            if ("RSA".equals(kty) && "RS256".equals(alg)){
                String n = keyNode.get("n").asText();
                String e = keyNode.get("e").asText();
                PublicKey publicKey = createPublicKey(n,e);//Creating new key
                keyCache.put(kid, publicKey);
            }
        }
        lastFetchedTime = System.currentTimeMillis();
    }

    //It reconstructs an RSA public key from raw values so Spring can verify a JWT signature.
    private PublicKey createPublicKey(String modulus, String exponent) throws Exception{
            byte[] modulusBytes = Base64.getUrlDecoder().decode(modulus);
            byte[] exponentBytes = Base64.getUrlDecoder().decode(exponent);

            BigInteger modulusBigInt = new BigInteger(1, modulusBytes);
            BigInteger exponentBigInt = new BigInteger(1, exponentBytes);
            RSAPublicKeySpec spec = new RSAPublicKeySpec(modulusBigInt, exponentBigInt);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePublic(spec);
    }
}
