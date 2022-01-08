package data.dao;

import data.dto.ExpertDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterExpertDaoTest extends BaseTest {


    @BeforeEach
    void init() {
        expertDao = new ExpertDao();
    }

    @AfterEach
    void after() {
        System.out.println("******* after " + nameMethod + " test method  *******");
    }

    @DisplayName("It is just a null argument")
    @ParameterizedTest
    @CsvSource({"sama,seven,null,moving", ",sama,seven,samaseven@gmail.com,null", "null,seven,samaseven@gmail.com,moving",
            "sama,null,samaseven@gmail.com,moving"})
    void givenOneNullArgs_WhenFilter_ThenListExpertDtoResponseReturn(String name, String family, String email, String duty) {
        List<ExpertDto> filter = expertDao.filter(name, family, email, duty);
        Assertions.assertNotNull(filter);
        nameMethod = "It is just a null argument";
    }


    @DisplayName("It is not just a null argument")
    @ParameterizedTest
    @CsvSource({"sama,null,null,null", "null,seven,null,null", "null,null,samaseven@gmail.com,null", "null,null,null,moving"})
    void givenOneNotNullArgs_WhenFilter_ThenListExpertDtoResponseReturn(String name, String family, String email, String duty) {
        List<ExpertDto> filter = expertDao.filter(name, family, email, duty);
        Assertions.assertNotNull(filter);
        nameMethod = "It is not just a null argument";
    }

    @DisplayName("The name & another argument are not null")
    @ParameterizedTest
    @CsvSource({"sama,seven,null,null", ",sama,null,samaseven@gmail.com,null", "sama,null,null,moving"})
    void givenTwoNotNullArgs_WhenFilter_ThenListExpertDtoResponseReturn(String name, String family, String email, String duty) {
        List<ExpertDto> filter = expertDao.filter(name, family, email, duty);
        Assertions.assertNotNull(filter);
        nameMethod = "The name & another argument are not null";
    }

    @DisplayName("The family & another argument are not null")
    @ParameterizedTest
    @CsvSource({"sama,seven,null,null", ",null,seven,samaseven@gmail.com,null", "null,seven,null,moving"})
    void notNullFamily_WhenFilter_ThenListExpertDtoResponseReturn(String name, String family, String email, String duty) {
        List<ExpertDto> filter = expertDao.filter(name, family, email, duty);
        Assertions.assertNotNull(filter);
        nameMethod = "The family & another argument are not null";
    }

    @DisplayName("The email & another argument are not null")
    @ParameterizedTest
    @CsvSource({"sama,null,samaseven@gmail.com,null", "null,seven,samaseven@gmail.com,null", "null,null,samaseven@gmail.com,moving"})
    void notNullEmail_WhenFilter_ThenListExpertDtoResponseReturn(String name, String family, String email, String duty) {
        List<ExpertDto> filter = expertDao.filter(name, family, email, duty);
        Assertions.assertNotNull(filter);
        nameMethod = "The email & another argument are not null";
    }

    @DisplayName("The duty & another argument are not null")
    @ParameterizedTest
    @CsvSource({"sama,null,null,moving", "null,seven,null,moving", "null,null,samaseven@gmail.com,moving"})
    void notNullDuty_WhenFilter_ThenListExpertDtoResponseReturn(String name, String family, String email, String duty) {
        List<ExpertDto> filter = expertDao.filter(name, family, email, duty);
        Assertions.assertNotNull(filter);
        nameMethod = "The duty & another argument are not null";
    }

    @DisplayName(" All arguments are null")
    @ParameterizedTest
    @CsvSource({"null,null,null,null"})
    void givenNullAllArgs_WhenFilter_ThenListExpertDtoResponseReturn(String name, String family, String email, String duty) {
        List<ExpertDto> filter = expertDao.filter(name, family, email, duty);
        assertEquals(0, filter.size());
        nameMethod = "All arguments are null";
    }

}
