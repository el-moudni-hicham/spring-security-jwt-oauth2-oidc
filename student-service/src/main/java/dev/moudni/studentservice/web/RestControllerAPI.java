package dev.moudni.studentservice.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RestControllerAPI {
    @GetMapping("/students")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Map<String,Object> student(Authentication authentication){
        return Map.of("name","hicham","email", "him@gmail.com",
                "username",authentication.getName(),
                "scope",authentication.getAuthorities());
    }
}
