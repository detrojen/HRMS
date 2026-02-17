package com.hrms.backend.controllers;

import com.hrms.backend.utils.FileUtility;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class ResourceController {
    @GetMapping("/resource/{foldername}/{filename}")
    public ResponseEntity<byte[]> getResource(@PathVariable String foldername, @PathVariable String filename, ServletWebRequest request) throws IOException {
        byte[] file = FileUtility.readByte(foldername,filename);
        MediaType contentType = filename.indexOf(".pdf") == -1 ? MediaType.IMAGE_PNG :MediaType.APPLICATION_PDF;
        ContentDisposition cd = ContentDisposition.inline().filename(filename).build();
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDisposition(cd);
        return ResponseEntity.ok()
                .contentType(contentType)
                .headers(headers)
                .body(file);

    }
}
