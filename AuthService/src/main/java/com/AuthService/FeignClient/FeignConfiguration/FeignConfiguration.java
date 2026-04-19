package com.AuthService.FeignClient.FeignConfiguration;


import com.AuthService.FeignClient.FeignClientUtilities.ServiceTokenProvider;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration   {

    @Bean
    public RequestInterceptor serviceAuthInterceptor(ServiceTokenProvider serviceTokenProvider) {
        return template -> {
            String token = serviceTokenProvider.getServiceToken();
            template.header("Authorization", token);
        };
    }
}
