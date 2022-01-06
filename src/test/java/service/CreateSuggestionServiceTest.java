package service;

import model.person.Expert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateSuggestionServiceTest extends BaseServiceTest {

    @BeforeEach
    void init() {
        suggestService = new SuggestionService();
    }

    @AfterEach
    void after() {
        System.out.println("******* after  test method  *******");
    }

    @Test
    void givenStringAndExpert_WhenCreate_ThenSuggestionResponseReturn() {
        String info = "200000,5,14";
        Expert expert = Expert.builder().firstName("ahmad").build();
        Assertions.assertNotNull(suggestService.createSuggest(info, expert));
    }
}
