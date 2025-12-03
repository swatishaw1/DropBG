package com.example.DropBGBackend.Controller;

import com.example.DropBGBackend.DTO.UserDTO;
import com.example.DropBGBackend.Response.DropBGResponse;
import com.example.DropBGBackend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createOrUpdateUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        DropBGResponse response = null;
        try {
            if (!authentication.getName().equals(userDTO.getClerkId())) {
                response = DropBGResponse.builder()
                        .success(false)
                        .data("User does not have permission to access this resource.")
                        .statusCode(HttpStatus.FORBIDDEN)
                        .build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            UserDTO user = userService.saveUser(userDTO);
            response = DropBGResponse.builder()
                    .success(true)
                    .data(user)
                    .statusCode(HttpStatus.OK)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response = DropBGResponse.builder()
                    .success(false)
                    .data(e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/credits")
    public ResponseEntity<?> getUserCredits(Authentication authentication){
        DropBGResponse creditResponse =null;
        try {
            if(authentication == null){
                creditResponse = DropBGResponse.builder()
                        .statusCode(HttpStatus.FORBIDDEN)
                        .data("User does not have permission to access this resource.")
                        .success(false)
                        .build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(creditResponse);
            }
            String clerkId = authentication.getName();
            UserDTO existingUser = userService.getUserByClerkId(clerkId);
            Map<String,Integer> map = new HashMap<>();
            map.put("credits",existingUser.getCredits());
            creditResponse = DropBGResponse.builder()
                    .statusCode(HttpStatus.OK)
                    .data(map)
                    .success(true)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(creditResponse);
        }catch (Exception exception){
            creditResponse = DropBGResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .data(exception.getMessage())
                    .success(false)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(creditResponse);
        }
    }
}
