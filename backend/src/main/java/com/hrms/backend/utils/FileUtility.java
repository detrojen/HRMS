package com.hrms.backend.utils;

import com.hrms.backend.exceptions.ServerError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;
@Component
public class FileUtility {
    private static String uploadDir = "uploads";
    private static String userDir = System.getProperty("user.dir");
    private FileUtility(){}
    private static String uploadFolderPath =userDir + File.separator + uploadDir;
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
        String absFilePath = userDir + File.separator + uploadDir + File.separator + folderName + File.separator + fileName;
        Path filePath = Paths.get( absFilePath);
        FileSystemResource file = new FileSystemResource(new File(filePath.toUri()));
        return file;
    }
    public static boolean delete(String folderName, String fileName){
        String absFilePath = userDir + File.separator + uploadDir + File.separator + folderName + File.separator + fileName;
        File file = new File(absFilePath);
        boolean flag = false;
        if(file.exists()){
            flag = file.delete();
        }
        return flag;
    }
    public static byte[] readByte(String folderName,String fileName) throws IOException {
        String absFilePath = userDir + File.separator + uploadDir + File.separator + folderName + File.separator + fileName;
        Path filePath = Paths.get( absFilePath);
        FileInputStream file = null;
        try {
            file = new FileInputStream((new File(filePath.toUri())));
            byte[] bytes = file.readAllBytes();
            file.close();
            return bytes;
        }catch (Exception e){
            file.close();
            return new byte[]{};
        }
    }



}
