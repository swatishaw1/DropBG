package com.example.DropBGBackend.Controller;


import com.example.DropBGBackend.DTO.UserDTO;
import com.example.DropBGBackend.Response.DropBGResponse;
import com.example.DropBGBackend.Service.RemoveBackgroundService;
import com.example.DropBGBackend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final RemoveBackgroundService removeBackgroundService;
    private final UserService userService;

    @PostMapping("/remove-background")
    public ResponseEntity<?> removeBackground(@RequestPart("file")MultipartFile file, Authentication authentication){
        DropBGResponse imageResponse =null;
        Map<String,Object> responseMap = new HashMap<>();
        try{
            if (authentication.getName().isEmpty() || authentication.getName() == null){
                imageResponse = DropBGResponse.builder()
                        .statusCode(HttpStatus.FORBIDDEN)
                        .data("User doesn't have the permission to access the information")
                        .success(false)
                        .build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(imageResponse);
            }

            //Validation
            String clerkId = authentication.getName();
            UserDTO userDTO = userService.getUserByClerkId(clerkId);
            if (userDTO.getCredits()==0){
                responseMap.put("message","No Credit Balance");
                responseMap.put("CreditBalance: ",userDTO.getCredits());
                imageResponse = DropBGResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .data("The Image handled majorly")
                        .success(false)
                        .build();
                return  ResponseEntity.ok(imageResponse);
            }
            byte[] imageByte = removeBackgroundService.removeBackground(file);
             String base64Image = Base64.getEncoder().encodeToString(imageByte);
             userDTO.setCredits(userDTO.getCredits()-1);
             userService.saveUser(userDTO);
             return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(base64Image);
        } catch (Exception e) {
            imageResponse = DropBGResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .data("Internal Server Error: " + e.getMessage())
                    .success(false)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(imageResponse);
        }
    }
}
