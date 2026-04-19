package com.ProductService.ProductService;

import com.ProductService.ProductDto.ProductSearchingDto;
import com.ProductService.ProductModel.ProductModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductSearchingService {

    private final MongoTemplate mongoTemplate;


    public List<ProductModel> searchProducts(ProductSearchingDto req) {

        List<AggregationOperation> ops = new ArrayList<>();


        if (req.getProductName() != null) {
            ops.add(Aggregation.match(
                    Criteria.where("productName").regex(req.getProductName(), "i")
            ));
        }

        if (req.getBrand() != null) {
            ops.add(Aggregation.match(
                    Criteria.where("brand").regex(req.getBrand(), "i")
            ));
        }

        if (req.getProductType() != null) {
            ops.add(Aggregation.match(
                    Criteria.where("productType").regex(req.getProductType(), "i")
            ));
        }

        if (req.getCondition() != null) {
            ops.add(Aggregation.match(Criteria.where("condition").is(req.getCondition())));
        }

        if (req.getCategory() != null) {
            ops.add(Aggregation.match(Criteria.where("category").is(req.getCategory())));
        }
        if (req.getColors() != null && !req.getColors().isEmpty()) {
            ops.add(Aggregation.match(Criteria.where("color").in(req.getColors())));
        }

        if (req.getBrands() != null && !req.getBrands().isEmpty()) {
            ops.add(Aggregation.match(Criteria.where("brand").in(req.getBrands())));
        }
        if (req.getAttributes() != null && !req.getAttributes().isEmpty()) {
            req.getAttributes().forEach((key, values) -> {
                ops.add(Aggregation.match(
                        Criteria.where("attributes." + key).in(values)
                ));
            });
        }

        if (req.getMinPrice() != null && req.getMaxPrice() != null) {
            ops.add(Aggregation.match(
                    Criteria.where("price").gte(req.getMinPrice()).lte(req.getMaxPrice())
            ));
        }

        if (req.getMinRating() != null && req.getMaxRating() != null) {
            ops.add(Aggregation.match(
                    Criteria.where("rating").gte(req.getMinRating()).lte(req.getMaxRating())
            ));
        }

        if (req.getSortBy() != null) {
            Sort.Direction direction =
                    "desc".equalsIgnoreCase(req.getSortDirection())
                            ? Sort.Direction.DESC
                            : Sort.Direction.ASC;

            ops.add(Aggregation.sort(Sort.by(direction, req.getSortBy())));
        }
        int page = req.getPage() == null ? 0 : req.getPage();
        int size = req.getSize() == null ? 10 : req.getSize();

        ops.add(Aggregation.skip((long) page * size));
        ops.add(Aggregation.limit(size));

        Aggregation agg = Aggregation.newAggregation(ops);

        return mongoTemplate.aggregate(agg, "Products", ProductModel.class)
                .getMappedResults();
    }
}
