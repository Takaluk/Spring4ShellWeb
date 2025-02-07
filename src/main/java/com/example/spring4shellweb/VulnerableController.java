package com.example.spring4shellweb;

import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("/vulnerable")
public class VulnerableController {

    private final ServletContext servletContext;

    public VulnerableController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 특정 필드 차단 해제 -> 취약점 발생 가능
        binder.setDisallowedFields((String[]) null);
    }

    @PostMapping("/user")
    public String updateUser(@ModelAttribute User user, Model model) {
        model.addAttribute("message", "Updated user: " + user.getName() + " - " + user.getEmail());
        return "vulnerable";
    }

    @GetMapping
    public String vulnerablePage(Model model) {
        // 시스템 환경 정보 가져오기
        String springVersion = SpringVersion.getVersion();
        String javaVersion = System.getProperty("java.version");
        String serverInfo = servletContext.getServerInfo();

        model.addAttribute("springVersion", springVersion);
        model.addAttribute("javaVersion", javaVersion);
        model.addAttribute("serverInfo", serverInfo);
        model.addAttribute("message", "This page is intentionally vulnerable for testing Spring4Shell.");
        
        return "vulnerable";
    }

    @PostMapping
    public String handlePost(@RequestParam String input, Model model) {
        model.addAttribute("message", "Received input: " + input);
        return "vulnerable";
    }
}
