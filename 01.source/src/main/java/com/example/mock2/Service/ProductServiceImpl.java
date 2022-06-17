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
        return productRepository.findAll(PageRequest.of(pageNumber, NUMBER_OF_ENTITY_PER_PAGE)).map(
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
        Product product = productRepository.findById(id).get();
        product.setProductId(id);
        product.setProductPrice(ProductDTO.getProductPrice());
        product.setProductName(ProductDTO.getProductName());
        product.setProductQuantity(ProductDTO.getProductQuantity());
        product.setCategoryId(ProductDTO.getCategoryId());
        product.setProductRating(ProductDTO.getProductRating());
        productRepository.save(product);


        updateProductMedia(ProductDTO, multipartFile);

        return productRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Update Fail!")
        ).convertToProductDTO();
    }

    @Override
    public ProductDTO addAProduct(ProductDTO ProductDTO, MultipartFile[] multipartFile) {
        productRepository.save(ProductDTO.convertToProduct());
        System.out.println(ProductDTO);
        List<Product> product = productRepository.findByProductName(ProductDTO.getProductName());
        if (product == null) throw new RuntimeException("Update Fail!");

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
        if (product == null) throw new RuntimeException("cant find");
        return product.stream().map(
                product1 -> product1.convertToProductDTO()
        ).collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> findByName(String name, int pageNumber) {
        Page<Product> product = productRepository.findByProductName(name, PageRequest.of(pageNumber, NUMBER_OF_ENTITY_PER_PAGE));
        if (product == null) throw new RuntimeException("cant find");
        return product.map(
                product1 -> product1.convertToProductDTO()
        );
    }

    @Override
    public String uploadProductMedia(ProductDTO ProductDTO, MultipartFile[] multipartFile) throws IOException {
        if (multipartFile == null) return "nothing to upload";
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

    @Override
    public boolean updateProductMedia(ProductDTO productDTO, MultipartFile[] multipartFiles) {

        List<ProductMedia> product = productMediaRepository.findAllByProductId(productDTO.getProductId());

        for (int i = 0; i < product.size(); i++) {
            filesStorageService.deleteByPath(product.get(i).getPath());
            productMediaRepository.deleteById(product.get(i).getProductMediaId());
        }
        for (int i = 0; i < multipartFiles.length; i++) {

            String path = filesStorageService.save(multipartFiles[i]);
            String type = filesStorageService.getFileTypeByProbeContentType(multipartFiles[i].getOriginalFilename());
            ProductMedia productMedia1 = new ProductMedia();
            productMedia1.setPath(path);
            productMedia1.setProductId(productDTO.getProductId());
            productMedia1.setType(type);
            productMediaRepository.save(productMedia1);
        }
        return true;

    }

}
