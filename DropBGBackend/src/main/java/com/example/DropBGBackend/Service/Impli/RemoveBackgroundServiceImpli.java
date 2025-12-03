package com.example.DropBGBackend.Service.Impli;

import com.example.DropBGBackend.Client.ClipDropClient;
import com.example.DropBGBackend.Service.RemoveBackgroundService;
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

    @Override
    public byte[] removeBackground(MultipartFile file) {
        return clipDropClient.removeBackground(file,apiKey);
    }
}
