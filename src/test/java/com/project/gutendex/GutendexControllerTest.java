package com.project.gutendex;

import com.project.gutendex.controller.GutendexController;
import com.project.gutendex.entity.Book;
import com.project.gutendex.entity.ResultBooks;
import com.project.gutendex.service.GutendexService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GutendexController.class)
public class GutendexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GutendexService service;

    @Test
    public void testRetrievingBooks() throws Exception {
        ResultBooks resultBooks= new ResultBooks();
        when(service.orderBooks()).thenReturn(resultBooks);

        resultBooks.setResults(List.of(new Book("1","Pride and Prejudice",32),new Book("2","White nights",50)));

        this.mockMvc.perform(get("/downloadCSV")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"))
                .andExpect(content().string(containsString("Pride and Prejudice")));
    }
}
