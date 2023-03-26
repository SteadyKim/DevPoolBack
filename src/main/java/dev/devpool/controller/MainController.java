package dev.devpool.controller;

import dev.devpool.domain.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("")
    public String main() {
        return "home";
    }

    @GetMapping("/api")
    public Member getMember() {
        Member member = new Member();
        member.setName("김태우");
        member.setEmail("rereers1125@naver.com");
        member.setNickName("테디");
        member.setPassword("taeu1125");

        return member;
    }
}
