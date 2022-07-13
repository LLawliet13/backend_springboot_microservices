package com.example.mock2.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    //    private final static Path root = Paths.get("C:/upload_mooc2/");// for non-docker
    private final static Path root = Paths.get("upload_mooc2/");

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

    @Override
    public String getPathFile(MultipartFile file, String subFolder) {
        String filename = file.getOriginalFilename();
        return root  +"/"+ subFolder + "/" + filename;
    }

    @PreDestroy
    public void destroy() {
//        System.out.println("deleting upload folder");
//        FileSystemUtils.deleteRecursively(root.toFile());

    }

    @Override
    public String save(MultipartFile file, String subFolder) {
        String savingPath;
        try {
            File theDir = new File(root +"/"+ subFolder);
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            for (int i = 0; i < 50; i++)
                System.out.println("Folder is Exist?" + theDir.getAbsolutePath() + theDir.exists());
            String filename = file.getOriginalFilename();
            savingPath = root + "/" + subFolder + "/" + filename;
            Files.copy(file.getInputStream(), this.root.resolve(subFolder + "/" + filename), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File input is Exist?" + new File(root + "/" + subFolder + "/" + filename).getAbsolutePath() + " is " + (new File(root + "/" + subFolder + "/" + filename)).exists());
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return savingPath;
    }


    @Override
    public void deleteAll() {

        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public void deleteByPath(String path) {
        if (!path.contains(root.toString())) {
            path = root +"/"+ path;
        }
        Path pathName = Paths.get(path);
        FileSystemUtils.deleteRecursively(pathName.toFile());
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


    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
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


    public static void main(String[] args) {
        System.out.println(root);
    }


}