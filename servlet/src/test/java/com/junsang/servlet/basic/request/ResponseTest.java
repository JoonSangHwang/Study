package com.junsang.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ResponseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    @DisplayName("단순 텍스트 응답")
    public void e() throws Exception {

        // Given
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("username", "hello");
        param.add("age", "20");

        // When
        mockMvc.perform(get("/response-header")
                .characterEncoding("UTF-8")
                .accept(MediaType.ALL)
                .params(param)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // Then
    }

    @Test
    @DisplayName("HTML 응답")
    public void f() throws Exception {

        // Given
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("username", "hello");
        param.add("age", "20");

        // When
        mockMvc.perform(get("/response-html")
                .characterEncoding("UTF-8")
                .accept(MediaType.ALL)
                .params(param)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // Then
    }

    @Test
    @DisplayName("JSON 응답")
    public void g() throws Exception {

        // Given
        HelloData sample = HelloData.builder()
                .username("준상")
                .age(29)
                .build();

        // When
        mockMvc.perform(get("/response-json")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .accept(MediaType.ALL)
                .content(objectMapper.writeValueAsString(sample))
        )
                .andExpect(status().isOk())
                .andDo(print());

        // Then
    }


}
