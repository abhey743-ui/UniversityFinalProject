package com.BasketService.BasketSecurity.SecurityUtilities;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class GeneratingInternalToken {

    private String SECRET_KEY = "JDFHSKFHSKLFJASKLFHSDKLHFS9352305725207-5-37@@@@NMDFSKLFJLFJDSLDF";

    private Key SigningKey(){
         return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String getToken(){

        return Jwts.builder().signWith(SigningKey()).setExpiration(new Date(System.currentTimeMillis()*30))
                .claim("service","BASKETSERVICE").compact();
    }
}
