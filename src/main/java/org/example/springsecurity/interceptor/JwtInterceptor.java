package org.example.springsecurity.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.*;
import org.example.springsecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Missing or invalid token");
            return false;
        }

        try {
            String token = authHeader.substring(7);
            System.out.println(token);
            Claims claims = jwtUtil.validateToken(token);

            req.setAttribute("username", claims.getSubject());
            req.setAttribute("role", claims.get("role"));
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write("Token invalid or expired");
            return false;
        }

        return true;
    }
}

//String token = authHeader.substring(7);
//Claims claims;
//
//        try {
//claims = jwtUtil.validateToken(token);
//        } catch (Exception e) {
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
//            return false;
//                    }
//
//String role = (String) claims.get("role");
//
//// Example: Only ADMIN can access /api/secure/admin/**
//String path = request.getRequestURI();
//
//        if (path.startsWith("/api/secure/admin") && !role.equals("ADMIN")) {
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied: ADMIN only");
//            return false;
//                    } else if (path.startsWith("/api/secure/store") && !role.equals("STORE_MANAGER")) {
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied: STORE_MANAGER only");
//            return false;
//                    } else if (path.startsWith("/api/secure/production") && !role.equals("PRODUCTION_OFFICER")) {
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied: PRODUCTION_OFFICER only");
//            return false;
//                    } else if (path.startsWith("/api/secure/sales") && !role.equals("SALES_OFFICER")) {
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied: SALES_OFFICER only");
//            return false;
//                    }
//
//                    // Valid token and role matched
//                    return true;