package com.ProductService.Cloudinary;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dv6giuqmc",
                "api_key", "156792319189414",
                "api_secret", "y-U3O1Rj153CefiJDA0F1erUDU8"
        ));
    }
}
