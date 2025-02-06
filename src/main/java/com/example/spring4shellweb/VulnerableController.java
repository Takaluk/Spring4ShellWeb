package com.example.spring4shellweb;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class VulnerableController {

    @RequestMapping("/vulnerable")
    public Map<String, String> vulnerable(@RequestParam Map<String, String> params) {
        return params;
    }
}
