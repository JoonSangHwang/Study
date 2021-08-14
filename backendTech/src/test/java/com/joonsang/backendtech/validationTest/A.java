package com.joonsang.backendtech.validationTest;

import com.joonsang.backendtech.domain.item.ItemValid;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.*;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class A {

    @Test
    @DisplayName("Bean Validation 테스트")
    public void a() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        ItemValid itemValid = new ItemValid();
        itemValid.setItemName(" ");
        itemValid.setPrice(0);
        itemValid.setQuantity(10000);

        Set<ConstraintViolation<ItemValid>> violations = validator.validate(itemValid);
        for (ConstraintViolation violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation message = " + violation.getMessage());
        }

//        Assertions.assertThat(messageCodes).containsExactly("required.item", "required");
    }
}
