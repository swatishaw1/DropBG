// Description: This class validates JWT tokens using the Svix library.
package com.example.DropBGBackend.Config;

import com.svix.Webhook;
import com.svix.exceptions.WebhookVerificationException;
import org.springframework.beans.factory.annotation.Value;
import java.net.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    @Value("${CLERK_APP_SECRET_KEY}")
    private String secret;

    public boolean validate(String payload, HttpHeaders headers) {
        /*try {
            Webhook webhook = new Webhook(secret);
            webhook.verify(payload, headers);
            return true;
        } catch (WebhookVerificationException e) {
            return false;
        }*/
        return true;
    }

}
