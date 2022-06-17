package com.example.mock2.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
    private final static Path root = Paths.get("C:\\upload_mooc2\\");

    @Override
    @PostConstruct
    public void init() {
        System.out.println("Creating upload folder");
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @PreDestroy
    public void destroy() {
        System.out.println("deleting upload folder");
        try {
            Files.deleteIfExists(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete upload folder !");
        }
    }

    @Override
    public String save(MultipartFile file) {
        String savingPath;
        try {
            String filename = file.getOriginalFilename();
            savingPath = root + "\\" + filename;
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return savingPath;
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public String getFileTypeByProbeContentType(String fileName) {
        String fileType = "Undetermined";
        final File file = new File(fileName);
        try {

            fileType = Files.probeContentType(file.toPath());

        } catch (IOException ioException) {
            System.out.println("File type not detected for " + fileName);
        }
        return fileType;

    }

    public static void main(String[] args) {
        System.out.println(root);
    }


}