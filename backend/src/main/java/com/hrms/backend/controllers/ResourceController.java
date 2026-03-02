package com.hrms.backend.controllers;

import com.hrms.backend.utils.FileUtility;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;


@RestController
public class ResourceController {
    @GetMapping("/resource/{foldername}/{filename}")
    public ResponseEntity<byte[]> getResource(@PathVariable String foldername, @PathVariable String filename, ServletWebRequest request) {
        byte[] file = FileUtility.readByte(foldername,filename);
        MediaType contentType = filename.indexOf(".pdf") == -1 ? MediaType.IMAGE_PNG :MediaType.APPLICATION_PDF;
        HttpHeaders headers = new HttpHeaders();
        return ResponseEntity.ok()
                .contentType(contentType)
                .headers(headers)
                .body(file);

    }
}
