package com.example.spring4shellweb;

import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class VersionController {
    @GetMapping("/spring")
    public String getSpringVersion() {
        return "Spring Framework Version: " + SpringVersion.getVersion();
    }
}
