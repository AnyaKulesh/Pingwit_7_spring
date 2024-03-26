package com.example.springdemo.controller.joke;

import com.example.springdemo.service.joke.JokesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/joke")
public class JokeController {
    private static final Integer JOKES_NUM_LIMIT = 5;
    private final JokesService jokesService;

    public JokeController(JokesService jokesService) {
        this.jokesService = jokesService;
    }

    @GetMapping
    public String getJoke() {
        return jokesService.getRandomJoke();
    }

    @GetMapping("/{count}")
    public List<String> getJokes(@PathVariable(name = "count") Integer count) {
        if (count < 2) {
            count = 1;
        } else if (count > JOKES_NUM_LIMIT) {
            count = JOKES_NUM_LIMIT;
        }
        return jokesService.getRandomJokes(count);
    }
}
