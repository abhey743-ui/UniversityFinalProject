package com.user.Security.JwtVerificationFilter;
import com.user.Dto.AuthDto.UserPrincipalAuth;
import com.user.Repository.UserRepository.PermissionRepository;
import com.user.Repository.UserRepository.RoleRepository;
import com.user.Security.SecitityUtilities.JwtToken.JwtToken;
import com.user.Repository.UserRepository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JwtVerificationFilter extends OncePerRequestFilter {

    JwtToken jwtToken;
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    public JwtVerificationFilter(JwtToken jwtToken, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.jwtToken = jwtToken;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");


        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }



        if (token != null) {

            Claims UserClaim = jwtToken.verifyUserToken(token);
            Claims MachineClaim = jwtToken.verifyMachineToken(token);

            if (UserClaim != null) {

                String userName = UserClaim.getSubject();

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null || !authentication.isAuthenticated()) {

                    String idString = UserClaim.get("id", String.class);
                    Long id  = Long.valueOf(idString);
                    List<String> roles = roleRepository.getRoles(id);
                    List<String> permissions = permissionRepository.getPermission(id);

                    Set<GrantedAuthority> UserAuthorities = new HashSet<>();

                    for (String r : roles) {
                        String roleName = "ROLE_" + r;
                        UserAuthorities.add(new SimpleGrantedAuthority(roleName));
                    }
                    for (String p : permissions) {
                        UserAuthorities.add(new SimpleGrantedAuthority(p));
                    }

                    UserPrincipalAuth userPrincipalAuth1 = UserPrincipalAuth.builder()
                            .id(id).userName(userName).build();


                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userPrincipalAuth1, null, UserAuthorities);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);
                    return;
                }

            } else if (MachineClaim != null) {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null || !authentication.isAuthenticated()) {

                    List<GrantedAuthority> authorities =
                            List.of(new SimpleGrantedAuthority("ROLE_INTERNAL_SERVICE"));

                    String serviceName = MachineClaim.get("serviceName", String.class);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(serviceName, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    filterChain.doFilter(request, response);
                    return;
                }

            } else {
                filterChain.doFilter(request, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
