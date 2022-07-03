package org.freeuni.musicforum.file.processor;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageProcessor {

    private Part imagePart;
    private String imageName;
    private String uploadPath;

    private File newImageFile;

    public ImageProcessor(Part imagePart, String imageNewName, String uploadPath) throws IOException {
        this.imagePart = imagePart;
        this.imageName = imageNewName;
        this.uploadPath = uploadPath;
        newImageFile = null;
        copyImageToNewPath();
    }



    public String getBase64EncodedString() throws IOException {
        byte[] encodedBytes = Base64.getEncoder().encode(Files.readAllBytes(getImageFile().toPath()));
        String encodedString = new String(encodedBytes, StandardCharsets.UTF_8);
        return encodedString;
    }

    public String getFullName() {
        String originalName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
        String imageExtension = originalName.substring(originalName.lastIndexOf('.'));
        String fullName = imageName + imageExtension;
        return fullName;
    }

    public File getImageFile() {
        if(newImageFile == null) constructNewImageFile();
        return newImageFile;
    }

    private void copyImageToNewPath() throws IOException {
        InputStream fileContent = imagePart.getInputStream();
        Files.copy(fileContent, getImageFile().toPath());
    }

    private void constructNewImageFile() {
        String uploadPath = this.uploadPath;
        File uploads = new File(uploadPath);
        newImageFile = new File(uploads, getFullName());
    }
}
