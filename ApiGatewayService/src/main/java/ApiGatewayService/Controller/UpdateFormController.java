package ApiGatewayService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateFormController {
    @GetMapping(value = "/test-update-form", produces = "text/html")
    public String updateForm() {
        return """
    <!DOCTYPE html>
    <html>
    <body>

    <h2>Update Product</h2>

    <form id="updateForm">

        <label>Product ID:</label><br>
        <input type="text" id="id" required>
        <br><br>

        <h3>Basic Fields (optional)</h3>

        <label>Product Name:</label><br>
        <input type="text" id="productName">
        <br><br>

        <label>Brand:</label><br>
        <input type="text" id="brand">
        <br><br>

        <label>Color:</label><br>
        <input type="text" id="color">
        <br><br>

        <label>Condition:</label><br>
        <input type="text" id="condition">
        <br><br>

        <label>Product Type:</label><br>
        <input type="text" id="productType">
        <br><br>

        <label>Category:</label><br>
        <input type="text" id="category">
        <br><br>

        <label>Price:</label><br>
        <input type="number" step="0.01" id="price">
        <br><br>

        <h3>Attributes</h3>

        <label>Attributes to Add/Update (JSON):</label><br>
        <textarea id="attributesToAddOrUpdate" rows="4" cols="50">
{"color":"Blue","storage":"256GB"}
        </textarea>
        <br><br>

        <label>Attributes to Remove (comma separated):</label><br>
        <input type="text" id="attributesToRemove" placeholder="color,storage">
        <br><br>

        <h3>Images</h3>

        <label>New Image URLs (comma separated):</label><br>
        <textarea id="newImageUrls" rows="3" cols="50"></textarea>
        <br><br>

        <label>Remove Image URLs (comma separated):</label><br>
        <textarea id="removeImageUrls" rows="3" cols="50"></textarea>
        <br><br>

        <label>Replace All Images?</label>
        <input type="checkbox" id="replaceAllImages">
        <br><br>

        <button type="button" onclick="submitUpdate()">Update Product</button>

    </form>

    <script>
        function submitUpdate() {

            function parseList(value) {
                return value.trim() === "" ? null :
                       value.split(",").map(v => v.trim());
            }

            function parseJson(value) {
                try { return JSON.parse(value); }
                catch { return null; }
            }

        
            const priceInput = document.getElementById("price").value.trim();
            const price = priceInput === "" ? null : parseFloat(priceInput);

            const data = {
                id: document.getElementById("id").value,
                productName: document.getElementById("productName").value || null,
                brand: document.getElementById("brand").value || null,
                color: document.getElementById("color").value || null,
                condition: document.getElementById("condition").value || null,
                productType: document.getElementById("productType").value || null,
                category: document.getElementById("category").value || null,
                price: price,

                attributesToAddOrUpdate: parseJson(document.getElementById("attributesToAddOrUpdate").value),
                attributesToRemove: parseList(document.getElementById("attributesToRemove").value),

                newImageUrls: parseList(document.getElementById("newImageUrls").value),
                removeImageUrls: parseList(document.getElementById("removeImageUrls").value),
                replaceAllImages: document.getElementById("replaceAllImages").checked
            };

            fetch("/management/update/product", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            })
            .then(res => res.text())
            .then(result => alert("Update Response: " + result))
            .catch(err => alert("Error: " + err));
        }
    </script>

    </body>
    </html>
    """;
    }

}
