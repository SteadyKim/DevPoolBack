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
        member.setName("김");
        member.setEmail("rereer5@navom");
        member.setNickName("테디");
        member.setPassword("tae125");

        return member;
    }
}
