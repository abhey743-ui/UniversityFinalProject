package ProductManagement.example.ProductManagementDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ProductUpdateRequestDto {

    private String id;
    private String productName;
    private String brand;
    private String color;
    private String condition;
    private String productType;
    private String category;
    private Double price;
    private Map<String, Object> attributesToAddOrUpdate;
    private List<String> attributesToRemove;


    private List<String> newImageUrls;
    private List<String> removeImageUrls;
    private Boolean replaceAllImages;


    private List<String> fieldsToNullify;
}
