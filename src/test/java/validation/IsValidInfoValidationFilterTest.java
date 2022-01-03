package validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

public class IsValidInfoValidationFilterTest {
    String nameMethod = "";

    @BeforeEach
    void init() {
    }

    @AfterEach
    void after() {
        System.out.println("******* after " + nameMethod + " test method  *******");
    }

    @ParameterizedTest
    @CsvSource({"0,sama,samaseven@gmail.com"})
    void givenZeroAsName_WhenIsValidInfo_ThenNullInNameResponseReturn(String name, String family, String email) throws IOException {
        String info = name + "," + family + "," + email;
        String result = null;
        String validInfo = ValidationFilter.isValidInfo(info);
        String[] split = validInfo.split(",");
        if (!split[0].equals("null"))
            result = split[0];
        Assertions.assertNull(result);
        nameMethod = "null name for validationFilter";
    }

    @ParameterizedTest
    @CsvSource({"sama,0,samaseven@gmail.com"})
    void givenZeroAsFamily_WhenIsValidInfo_ThenNullInNameResponseReturn(String name, String family, String email) throws IOException {
        String info = name + "," + family + "," + email;
        String result = null;
        String validInfo = ValidationFilter.isValidInfo(info);
        String[] split = validInfo.split(",");
        if (!split[1].equals("null"))
            result = split[1];
        Assertions.assertNull(result);
        nameMethod = "null family for validationFilter";
    }

    @ParameterizedTest
    @CsvSource({"sama,seven,0"})
    void givenZeroAsEmail_WhenIsValidInfo_ThenNullInNameResponseReturn(String name, String family, String email) throws IOException {
        String info = name + "," + family + "," + email;
        String result = null;
        String validInfo = ValidationFilter.isValidInfo(info);
        String[] split = validInfo.split(",");
        if (!split[2].equals("null"))
            result = split[2];
        Assertions.assertNull(result);
        nameMethod = "null email for validationFilter";
    }

    @ParameterizedTest
    @CsvSource({"sama,seven,samaseven@gmail.com,test"})
    void givenZeroAsName_WhenIsValidInfo_ThenNullInNameResponseReturn(String name, String family, String email, String test) {
        String info = name + "," + family + "," + email + "," + test;
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                ValidationFilter.isValidInfo(info));
        Assertions.assertEquals("----entered more than invalid you shod 1 or 2 or 3 case for filter ----", runtimeException.getMessage());
        nameMethod = "the length of string not valid";

    }

}
