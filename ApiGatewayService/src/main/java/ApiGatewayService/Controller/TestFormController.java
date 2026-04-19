package ApiGatewayService.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestFormController {

    @GetMapping(value = "/test-form", produces = "text/html")
    public String testForm() {
        return """
                <!DOCTYPE html>
                <html>
                <body>

                <h2>Upload Product</h2>

                <form action="/management/add/product" method="post" enctype="multipart/form-data">

                    <label>Product JSON:</label><br>
                    <textarea name="data" rows="10" cols="50">
{
  "productName": "iPhone 14",
  "brand": "Apple",
  "condition": "New",
  "productType": "Electronics",
  "category": "Mobile",
  "price": 799.99,
  "attributes": {
    "color": "Black",
    "storage": "128GB"
  }
}
                    </textarea>
                    <br><br>

                    <label>Images:</label><br>
                    <input type="file" name="images" multiple>
                    <br><br>

                    <button type="submit">Upload</button>

                </form>

                </body>
                </html>
                """;
    }
}