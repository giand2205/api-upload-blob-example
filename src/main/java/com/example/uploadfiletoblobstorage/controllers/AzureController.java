package com.example.uploadfiletoblobstorage.controllers;

import com.example.uploadfiletoblobstorage.AzureBlobAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class AzureController {

    @Autowired
    private AzureBlobAdapter azureBlobAdapter;

    @PostMapping("upload")
    public ResponseEntity upload(@RequestParam MultipartFile multipartFile) {
        URI url = azureBlobAdapter.upload(multipartFile);
        return ResponseEntity.ok(url);
    }
}