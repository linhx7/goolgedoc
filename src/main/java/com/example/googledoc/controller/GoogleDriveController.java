package com.example.googledoc.controller;


import com.example.googledoc.service.GoogleDriveService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
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
    @PostMapping("/delete/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileId) {
        try {
            googleDriveService.deleteFile(fileId);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete file: " + e.getMessage());
        }
    }

    @PostMapping("/download/{fileId}")
    public ResponseEntity<Void> downloadFileAsDocx(@PathVariable String fileId, HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"document.docx\"");
        try {
            googleDriveService.downloadFileAsDocx(fileId, response.getOutputStream());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
