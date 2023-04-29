package dev.devpool.controller;

import dev.devpool.domain.Member;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {

    @GetMapping("")
    public String main() {
        return "home";
    }

}
