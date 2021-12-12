package com.project.gutendex.controller;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.project.gutendex.entity.Book;
import com.project.gutendex.entity.ResultBooks;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

@RestController
public class GutendexController {
    @GetMapping(value = "/downloadCSV")
    public static void getBooks() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        PropertyConfigurator.configure("log4j.properties");
        Logger logger = Logger.getLogger(GutendexController.class);

        logger.info("Using RestTemplate to call gutendex api");
        String url = "https://gutendex.com/books?topic=history";
        RestTemplate restTemplate = new RestTemplate();
        ResultBooks results = restTemplate.getForObject(url, ResultBooks.class);

        logger.info("Ordering books in descending order");
        if (results != null) {
            //ordering
            results.getResults().sort((book1, book2)
                    -> book2.getDownload_count().compareTo(
                    book1.getDownload_count()));
        }
        //CSV download
        logger.info("Starting CSV download");
        String outputFileName = "Books.csv";
        try (Writer writer = new FileWriter(outputFileName)) {
            StatefulBeanToCsv<Book> statefulBeanToCsv = new StatefulBeanToCsvBuilder<Book>(writer).build();
            if (results != null) {
                statefulBeanToCsv.write(results.getResults());
            }
        }
    }
}

