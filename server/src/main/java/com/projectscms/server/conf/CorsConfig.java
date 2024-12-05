// package com.projectscms.server.conf;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class CorsConfig {
//     @Value("${CLIENT_URL}")
//     private String CLIENT_URL;

//     @Bean
//     public WebMvcConfigurer corsConfigurer() {

//         return new WebMvcConfigurer() {
//             @Override
//             public void addCorsMappings(CorsRegistry registry) {
//                 registry.addMapping("/**")
//                         .allowedOrigins(CLIENT_URL)
//                         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                         .allowedHeaders("Content-Type", "Authorization")
//                         .exposedHeaders("Authorization", "Content-Type")
//                         .maxAge(3600); // cache preflight request
//             }
//         };
//     }
// }
