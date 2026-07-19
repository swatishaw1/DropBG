package com.example.DropBGBackend.service.Impli;

import com.example.DropBGBackend.dto.UserDTO;
import com.example.DropBGBackend.model.UserEntity;
import com.example.DropBGBackend.repository.UserRepository;
import com.example.DropBGBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpli implements UserService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpli.class);

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        Optional<UserEntity> optionalUser = userRepository.findByClerkId(userDTO.getClerkId());
        if (optionalUser.isPresent()){
            UserEntity existingUser = optionalUser.get();
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setFirstName(userDTO.getFirstName());
            existingUser.setLastName(userDTO.getLastName());
            existingUser.setPhotoUrl(userDTO.getPhotoUrl());
            if (userDTO.getCredits()!=null){// Only update credits if it's provided
                existingUser.setCredits(userDTO.getCredits());
            }
            existingUser = userRepository.save(existingUser);
            return mapToDTO(existingUser);
        }
        UserEntity newUser = mapToEntity(userDTO);
        userRepository.save(newUser);
        return mapToDTO(newUser);
    }

    @Override
    public UserDTO getUserByClerkId(String clerkId) {
        UserEntity userEntity = userRepository.findByClerkId(clerkId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return mapToDTO(userEntity);
    }

    @Override
    public void deleteUserByClerkId(String clerkId) {
        UserEntity userEntity = userRepository.findByClerkId(clerkId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        logger.info("User Is deleted: {}", userEntity);
        userRepository.delete(userEntity);
    }

    private UserDTO mapToDTO(UserEntity newUser) {
        return UserDTO.builder()
                .clerkId(newUser.getClerkId())
                .credits(newUser.getCredits())
                .email(newUser.getEmail())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .build();
    }

    private UserEntity mapToEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .clerkId(userDTO.getClerkId())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .photoUrl(userDTO.getPhotoUrl())
                .credits(userDTO.getCredits())
                .build();
    }
}
