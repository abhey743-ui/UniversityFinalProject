package ProductManagement.example.FeignConfiguration;
import ProductManagement.example.Security.SecurityUtilities.JwtToken;
import feign.RequestInterceptor;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import feign.template.Template;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor(JwtToken jwtToken){

        return Template -> {

         String token = jwtToken.getToken("ProductManagement");
         Template.header("Authorization",token);

        };
    }

    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

}
