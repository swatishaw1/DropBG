// Description: This class validates JWT tokens using the Svix library.
package com.example.DropBGBackend.Config;

import com.svix.Webhook;
import com.svix.exceptions.WebhookVerificationException;
import org.springframework.beans.factory.annotation.Value;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    @Value("${app_secret_key}")
    private String secret;

    public boolean validate(String svixId,String svixTimestamp,String svixSignature,String payload) {
        if (svixId == null || svixTimestamp == null || svixSignature == null) {
            return false;
        }
        try {
            Webhook webhook = new Webhook(secret);
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("svix-id", List.of(svixId));
            headers.put("svix-timestamp", List.of(svixTimestamp));
            headers.put("svix-signature", List.of(svixSignature));
            HttpHeaders httpHeaders =
                    HttpHeaders.of(headers, (k, v) -> true);
            webhook.verify(payload, httpHeaders);
            return true;
        } catch (WebhookVerificationException e) {
            return false;
        }
    }
}
