package com.project.gutendex.controller;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.project.gutendex.entity.Book;
import com.project.gutendex.entity.ResultBooks;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

@RestController
public class GutendexController {

    @GetMapping(value = "/books")
    public static void getBooks() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String url = "https://gutendex.com/books?topic=history";
        RestTemplate restTemplate = new RestTemplate();

        ResultBooks results = restTemplate.getForObject(url, ResultBooks.class);
        if (results != null) {
            //ordering
            results.getResults().sort((book1, book2)
                    -> book2.getDownload_count().compareTo(
                    book1.getDownload_count()));
        }
        //CSV download
        String outputFileName = "Books.csv";
        try (Writer writer = new FileWriter(outputFileName)) {
            StatefulBeanToCsv<Book> statefulBeanToCsv = new StatefulBeanToCsvBuilder<Book>(writer).build();
            if (results != null) {
                statefulBeanToCsv.write(results.getResults());
            }
        }
    }
}

