package com.project.gutendex.service;

import com.project.gutendex.controller.GutendexController;
import com.project.gutendex.entity.ResultBooks;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GutendexService {

    public ResultBooks orderBooks() {
        PropertyConfigurator.configure("log4j.properties");
        Logger logger = Logger.getLogger(GutendexController.class);

        logger.info("Calling Gutendex api to retrieve books");

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
        return results;
    }
}
