package com.example.googledoc.controller;


import com.example.googledoc.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping("/api/drive")
public class GoogleDriveController {

    @Autowired
    private GoogleDriveService googleDriveService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileId = googleDriveService.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully. File ID: https://docs.google.com/document/d/" + fileId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
