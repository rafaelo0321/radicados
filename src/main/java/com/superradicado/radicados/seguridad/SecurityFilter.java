package com.superradicado.radicados.seguridad;


import com.superradicado.radicados.seguridad.servicios.IJWTUtilityService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
public class SecurityFilter extends OncePerRequestFilter {

    private final IJWTUtilityService jwtUtilityService;
    private final AutenticacionService userDetailsCustomService;



    public SecurityFilter(IJWTUtilityService jwtUtilityService, AutenticacionService userDetailsCustomService) {
        this.jwtUtilityService = jwtUtilityService;
        this.userDetailsCustomService = userDetailsCustomService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {

        try{
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = header.substring(7); // Extract the token excluding "Bearer "


            JWTClaimsSet claims = jwtUtilityService.parseJWT(token);

            UserDetails user = this.userDetailsCustomService.loadUserByUsername(claims.getSubject());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(),
                    null,
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        }catch (ServletException | IOException e){
            System.err.println(e.getMessage());
        }

    }

}