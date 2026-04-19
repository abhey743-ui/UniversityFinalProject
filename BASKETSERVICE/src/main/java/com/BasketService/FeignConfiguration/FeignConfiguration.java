package com.BasketService.FeignConfiguration;


import com.BasketService.BasketSecurity.SecurityUtilities.GeneratingInternalToken;
import feign.RequestInterceptor;
import feign.template.Template;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {


    @Bean
    public RequestInterceptor requestInterceptor(){

        return Template->{

            GeneratingInternalToken generatingInternalToken = new GeneratingInternalToken();
            String token  =  generatingInternalToken.getToken();
            Template.header("Authorization","Bearer "+token);
        };
    }

}
