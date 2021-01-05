package com.example.uploadfiletoblobstorage;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class AzureBlobAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AzureBlobAdapter.class);

    @Autowired
    private CloudBlobContainer cloudBlobContainer;

    public URI upload(MultipartFile multipartFile){
        LocalDateTime now = LocalDateTime.now();
        String formatDate = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.ENGLISH));
        URI uri = null;
        CloudBlockBlob blob = null;
        try {
            blob = cloudBlobContainer.getBlockBlobReference(formatDate+multipartFile.getOriginalFilename());
            BlobRequestOptions blobRequestOptions = new BlobRequestOptions();
            blobRequestOptions.setConcurrentRequestCount(15);
            blobRequestOptions.setSingleBlobPutThresholdInBytes(1048576);
            blob.upload(multipartFile.getInputStream(), multipartFile.getSize(),  null, blobRequestOptions ,null);
            uri = blob.getUri();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return uri;
    }

}
