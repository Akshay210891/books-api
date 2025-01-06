package com.netgear.books.booksapi.auth;

import com.netgear.books.booksapi.service.JwtImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.netgear.books.booksapi.entity.User;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuth extends OncePerRequestFilter {

    private final JwtImpl jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException{
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null ){
            filterChain.doFilter(request, response);
            return;
        }
        if(jwtToken.startsWith("Bearer")){
            jwtToken = jwtToken.substring(7);
            String username = jwtService.extractSubject(jwtToken);
            SecurityContext context = SecurityContextHolder.getContext();
            if(context.getAuthentication() == null && username != null){
                User user = (User) userDetailsService.loadUserByUsername(username);
                if(jwtService.isTokenValid(jwtToken)){
                    var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }
    }


}
