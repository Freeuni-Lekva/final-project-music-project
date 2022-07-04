package org.freeuni.musicforum.file.processor;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class FileProcessor {

    private Part part;
    private String fileName;
    private String uploadPath;

    private File newFile;

    public FileProcessor(Part part, String fileNewName, String uploadPath) throws IOException {
        this.part = part;
        this.fileName = fileNewName;
        this.uploadPath = uploadPath;
        newFile = null;
        copyFileToNewPath();
    }



    public String getBase64EncodedString() throws IOException {
        byte[] encodedBytes = Base64.getEncoder().encode(Files.readAllBytes(getFile().toPath()));
        String encodedString = new String(encodedBytes, StandardCharsets.UTF_8);
        return encodedString;
    }

    public String getFullName() {
        String originalName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String fileExtension = originalName.substring(originalName.lastIndexOf('.'));
        String fullName = fileName + fileExtension;
        return fullName;
    }

    public File getFile() {
        if(newFile == null) constructNewFile();
        return newFile;
    }

    private void copyFileToNewPath() throws IOException {
        InputStream fileContent = part.getInputStream();
        Files.copy(fileContent, getFile().toPath());
    }

    private void constructNewFile() {
        File uploads = new File(uploadPath);
        newFile = new File(uploads, getFullName());
    }
}
