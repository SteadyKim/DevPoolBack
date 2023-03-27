package dev.devpool.controller;

import dev.devpool.service.MemberService;
import dev.devpool.service.TeamService;
import dev.devpool.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    private final MemberService memberService;
    private final TeamService teamService;

    private final TestService testService;

    @Autowired
    public MemberController(MemberService memberService, TeamService teamService, TestService testService) {
        this.memberService = memberService;
        this.teamService = teamService;
        this.testService = testService;
    }

    @GetMapping("member/new")
    public String signUp() {
        testService.test();

        return "redirect:/";
    }
}
