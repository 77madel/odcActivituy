package com.odk.securityConfig;

import com.odk.Entity.Jwt;
import com.odk.Service.Interface.Service.JwtUtile;
import com.odk.Service.Interface.Service.UtilisateurService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private JwtUtile jwtUtile;
    private UtilisateurService utilisateurService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        Jwt tokenDansLaBDD = null;
        String username = null;
        boolean isTokenExpired = true;

        try {
            //Bearer eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1hZG91QGdtYWlsLmNvbSIsIm5vbSI6Ik1hZG91IEtPTkUifQ.pr9Vp6yPseKKHcceZuckzulfwPjeU9GYa_nWYJXNw9w
            final String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
                tokenDansLaBDD = this.jwtUtile.tokenByValue(token);
                isTokenExpired = jwtUtile.isTokenExpired(token);
                username = jwtUtile.extractUsername(token);
            }
            if (
                    !isTokenExpired
                            && tokenDansLaBDD.getUtilisateur().getEmail().equals(username)
                            && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = utilisateurService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

}
