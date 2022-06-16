package com.example.mock2.Service;

import com.example.mock2.DTO.ProductDTO;
import com.example.mock2.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private FilesStorageService filesStorageService;


    private ProductDTO convertToDTO(){
        return null;
    }

    @Override
    public Page<ProductDTO> findAllProduct(int pageNumber) {
        return null;
    }

    @Override
    public Set<ProductDTO> findAllProduct() {
        return null;
    }

    @Override
    public ProductDTO updateAProduct(int id, ProductDTO ProductDTO, MultipartFile[] multipartFile) {
        return null;
    }

    @Override
    public ProductDTO addAProduct(ProductDTO ProductDTO) {
        return null;
    }

    @Override
    public ProductDTO deleteAProduct(int id) {
        return null;
    }

    @Override
    public Set<ProductDTO> findByName(String name) {
        return null;
    }

    @Override
    public Page<ProductDTO> findByName(String name, int pageNumber) {
        return null;
    }

    @Override
    public String uploadFile(ProductDTO ProductDTO, MultipartFile[] multipartFile) throws IOException {

        List<String> fileNames = new ArrayList<>();
        Arrays.asList(multipartFile).stream().forEach(file -> {
            filesStorageService.save(file);
            fileNames.add(file.getOriginalFilename());
        });

        String message = "Uploaded the files successfully: " + fileNames;
//        String filename = multipartFile.getOriginalFilename();
//        String localPath = "C:\\upload\\"+filename;
//        multipartFile.transferTo(new File(localPath));
        return message;
    }
}
