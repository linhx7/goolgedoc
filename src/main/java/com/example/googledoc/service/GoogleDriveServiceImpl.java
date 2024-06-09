package com.example.googledoc.service;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class GoogleDriveServiceImpl implements GoogleDriveService {

    @Autowired
    private Drive drive;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        File fileMetadata = new File();
//        fileMetadata.setName("My Document");
        fileMetadata.setMimeType("application/vnd.google-apps.document");

//        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());

        java.io.File filePath = new java.io.File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(filePath);
        FileContent mediaContent = new FileContent("application/vnd.openxmlformats-officedocument.wordprocessingml.document", filePath);

        File file2 = drive.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        setPermissionsToFile(drive, file2.getId());
        return file2.getId();


    }


    @Override
    public String deleteFile(String fileId) throws IOException {
        try{
            drive.files().delete(fileId).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "SUCCESS";

    }

    @Override
    public void downloadFileAsDocx(String fileId, OutputStream outputStream) throws IOException {
        try {
//            OutputStream outputStream = new FileOutputStream("D:/test2.docx");
//            drive.files().export(fileId, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
//                    .executeMediaAndDownloadTo(outputStream);
//
//            System.out.println("File downloaded to " + "D:/test2.docx");
//            outputStream.close();


            Drive.Files.Export export = drive.files().export(fileId, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            HttpResponse response = export.executeMedia();
           OutputStream outputStream2 = new FileOutputStream("D:/test99.docx");
            drive.files().export(fileId, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                    .executeMediaAndDownloadTo(outputStream2);
            outputStream2.close();
            System.out.println("File downloaded to " + "D:/test99.docx");
//            outputStream.close();
            response.download(outputStream);
        } catch (HttpResponseException e) {
            e.printStackTrace();
            throw new IOException("Failed to export file: " + e.getStatusMessage(), e);
        }
    }

    /**
     * Set permissions to a file in Google Drive.
     * @param fileId Id of the file to modify
     * @param service an authorized Drive client service
     * @throws IOException
     */
    public static void setPermissionsToFile(Drive service, String fileId) throws IOException {

        JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
            @Override
            public void onFailure(GoogleJsonError e,
                                  HttpHeaders responseHeaders)
                    throws IOException {
                // Handle error
                System.err.println(e.getMessage());
            }

            @Override
            public void onSuccess(Permission permission,
                                  HttpHeaders responseHeaders)
                    throws IOException {
                System.out.println("Permission ID: " + permission.getId());
            }
        };
        BatchRequest batch = service.batch();
        Permission userPermission = new Permission()
                .setType("anyone")
                .setRole("writer")
                .setAllowFileDiscovery(false);
//        Permission userPermission = new Permission()
//                .setType("user")
//                .setRole("writer")
//                .setEmailAddress("linhx7tiktok1@gmail.com");
        service.permissions().create(fileId, userPermission)
                .setFields("id")
                .queue(batch, callback);

        batch.execute();
    }
}
