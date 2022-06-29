package com.example.mock2.Service;

import com.example.mock2.DTO.ProductDTO;
import com.example.mock2.Entity.Product;
import com.example.mock2.Entity.ProductMedia;
import com.example.mock2.Repository.CategoryRepository;
import com.example.mock2.Repository.ProductMediaRepository;
import com.example.mock2.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final int NUMBER_OF_ENTITY_PER_PAGE = 10;

    private ProductRepository productRepository;
    private ProductMediaRepository productMediaRepository;
    private CategoryRepository categoryRepository;
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
        //check if update product details is the same with other product
        List<Product> productList = productRepository.findAll();
        Optional<Product> product1 = productList.stream().filter(
                p -> p.equals(ProductDTO.convertToProduct())
        ).findFirst();
        if(product1.isPresent()){
            if(product1.get().getProductId() != id)
                throw new RuntimeException("The product information to be updated is the same as that of another product");
        }

        Product product = productRepository.findById(id).get();
        ProductDTO.setProductId(id);
        product.setProductId(id);
        product.setProductPrice(ProductDTO.getProductPrice());
        product.setProductName(ProductDTO.getProductName());
        product.setProductQuantity(ProductDTO.getProductQuantity());
        product.setCategoryId(ProductDTO.getCategoryId());
        product.setProductRating(ProductDTO.getProductRating());
        productRepository.save(ProductDTO.convertToProduct());
        ProductDTO.setProductId(id);
        ProductDTO.setProductMediaSet(updateProductMedia(ProductDTO, multipartFile));
        return ProductDTO;

    }

    @Override
    public ProductDTO addAProduct(ProductDTO ProductDTO, MultipartFile[] multipartFile) {
        MultipartFile[] multipartFiles = multipartFile;
        if (checkIfExisted(ProductDTO.convertToProduct())) {
            throw new RuntimeException("this Product Details already exists");
        }
        else {
            Product product = productRepository.save(ProductDTO.convertToProduct());
            System.out.println(ProductDTO);
            if (product == null) throw new RuntimeException("Update Fail!");

            try {
                ProductDTO.setProductId(product.getProductId());
                ProductDTO.setProductMediaSet(uploadProductMedia(ProductDTO, multipartFiles));
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e.getCause());
            }

            return ProductDTO;
        }
    }

    @Override
    public ProductDTO deleteAProduct(long id) {

        ProductDTO productDTO = productRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Product with this id doesnt exist")).convertToProductDTO();
        productRepository.deleteById(id);
        filesStorageService.deleteByPath(id+"");
        return productDTO;
    }

    @Override
    public List<ProductDTO> findByName(String name) {
        List<Product> product = productRepository.findByProductNames(name);
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
    public Set<ProductMedia> uploadProductMedia(ProductDTO ProductDTO, MultipartFile[] multipartFile) throws IOException {
        if (multipartFile == null) return null;
        Set<ProductMedia> productMediaSet = new HashSet<>();
        List<String> fileNames = new ArrayList<>();
        Arrays.asList(multipartFile).stream().forEach(file -> {
            String path = filesStorageService.save(file,ProductDTO.getProductId()+"");
            String type = filesStorageService.getFileTypeByProbeContentType(file.getOriginalFilename());
            ProductMedia productMedia = new ProductMedia();
            productMedia.setPath(path);
            productMedia.setProductId(ProductDTO.getProductId());

            if (type == null) {
                type = "Unknown";
            }
            productMedia.setType(type);

            productMediaSet.add(productMediaRepository.saveAndFlush(productMedia));

        });
        return productMediaSet;
    }

    @Override
    public Page<ProductDTO> findByCategoryNamePagination(String name, int pageNumber) {
        return productRepository.findByCategoryNamePagination(name,PageRequest.of(pageNumber,NUMBER_OF_ENTITY_PER_PAGE))
                .map(
                        product -> product.convertToProductDTO()
                );
    }

    @Override
    public List<ProductDTO> findByCategoryName(String name) {
       return productRepository.findByCategoryName(name).stream().map(
               p -> p.convertToProductDTO()
       ).collect(Collectors.toList());
    }

    @Override
    public Set<ProductMedia> updateProductMedia(ProductDTO productDTO, MultipartFile[] multipartFiles) {

        List<ProductMedia> product = productMediaRepository.findAllByProductId(productDTO.getProductId());
        Set<ProductMedia> productMediaSet = new HashSet<>();
        for (int i = 0; i < product.size(); i++) {
            filesStorageService.deleteByPath(product.get(i).getPath());
            productMediaRepository.deleteById(product.get(i).getProductMediaId());
        }
        if(multipartFiles == null) {
            filesStorageService.deleteByPath(productDTO.getProductId()+"");
            return null;
        }

        for (int i = 0; i < multipartFiles.length; i++) {

            String path = filesStorageService.save(multipartFiles[i],productDTO.getProductId()+"");
            String type = filesStorageService.getFileTypeByProbeContentType(multipartFiles[i].getOriginalFilename());
            ProductMedia productMedia1 = new ProductMedia();
            productMedia1.setPath(path);
            productMedia1.setProductId(productDTO.getProductId());
            productMedia1.setType(type);
            productMediaSet.add(productMediaRepository.save(productMedia1));
        }
        return productMediaSet;

    }

    //check name, price, categoryId
    private boolean checkIfExisted(Product product) {
        List<Product> products = productRepository.findAll();

        if (products.contains(product)) return true;
        return false;
    }


    public long getProductIdByProductName(String productName) {
        return productRepository.getProductIdByProductName(productName);
    }

    public ProductDTO getProductByProductId(long productId) {
        return productRepository.getProductByProductId(productId).convertToProductDTO();
    }


    public ProductDTO findExactByName(String name){
        Product product = productRepository.findByProductName(name);
        return product.convertToProductDTO();
    }

}
