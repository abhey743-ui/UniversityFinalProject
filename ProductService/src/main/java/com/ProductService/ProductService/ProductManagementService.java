package com.ProductService.ProductService;
import com.ProductService.ProductDto.ProductAddingDto;
import com.ProductService.ProductDto.ProductUpdateRequestDto;
import com.ProductService.ProductModel.ProductModel;
import com.ProductService.ProductRepository.ProductRepository;
import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
@AllArgsConstructor
public class ProductManagementService {

    private final Cloudinary cloudinary;
    private final ProductRepository productRepository;

    public void  addProducts(ProductAddingDto productAddingDto, List<MultipartFile> images) throws IOException {

        ProductModel newProduct = new ProductModel();
        if (productAddingDto.getProductName() != null) {

            newProduct.setProductName(productAddingDto.getProductName());
        }
        if (productAddingDto.getProductType() != null) {
            newProduct.setProductType(productAddingDto.getProductType());
        }
        if (productAddingDto.getCategory() != null) {

           newProduct.setCategory(productAddingDto.getCategory());
        }
        if (productAddingDto.getBrand() != null) {
            newProduct.setBrand(productAddingDto.getBrand());
        }

        newProduct.setPrice(productAddingDto.getPrice());
        Map<String , Object> newCategory = new HashMap<>();

        Map<String,Object>  allAttributes = productAddingDto.getAttributes();

        allAttributes.forEach((key, value) -> {
            if (key != null && !key.trim().isEmpty() && value != null) {
                newCategory.put(key.trim(), value);
            }
        });

        List<String> imageUrls = new ArrayList<>();
        for(MultipartFile image: images) {

            try {
                Map uploadResult = cloudinary.uploader().upload(
                        image.getBytes(),
                        ObjectUtils.asMap("folder", "products")
                );

                imageUrls.add(uploadResult.get("secure_url").toString());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
         newProduct.setAttributes(newCategory);
        newProduct.setImageUrls(imageUrls);
        productRepository.save(newProduct);

    }

    public void deleteProduct(String id){

                productRepository.findById(id);
    }
    public void updateProduct(ProductUpdateRequestDto dto) throws IOException {

        ProductModel product = productRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));


        if (dto.getProductName() != null) product.setProductName(dto.getProductName());
        if (dto.getBrand() != null) product.setBrand(dto.getBrand());
        if (dto.getColor() != null) product.setColor(dto.getColor());
        if (dto.getCondition() != null) product.setCondition(dto.getCondition());
        if (dto.getProductType() != null) product.setProductType(dto.getProductType());
        if (dto.getCategory() != null) product.setCategory(dto.getCategory());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());


        if (dto.getAttributesToAddOrUpdate() != null) {
            Map<String, Object> attributes = product.getAttributes();
            if (attributes == null) attributes = new HashMap<>();
            attributes.putAll(dto.getAttributesToAddOrUpdate());
            product.setAttributes(attributes);
        }


        if (dto.getAttributesToRemove() != null) {
            Map<String, Object> attributes = product.getAttributes();
            if (attributes != null) {
                dto.getAttributesToRemove().forEach(attributes::remove);
            }
        }

        List<String> currentImages = product.getImageUrls();
        if (currentImages == null) currentImages = new ArrayList<>();

        if (dto.getRemoveImageUrls() != null) {
            currentImages.removeAll(dto.getRemoveImageUrls());
        }

        if (Boolean.TRUE.equals(dto.getReplaceAllImages())) {
            currentImages = new ArrayList<>();
            if (dto.getNewImageUrls() != null) {
                currentImages.addAll(dto.getNewImageUrls());
            }
        } else {
            if (dto.getNewImageUrls() != null) {
                currentImages.addAll(dto.getNewImageUrls());
            }
        }

        product.setImageUrls(currentImages);

        productRepository.save(product);


    }




}
