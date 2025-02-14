package com.example.spring4shellweb.controller;

import com.example.spring4shellweb.User;
import com.example.spring4shellweb.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
            User user = optionalUser.get(); // Optional에서 User 객체 추출
            if (user.getPassword().equals(password)) { // 비밀번호 검증
                session.setAttribute("user", user); // 세션 저장
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
            User user = (User) userObj; // 안전한 캐스팅
            model.addAttribute("user", user);
            return "dashboard";
        }

        return "redirect:/login"; // 로그인 안 된 경우 다시 로그인 페이지로 이동
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
