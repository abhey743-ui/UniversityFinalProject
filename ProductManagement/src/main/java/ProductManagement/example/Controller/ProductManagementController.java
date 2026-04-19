package ProductManagement.example.Controller;
import ProductManagement.example.ProductManagementDto.ProductInfoAddDto;
import ProductManagement.example.ProductManagementDto.ProductUpdateRequestDto;
import ProductManagement.example.Service.ProductManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;



@RestController
@AllArgsConstructor
public class ProductManagementController {

    private final ProductManagementService productManagementService;


    @PostMapping(value = "/add/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(
            @RequestPart("data") String productJson,
            @RequestPart("images") List<MultipartFile> images) throws Exception {

       productManagementService.addProduct(productJson, images);

        return ResponseEntity.ok("Product uploaded with " + images.size() + " images");
    }


    @GetMapping("/delete/product/{id}")
    public HttpStatus deleteProduct(@PathVariable String id){
               productManagementService.deleteProduct(id);
               return HttpStatus.OK;
    }

    @PostMapping("/update/product")
    public HttpStatus updateProduct(@RequestBody  ProductUpdateRequestDto productUpdateRequestDto){

                  productManagementService.updateProduct(productUpdateRequestDto);
                  return HttpStatus.OK;
    }

}
