package org.example.springsecurity.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleAllowed {
    String[] value(); // e.g., {"ADMIN", "SALES_OFFICER"}
}


//package org.example.springsecurity.interceptor;
//
//import io.jsonwebtoken.Claims;
//import org.example.springsecurity.annotation.RoleAllowed;
//import org.example.springsecurity.util.JwtUtil;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.lang.reflect.Method;
//import java.util.Arrays;

//@Component
//public class RoleInterceptor implements HandlerInterceptor {
//
//    private final JwtUtil jwtUtil;
//
//    public RoleInterceptor(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        if (!(handler instanceof HandlerMethod)) {
//            return true; // Allow non-method mappings (e.g., static resources)
//        }
//
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Method method = handlerMethod.getMethod();
//
//        RoleAllowed roleAllowed = method.getAnnotation(RoleAllowed.class);
//        if (roleAllowed == null) {
//            roleAllowed = handlerMethod.getBeanType().getAnnotation(RoleAllowed.class);
//        }
//
//        if (roleAllowed == null) {
//            return true; // No role restriction — allow
//        }
//
//        // Extract token
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
//            return false;
//        }
//
//        String token = authHeader.substring(7);
//        Claims claims;
//        try {
//            claims = jwtUtil.validateToken(token);
//        } catch (Exception e) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
//            return false;
//        }
//
//        String userRole = (String) claims.get("role");
//
//        boolean allowed = Arrays.asList(roleAllowed.value()).contains(userRole);
//        if (!allowed) {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
//            return false;
//        }
//
//        return true;
//    }
//}
