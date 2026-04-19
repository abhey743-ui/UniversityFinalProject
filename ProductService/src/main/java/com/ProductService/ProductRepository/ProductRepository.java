package com.ProductService.ProductRepository;

import com.ProductService.ProductModel.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends MongoRepository<ProductModel,String > {

    List<ProductModel> findByIdIn(Set<String> ids);
}
