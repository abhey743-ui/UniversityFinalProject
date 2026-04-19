package com.ProductService.SecurityService;

import ch.qos.logback.core.subst.Token;
import com.ProductService.ProductDto.AuthDetails;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class JwtFiltering extends OncePerRequestFilter {

    private final TokenVerification tokenVerification;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getRequestURI().startsWith("/products") || request.getRequestURI().startsWith("/reviews/public")){
                   filterChain.doFilter(request,response);
                   return;
        }


        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        System.out.println("***********************");

        System.out.println(token);

        System.out.println("***********************");


        if(token == null){
            filterChain.doFilter(request,response);
            return;

        }
        Claims claims =  tokenVerification.verification(token);
        Claims internalClaims = tokenVerification.internalVerification(token);
        Claims basketInternalClaim = tokenVerification.BasketInterVerificationToken(token);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if(claims != null){
        if((authentication == null)){


          String  userId =  claims.get("id").toString();
          Long longUserId = Long.parseLong(userId);
            AuthDetails authDetails = new AuthDetails(longUserId);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authDetails,null,null);
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
          filterChain.doFilter(request,response);
          return;
        }}

        if(internalClaims != null){

            if(authentication == null){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(internalClaims,null,List.of(new SimpleGrantedAuthority("INTERNAL")));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                filterChain.doFilter(request,response);
                return;

            }
        }
        if(basketInternalClaim != null){
             if(authentication == null){

                 UsernamePasswordAuthenticationToken usernameToken  = new UsernamePasswordAuthenticationToken(basketInternalClaim,null,List.of(new SimpleGrantedAuthority("INTERNAL")));
                 SecurityContextHolder.getContext().setAuthentication(usernameToken);
                 filterChain.doFilter(request,response);
                 return;
             }

        }

        filterChain.doFilter(request,response);
        return;


    }
}
