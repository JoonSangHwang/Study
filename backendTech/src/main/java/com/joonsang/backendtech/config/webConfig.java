package com.joonsang.backendtech.config;

import com.joonsang.backendtech.validation.ItemValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {

//    /**
//     * 글로벌 검증기 등록
//     */
//    @Override
//    public Validator getValidator() {
//        return new ItemValidator();
//    }
}
