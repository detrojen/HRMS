package com.hrms.backend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtility {
    private static String uploadFolderPath = System.getProperty("user.dir") + File.separator + "uploads";
    public static String Save(MultipartFile file, String folderName){
        String filePath = uploadFolderPath + File.separator + folderName + File.separator + file.getOriginalFilename();
        File file1 = new File(filePath);
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file.getOriginalFilename();
    }

    public static Resource Get(String folderName,String fileName) throws MalformedURLException {
        String absFilePath = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + folderName + File.separator + fileName;
        Path filePath = Paths.get( absFilePath);
//        Resource file = new UrlResource(filePath.toUri());
        FileSystemResource file = new FileSystemResource(new File(filePath.toUri()));
        return file;
    }
}
