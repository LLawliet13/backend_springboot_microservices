package com.example.mock2.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    public void init();
    public String getPathFile(MultipartFile file,String subFolder);

    public String save(MultipartFile file,String subFolder);

    public Resource load(String filename);

    public void deleteAll();

    void deleteByPath(String path);

    public Stream<Path> loadAll();

    public String getFileTypeByProbeContentType(String fileName);
}
