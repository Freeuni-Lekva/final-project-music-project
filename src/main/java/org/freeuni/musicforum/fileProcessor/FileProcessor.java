package org.freeuni.musicforum.fileProcessor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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

    public FileProcessor(Part part, String fileNewName, HttpServletRequest req, String pathFromWebFolder) throws IOException {
        this.part = part;
        this.fileName = fileNewName;
        this.uploadPath = getFullPath(req, pathFromWebFolder);
        newFile = null;
        copyFileToNewPath();
    }

    public static String getFullPath(HttpServletRequest req, String pathFromWebFolder) {
        ServletContext context = req.getServletContext();
        String realPath = context.getRealPath("");
        String realPathWithoutTarget = realPath.substring(0, realPath.indexOf("target"));
        String PATH_TO_WEB_FOLDER = "src/main/webapp/";
        String pathFromContextRoot = PATH_TO_WEB_FOLDER + pathFromWebFolder;
        return realPathWithoutTarget + pathFromContextRoot;
    }

    private void copyFileToNewPath() throws IOException {
        InputStream fileContent = part.getInputStream();
        Files.copy(fileContent, getFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
        fileContent.close();
    }

    public File getFile() {
        if(newFile == null) constructNewFile();
        return newFile;
    }

    private void constructNewFile() {
        File uploads = new File(uploadPath);
        newFile = new File(uploads, getFullName());
    }

    public String getFullName() {
        String fileExtension = getFileExtension();
        return fileName + fileExtension;
    }

    public String getFileExtension() {
        String originalName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        return originalName.substring(originalName.lastIndexOf('.'));
    }

    public String getBase64EncodedString() throws IOException {
        byte[] encodedBytes = Base64.getEncoder().encode(Files.readAllBytes(getFile().toPath()));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }
    public static String getBase64EncodedString(File file) throws IOException {
        byte[] encodedBytes = Base64.getEncoder().encode(Files.readAllBytes(file.toPath()));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

}
