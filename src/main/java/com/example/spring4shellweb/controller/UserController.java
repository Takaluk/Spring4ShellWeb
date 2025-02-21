package com.example.spring4shellweb.controller;

import com.example.spring4shellweb.User;
import com.example.spring4shellweb.repository.UserRepository;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final ServletContext servletContext;

    public UserController(UserRepository userRepository, ServletContext servletContext) {
        this.userRepository = userRepository;
        this.servletContext = servletContext;
    }

    // 회원가입 페이지
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String name, 
                           @RequestParam String email, 
                           @RequestParam String password, 
                           Model model) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            model.addAttribute("error", "이미 존재하는 이메일입니다.");
            return "register";
        }

        User user = new User(name, password, email);
        userRepository.save(user);

        return "redirect:/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                return "redirect:/dashboard";
            }
        }

        model.addAttribute("error", "Invalid email or password");
        return "login";
    }

    // 대시보드 (로그인 후 이동)
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Object userObj = session.getAttribute("user");

        if (userObj instanceof User) {
            User user = (User) userObj;
            model.addAttribute("user", user);

            // 시스템 정보 추가
            model.addAttribute("springVersion", SpringVersion.getVersion());
            model.addAttribute("javaVersion", System.getProperty("java.version"));
            model.addAttribute("serverInfo", servletContext.getServerInfo());

            return "dashboard";
        }
        return "redirect:/login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // 사용자 정보 수정 기능 추가 (자동 객체 바인딩 포함)
    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute User user, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            return "redirect:/login";
        }

        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        userRepository.save(currentUser);

        model.addAttribute("message", "프로필이 업데이트되었습니다.");
        return "dashboard";
    }

    // WebDataBinder 설정 (취약점 발생 가능)
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields((String[]) null);
    }
}
