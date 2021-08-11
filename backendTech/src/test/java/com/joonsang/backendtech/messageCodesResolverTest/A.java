package com.joonsang.backendtech.messageCodesResolverTest;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ObjectError;

@RunWith(SpringRunner.class)
@SpringBootTest
public class A {

    MessageCodesResolver mcr = new DefaultMessageCodesResolver();

    @Test
    @DisplayName("Object Error 메시지 테스트")
    public void a() {
        String[] messageCodes = mcr.resolveMessageCodes("required", "item");

        for(String messageCode : messageCodes) {
            // messageCode: required.item
            // messageCode: required
            System.out.println("messageCode: " + messageCode);
        }
        //== new ObjectError("item", new String[]{"required.item", "required"});

        Assertions.assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    @DisplayName("Field Error 메시지 테스트")
    public void b() {
        String[] messageCodes = mcr.resolveMessageCodes("required", "item", "itemName", String.class);

        for(String messageCode : messageCodes) {
            // messageCode: required.item.itemName
            // messageCode: required.itemName
            // messageCode: required.java.lang.String
            // messageCode: required
            System.out.println("messageCode: " + messageCode);
        }
        //== new FieldError() 에서 codes 파라미터 값에 messageCodes 가 들어간다. (4개)

        Assertions.assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
    }
}
