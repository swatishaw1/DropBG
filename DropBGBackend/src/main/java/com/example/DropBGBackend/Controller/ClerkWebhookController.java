package com.example.DropBGBackend.Controller;

import com.example.DropBGBackend.Config.JwtValidator;
import com.example.DropBGBackend.DTO.UserDTO;
import com.example.DropBGBackend.Response.DropBGResponse;
import com.example.DropBGBackend.Service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks")
@RequiredArgsConstructor
public class ClerkWebhookController {
    private final UserService userService;
    private final JwtValidator validator;
    @PostMapping("/clerk")
    public ResponseEntity<?> handleClerkWebhook(@RequestHeader("svix-id") String svixId,
                                                  @RequestHeader("svix-timestamp") String svixTimestamp,
                                                @RequestHeader("svix-signature") String svixSignature,
                                                @RequestBody String payload){

        DropBGResponse response = null;
        try{
            System.out.println(svixId);
            System.out.println(svixTimestamp);
            System.out.println(svixSignature);

            boolean valid = verifySignature(svixId,svixTimestamp,svixSignature ,payload);
            if(!valid){
                response = DropBGResponse.builder()
                        .statusCode(HttpStatus.UNAUTHORIZED)
                        .data("Invalid signature")
                        .success(false)
                        .build();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(payload);
            String eventType = rootNode.path("type").asText();
            switch (eventType){
                case "user.created":
                    handleUserCreated(rootNode.path("data"));
                    break;
                case "user.updated":
                    handleUserUpdated(rootNode.path("data"));
                    break;
                case "user.deleted":
                    handleClerkDeleted(rootNode.path("data"));
                    break;
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            response = DropBGResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .data("Error processing webhook: " + e.getMessage())
                    .success(false)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    private void handleClerkDeleted(JsonNode data) {
        String clerkId = data.path("id").asText();
        userService.deleteUserByClerkId(clerkId);
    }

    private void handleUserUpdated(JsonNode data) {
        String clerkId = data.path("id").asText();
        UserDTO existingUser = userService.getUserByClerkId(clerkId);
        existingUser.setEmail(data.path("email_addresses").get(0).path("email_address").asText());
        existingUser.setFirstName(data.path("first_name").asText());
        existingUser.setLastName(data.path("last_name").asText());
        existingUser.setPhotoUrl(data.path("image_url").asText());
        userService.saveUser(existingUser);//DTO obj -> Entity obj and save into database
    }

    private void handleUserCreated(JsonNode data) {
        UserDTO newUser = UserDTO.builder()
                .clerkId(data.path("id").asText())
                .email(data.path("email_addresses").get(0).path("email_address").asText())
                .firstName(data.path("first_name").asText())
                .lastName(data.path("last_name").asText())
                .photoUrl(data.path("image_url").asText())
                .build();
        userService.saveUser(newUser);
    }

    private boolean verifySignature(String svixId, String svixTimestamp, String svixSignature, String payload) {
        return validator.validate(svixId,svixTimestamp,svixSignature,payload);//If validation cant be done properly then in database the user is not created properly and bg_removal won't get started though payment would be working
        /*return true; */
    }
}
