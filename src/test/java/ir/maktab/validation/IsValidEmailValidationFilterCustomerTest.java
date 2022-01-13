package ir.maktab.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class IsValidEmailValidationFilterCustomerTest {


    @BeforeEach
    void init() {
    }

    @AfterEach
    void after() {
        System.out.println("******* after test method  *******");
    }

    @DisplayName("Domain email is number")
    @ParameterizedTest
    @CsvSource({"samaseven@gmail.122"})
    void givenNumberForDomain_WhenIsValidEmail_ThenThrowsExceptionResponseReturn(String email) {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                ValidationInfo.isValidEmail(email));
        Assertions.assertEquals("---- email that entered is not valid ----", runtimeException.getMessage());

    }

    @ParameterizedTest
    @CsvSource({"samaseven5gmail.com"})
    void givenNumberForAnnotation_WhenIsValidEmail_ThenThrowsExceptionResponseReturn(String email) {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                ValidationInfo.isValidEmail(email));
        Assertions.assertEquals("---- email that entered is not valid ----", runtimeException.getMessage());

    }

    @ParameterizedTest
    @CsvSource({"samaseven@gmail.com"})
    void givenCorrect_WhenIsValidEmail_ThenTrueResponseReturn(String email) {
        boolean result = ValidationInfo.isValidEmail(email);
        Assertions.assertTrue(result);

    }
}
