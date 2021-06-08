package com.junsang.servlet.basic.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RequestTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("aaaaaaaa")
    public void a() throws Exception {

        // Given
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("username", "hello");
        param.add("age", "20");

        // When
        mockMvc.perform(get("/request-param")
//                    .contentType()
                    .characterEncoding("UTF-8")
                    .accept(MediaType.ALL)
                    .params(param)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // Then
    }

    @Test
    @DisplayName("bbbbbb")
    public void b() throws Exception {

        // http://localhost:8013/request-header?username=js

        // Given
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("username", "js");

        // When
        mockMvc.perform(get("/request-header")
    //                .contentType(MediaType.ALL)
    //                .characterEncoding("UTF-8")
    //                .accept(MediaType.ALL)
    //                .params(param)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // Then
    }

    @Test
    @DisplayName("단순 텍스트 요청")
    public void c() throws Exception {

        // Given
        String text = "hi hello";

        // When
        mockMvc.perform(post("/request-body-string")
                    .contentType("text/plain")
                    .characterEncoding("UTF-8")
                    .accept(MediaType.ALL)
                    .content(text)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // Then
    }



}