package com.example.service;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

@Component
public class FizzBuzzService {

    @Get("/fizzbuzz")
    public String fizzBuzz(@Param int i) {
        if (i % 3 == 0 && i % 5 == 0) {
            return "Fizz Buzz";
        } else if (i % 3 == 0) {
            return "Fizz";
        } else if (i % 5 == 0) {
            return "Buzz";
        } else {
            return String.valueOf(i);
        }
    }
}
