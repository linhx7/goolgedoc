package com.example.googledoc.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.OutputStream;

public interface GoogleDriveService {
    String uploadFile(MultipartFile file) throws IOException;
    String deleteFile(String fileId) throws IOException;
    void downloadFileAsDocx(String fileId, OutputStream outputStream) throws IOException;
}
