package org.freeuni.musicforum.file.processor;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class FileProcessor {

    public static final String IMAGE_HTML_PREFIX_BASE64 = "data:image/*;base64,";
    public static final String AUDIO_HTML_PREFIX_BASE64 = "data:audio/mpeg;base64,";

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
        String fileExtension = getFileExtension();
        String fullName = fileName + fileExtension;
        return fullName;
    }

    public String getFileExtension() {
        String originalName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String fileExtension = originalName.substring(originalName.lastIndexOf('.'));
        return fileExtension;
    }

    public File getFile() {
        if(newFile == null) constructNewFile();
        return newFile;
    }

    private void copyFileToNewPath() throws IOException {
        InputStream fileContent = part.getInputStream();
        Files.copy(fileContent, getFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
        fileContent.close();
    }

    private void constructNewFile() {
        File uploads = new File(uploadPath);
        newFile = new File(uploads, getFullName());
    }
}
