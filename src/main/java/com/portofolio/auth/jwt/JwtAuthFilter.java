package com.portofolio.auth.jwt;

import static com.portofolio.auth.util.Constants.TOKEN_HEADER;
import static com.portofolio.auth.util.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    @Value("${jwt.access-cookie.name}")
    private String accessTokenCookieName;
    @Value("${jwt.access-cookie.next-auth.name}")
    private String accessTokenCookieNameNextAuth;
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;
    
    private static final List<String> PUBLIC_PATHS = List.of(
    	    "/api/users/signup",
    	    "/api/auth/login",
    	    "/api/auth/refresh"
    	);
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        String path = request.getRequestURI();
        System.out.println("⛳ Request URI: " + path);

        // Lewatkan filter jika path publik
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Ambil token hanya dari header Authorization
        String accessToken = getJwtFromRequest(request);

        if (accessToken == null || !tokenProvider.validateToken(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = tokenProvider.getUsernameFromToken(accessToken);

        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

//    @Override
    protected void doFilterInternal_backup(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	
    	String path = request.getRequestURI();
    	System.out.println("⛳ Request URI: " + path);

        // ✅ Lewatkan filter jika path-nya termasuk public (pakai startsWith untuk aman)
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        
//        String accessToken  = getJwtToken(request, true);
        
//        String accessToken = getJwtFromCookie(request);
//        if (accessToken == null) {
//            accessToken = getJwtFromRequest(request);
//        }
        
        String accessToken = getJwtFromRequest(request);
        if (accessToken == null) {
            accessToken = getJwtFromCookie(request);
        }

        System.out.println("accessToken = "+accessToken);

//        System.out.println("tokenProvider.validateToken(accessToken)) = "+tokenProvider.validateToken(accessToken));
        if(accessToken == null || !tokenProvider.validateToken(accessToken)) {
        	System.out.println("sinikah");
            filterChain.doFilter(request, response);
            return;
        }

        // get username from token
        String username = tokenProvider.getUsernameFromToken(accessToken);
        System.out.println("username xx = "+username);

        if(username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
    
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
    
    private String getJwtToken(HttpServletRequest request, boolean fromCookie) {
        if (fromCookie) return getJwtFromCookie(request);
        return getJwtFromRequest(request);
    }
//    private String getJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader(TOKEN_HEADER);
//
//        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
//            return bearerToken.substring(TOKEN_PREFIX.length());
//        }
//        return null;
//    }
    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null)
            return null;

        for (Cookie cookie : cookies) {
            if (accessTokenCookieNameNextAuth.equals(cookie.getName())) {
//            	if (accessTokenCookieName.equals(cookie.getName())) {
            	System.out.println("cookie.getValue() = "+cookie.getValue());
                return cookie.getValue();
            }
        }
        return null;
    }
}