package com.example.DropBGBackend.service.Impli;

import com.example.DropBGBackend.client.ClipDropClient;
import com.example.DropBGBackend.service.RemoveBackgroundService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RemoveBackgroundServiceImpli implements RemoveBackgroundService {

    @Value("${clipDrop.apiKey}")
    private String apiKey;

    private final ClipDropClient clipDropClient;

    @Override// Getting the response from ClipDrop APi
    public byte[] removeBackground(MultipartFile file) {
        return clipDropClient.removeBackground(file,apiKey);
    }
}
