package com.example.mock2.Service;

import com.example.mock2.DTO.ProductDTO;
import com.example.mock2.Entity.Product;
import com.example.mock2.Entity.ProductMedia;
import com.example.mock2.Repository.ProductMediaRepository;
import com.example.mock2.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final int NUMBER_OF_ENTITY_PER_PAGE = 10;

    private ProductRepository productRepository;
    private ProductMediaRepository productMediaRepository;
    private FilesStorageService filesStorageService;




    @Override
    public Page<ProductDTO> findAllProduct(int pageNumber) {
        return productRepository.findAll(PageRequest.of(pageNumber,NUMBER_OF_ENTITY_PER_PAGE)).map(
                product -> product.convertToProductDTO()
        );
    }

    @Override
    public List<ProductDTO> findAllProduct() {
        return productRepository.findAll().stream().map(
                product -> product.convertToProductDTO()
        ).collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateAProduct(long id, ProductDTO ProductDTO, MultipartFile[] multipartFile) {
        ProductDTO.setProductId(id);
        productRepository.save(ProductDTO.convertToProduct());
        try {
            uploadProductMedia(ProductDTO, multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        return productRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Update Fail!")
        ).convertToProductDTO();
    }

    @Override
    public ProductDTO addAProduct(ProductDTO ProductDTO, MultipartFile[] multipartFile) {
        productRepository.save(ProductDTO.convertToProduct());
        List<Product> product = productRepository.findByProductName(ProductDTO.getProductName());
        if(product == null) throw new RuntimeException("Update Fail!");
        try {
            ProductDTO.setProductId(product.get(0).getProductId());
            uploadProductMedia(ProductDTO, multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        return product.get(0).convertToProductDTO();
    }

    @Override
    public ProductDTO deleteAProduct(long id) {

        ProductDTO productDTO = productRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Product with this id doesnt exist")).convertToProductDTO();
        productRepository.deleteById(id);

        return productDTO;
    }

    @Override
    public List<ProductDTO> findByName(String name) {
        List<Product> product = productRepository.findByProductName(name);
        if(product == null) throw new RuntimeException("cant find");
        return product.stream().map(
                product1 -> product1.convertToProductDTO()
        ).collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> findByName(String name, int pageNumber) {
        Page<Product> product = productRepository.findByProductName(name,PageRequest.of(pageNumber,NUMBER_OF_ENTITY_PER_PAGE));
        if(product == null) throw new RuntimeException("cant find");
        return product.map(
                product1 -> product1.convertToProductDTO()
        );
    }

    @Override
    public String uploadProductMedia(ProductDTO ProductDTO, MultipartFile[] multipartFile) throws IOException {

        List<String> fileNames = new ArrayList<>();
        Arrays.asList(multipartFile).stream().forEach(file -> {
            String path = filesStorageService.save(file);
            String type = filesStorageService.getFileTypeByProbeContentType(file.getOriginalFilename());
            ProductMedia productMedia = new ProductMedia();
            productMedia.setPath(path);
            productMedia.setProductId(ProductDTO.getProductId());
            productMedia.setType(type);
            productMediaRepository.save(productMedia);
            fileNames.add(file.getOriginalFilename());

        });
        String message = "Uploaded the files successfully: " + fileNames;
        return message;
    }

}
