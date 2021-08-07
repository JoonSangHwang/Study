package com.joonsang.backendtech.messageTest;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest
public class A {

    @Autowired
    MessageSource ms;

    @Test
    @DisplayName("기본 messages 확인")
    public void a() {
        String result = ms.getMessage("hello", null, null);

        Assertions.assertThat(result).isEqualTo("안녕");
    }

    @Test
    @DisplayName("없는 messages 를 사용하였을 경우, 예외 확인")
    public void b() {
        Assertions.assertThatThrownBy(() -> ms.getMessage("no_code", null, Locale.KOREA))
                .isInstanceOf(NoSuchMessageException.class)
        ;
    }

    @Test
    @DisplayName("없는 messages 를 사용하였을 경우, default 메시지를 적용하면 예외가 발생하지 않는다.")
    public void c() {
        String result = ms.getMessage("no_code", null, "웰컴", null);

        Assertions.assertThat(result).isEqualTo("웰컴");
    }

    @Test
    @DisplayName("messages 아규먼트 테스트")
    public void d() {
        String result = ms.getMessage("hello.name", new Object[]{"spring"}, null);

        Assertions.assertThat(result).isEqualTo("안녕 spring");
    }

    @Test
    @DisplayName("messages 국제화 테스트")
    public void e() {
        Assertions.assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        Assertions.assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
