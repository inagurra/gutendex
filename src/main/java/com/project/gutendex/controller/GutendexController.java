package com.project.gutendex.controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.project.gutendex.service.GutendexService;
import com.project.gutendex.entity.Book;

import com.project.gutendex.entity.ResultBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class GutendexController {
    @Autowired
    GutendexService service;

    @GetMapping(value = "/downloadCSV")
    public void downloadCSV(HttpServletRequest request, HttpServletResponse response)
            throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        ResultBooks results = service.orderBooks();
        String outputFileName = "Books.csv";
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename="+outputFileName);

        OutputStreamWriter streamWriter = new OutputStreamWriter(response.getOutputStream());

        //CSV download
        try (CSVWriter writer = new CSVWriter(streamWriter)) {
            StatefulBeanToCsv<Book> statefulBeanToCsv = new StatefulBeanToCsvBuilder<Book>(writer).build();
            if (results != null) {
                statefulBeanToCsv.write(results.getResults());
                streamWriter.flush();
            }
        }
    }
}

