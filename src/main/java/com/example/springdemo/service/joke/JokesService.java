package com.example.springdemo.service.joke;

import com.example.springdemo.exception.JokeAPIException;
import com.example.springdemo.service.joke.JokesApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class JokesService {
    private final String url;
    private final RestTemplate restTemplate;


    public JokesService(@Value("${pingwit.jokes.url}") String url,
                        RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public String getRandomJoke() {
        JokesApiResponse response = restTemplate.getForObject(url, JokesApiResponse.class);

        if (response == null) {
            throw new JokeAPIException("Unable to fetch a joke. Response is null");
        }
        return response.getSetup() + " - " + response.getPunchline();
    }

    public List<String> getRandomJokes(Integer count) {

        List<String> jokes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            jokes.add(getRandomJoke());
        }
        return jokes;
    }
}
