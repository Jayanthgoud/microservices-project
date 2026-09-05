package com.main.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE", fallbackFactory = UserServiceFallbackFactory.class)
public interface UserService {

    @GetMapping("/api/v1/users/exists/{id}")
    public Integer isUserExists(@PathVariable Long id);
    
}
