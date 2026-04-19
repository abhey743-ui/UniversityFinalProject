package ApiGatewayService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestBuyController {

    @GetMapping(value = "/test", produces = "text/html")
    public String testForm() {
        return """
        <!DOCTYPE html>
        <html>
        <body>

        <h2>Buy Product Payment Form</h2>

        <form id="paymentForm">

            <label>Email:</label><br>
            <input type="email" id="email" required>
            <br><br>

            <label>Address:</label><br>
            <input type="text" id="address" required>
            <br><br>

            <label>Post Code:</label><br>
            <input type="text" id="postCode" required>
            <br><br>

            <label>City:</label><br>
            <input type="text" id="city" required>
            <br><br>

            <label>Card Holder Name:</label><br>
            <input type="text" id="cardHolderName" required>
            <br><br>

            <label>Expiry Date:</label><br>
            <input type="text" id="expiryDate" placeholder="MM/YY" required>
            <br><br>

            <label>VCC:</label><br>
            <input type="password" id="vcc" required>
            <br><br>

            <label>Product IDs (comma separated):</label><br>
            <textarea id="productId" rows="3" cols="40">123,456,789</textarea>
            <br><br>

            <label>Amount:</label><br>
            <input type="number" step="0.01" id="amount" required>
            <br><br>

            <button type="button" onclick="submitPayment()">Pay Now</button>

        </form>

        <script>
            function submitPayment() {

                const productIds = document.getElementById("productId").value
                    .split(",")
                    .map(id => id.trim());

                const data = {
                    email: document.getElementById("email").value,
                    address: document.getElementById("address").value,
                    postCode: document.getElementById("postCode").value,
                    city: document.getElementById("city").value,
                    cardHolderName: document.getElementById("cardHolderName").value,
                    expiryDate: document.getElementById("expiryDate").value,
                    vcc: document.getElementById("vcc").value,
                    productId: productIds,
                    amount: parseFloat(document.getElementById("amount").value)
                };

                // ⭐ THIS IS THE CORRECT URL ⭐
                fetch("/buy/product/payment", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(data)
                })
                .then(res => res.json())
                .then(result => alert(result.message))
                .catch(err => alert("Error: " + err));
            }
        </script>

        </body>
        </html>
        """;
    }
}
