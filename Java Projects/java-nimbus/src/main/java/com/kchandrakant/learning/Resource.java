package com.kchandrakant.learning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Resource {

    @GetMapping
    public String handle() {
        return "Hello World!";
    }
}
