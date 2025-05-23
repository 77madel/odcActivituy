package com.odk.Entity.visitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odk.Entity.Utilisateur;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class HttpRequestResponseUtils {

    public HttpRequestResponseUtils() {
    }

    private static final String[] IP_HEADER_CANDIDATES = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

    public static String getClientIpAddress() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "0.0.0.0";
        }

        @SuppressWarnings("null")
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        for (String header : IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && !ipList.isEmpty() && !"unknown".equalsIgnoreCase(ipList)) {
                String ip = ipList.split(",")[0];
                return ip;
            }
        }

        return request.getRemoteAddr();
    }

    public static String getRequestUrl() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        @SuppressWarnings("null")
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request.getRequestURL().toString();
    }

    public static String getRequestUri() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        @SuppressWarnings("null")
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request.getRequestURI();
    }

    public static String getRefererPage() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        @SuppressWarnings("null")
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String referer = request.getHeader("Referer");

        return referer != null ? referer : request.getHeader("referer");
    }

    public static String getPageQueryString() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        @SuppressWarnings("null")
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request.getQueryString();
    }

    public static String getUserAgent() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        @SuppressWarnings("null")
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String userAgent = request.getHeader("User-Agent");

        return userAgent != null ? userAgent : request.getHeader("user-agent");
    }

    public static String getRequestMethod() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        @SuppressWarnings("null")
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request.getMethod();
    }

    public static String getLoggedInUser() {
        String userJson = null;

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ObjectMapper mapper = new ObjectMapper();

            try {
                userJson = mapper.writeValueAsString(utilisateur.getNom() + " " + utilisateur.getPrenom());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return userJson;
        }
        return userJson;
    }
}
