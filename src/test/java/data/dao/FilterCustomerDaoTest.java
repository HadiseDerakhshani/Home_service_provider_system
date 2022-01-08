package data.dao;

import data.dto.CustomerDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterCustomerDaoTest extends BaseTest {


    @BeforeEach
    void init() {
        customerDao = new CustomerDao();
    }

    @AfterEach
    void after() {
        System.out.println("******* after " + nameMethod + " test method  *******");
    }

    @DisplayName("It is just a null argument")
    @ParameterizedTest
    @CsvSource({"sama,seven,null", ",sama,null,samaseven@gmail.com", "null,seven,samaseven@gmail.com"})
    void givenOneNullArgs_WhenFilter_ThenListCustomerDtoResponseReturn(String name, String family, String email) {
        List<CustomerDto> filter = customerDao.filter(name, family, email);
        Assertions.assertNotNull(filter);
        nameMethod = "It is just a null argument";
    }


    @DisplayName("It is not just a null argument")
    @ParameterizedTest
    @CsvSource({"sama,null,null", "null,seven,null", "null,null,samaseven@gmail.com"})
    void givenAllNotNullArgs_WhenFilter_ThenListCustomerDtoResponseReturn(String name, String family, String email) {
        List<CustomerDto> filter = customerDao.filter(name, family, email);
        Assertions.assertNotNull(filter);
        nameMethod = "It is not just a null argument";
    }

    @DisplayName("Not all arguments are null")
    @ParameterizedTest
    @CsvSource({"sama,seven,samaseven@gmail.com"})
    void givenNotNullAllArgs_WhenFilter_ThenListCustomerDtoResponseReturn(String name, String family, String email) {
        List<CustomerDto> filter = customerDao.filter(name, family, email);
        Assertions.assertNotNull(filter);
        nameMethod = "Not all arguments are null";
    }

    @DisplayName(" All arguments are null")
    @ParameterizedTest
    @CsvSource({"null,null,null"})
    void givenNullAllArgs_WhenFilter_ThenListCustomerDtoResponseReturn(String name, String family, String email) {
        List<CustomerDto> filter = customerDao.filter(name, family, email);
        assertEquals(0, filter.size());
        nameMethod = "All arguments are null";
    }
}
