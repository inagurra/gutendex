package com.project.gutendex.controller;

import net.minidev.json.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class GutendexController {

    @GetMapping(value="/books")
    public String getBooks(){
        String url="https://gutendex.com/books?topic=history";
        RestTemplate restTemplate= new RestTemplate();
        String books = restTemplate.exchange(url, String.class);
        return books;
    }

}
