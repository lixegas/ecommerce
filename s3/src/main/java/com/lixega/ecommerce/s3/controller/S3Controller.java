package com.lixega.ecommerce.s3.controller;


import com.lixega.ecommerce.s3.model.FileMetadata;
import com.lixega.ecommerce.s3.repository.FileMetadataRepository;
import com.lixega.ecommerce.s3.service.S3Service;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/s3")
@AllArgsConstructor
public class S3Controller {

    private final S3Service s3Service;
    private final FileMetadataRepository fileMetadataRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        File localFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(localFile);

        String s3Url = s3Service.uploadFile(file.getOriginalFilename(), localFile);

        FileMetadata fileMetadata = FileMetadata.builder()
                .fileName(file.getOriginalFilename())
                .s3Url(s3Url)
                .build();
        fileMetadataRepository.save(fileMetadata);

        // Return a success message
        return ResponseEntity.ok("File uploaded successfully");
    }
}
