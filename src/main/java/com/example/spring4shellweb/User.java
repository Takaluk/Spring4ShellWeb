package com.example.spring4shellweb;

import java.io.Serializable;

public class User implements Serializable {  // 직렬화 가능하도록 추가
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String email;

    // 기본 생성자 추가 (Spring4Shell 익스플로잇 시 필요)
    public User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
