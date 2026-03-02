package com.hrms.backend.utils;

import com.hrms.backend.exceptions.ServerError;
import org.springframework.core.io.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

public class FileUtility {
    private FileUtility(){}
    private static String uploadFolderPath = System.getProperty("user.dir") + File.separator + "uploads";
    public static String Save(MultipartFile file, String folderName){
        UUID uuid = UUID.randomUUID();
        String fileType = Arrays.stream(file.getContentType().split("/")).toArray()[1].toString();
        String folderPath = uploadFolderPath + File.separator + folderName;
        String filePath = folderPath + File.separator + uuid.toString() +"."+ fileType;
        File file1 = new File(filePath);
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            throw new ServerError(e.getMessage());
        }
        return uuid.toString() + "." + fileType;
    }

    public static Resource Get(String folderName,String fileName){
        String absFilePath = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + folderName + File.separator + fileName;
        Path filePath = Paths.get( absFilePath);
        FileSystemResource file = new FileSystemResource(new File(filePath.toUri()));
        return file;
    }
    public static byte[] readByte(String folderName,String fileName){
        String absFilePath = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + folderName + File.separator + fileName;
        Path filePath = Paths.get( absFilePath);
        try {
            InputStreamResource file = new InputStreamResource(new FileInputStream(new File(filePath.toUri())));
            return file.getInputStream().readAllBytes();
        }catch (Exception e){
            return new byte[]{};
        }
    }



}
