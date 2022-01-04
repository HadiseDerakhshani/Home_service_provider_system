package validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class IsValidEmailValidationFilterCustomerTest {

    String nameMethod = "";

    @BeforeEach
    void init() {
    }

    @AfterEach
    void after() {
        System.out.println("******* after " + nameMethod + " test method  *******");
    }

    @ParameterizedTest
    @CsvSource({"samaseven@gmail.122"})
    void givenNumberForDomain_WhenIsValidEmail_ThenThrowsExceptionResponseReturn(String email) {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                ValidationFilterCustomer.isValidEmail(email));
        Assertions.assertEquals("---- email that entered is not valid ----", runtimeException.getMessage());
        nameMethod = "number as domain for email";
    }

    @ParameterizedTest
    @CsvSource({"samaseven5gmail.com"})
    void givenNumberForAnnotation_WhenIsValidEmail_ThenThrowsExceptionResponseReturn(String email) {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                ValidationFilterCustomer.isValidEmail(email));
        Assertions.assertEquals("---- email that entered is not valid ----", runtimeException.getMessage());
        nameMethod = "number as @ for email";
    }

    @ParameterizedTest
    @CsvSource({"samaseven@gmail.com"})
    void givenCorrect_WhenIsValidEmail_ThenTrueResponseReturn(String email) {
        boolean result = false;
        String validEmail = ValidationFilterCustomer.isValidEmail(email);
        if (validEmail.equals("true"))
            result = true;
        Assertions.assertTrue(result);
        nameMethod = "correct email for";
    }
}
