package dao;

import dto.CustomerDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FilterCustomerDaoTest extends BaseTest {


    @BeforeEach
    void init() {
        customerDao = new CustomerDao();
    }

    @AfterEach
    void after() {
        System.out.println("******* after " + nameMethod + " test method  *******");
    }

    @Test
    void givenNullAsNAme_WhenFilter_ThenListCustomerDtoResponseReturn() {
        List<CustomerDto> filter = customerDao.filter(null, "seven", "samaseven@gmail.com");
        Assertions.assertNotNull(filter);
        nameMethod = "name null for filter";
    }

    @Test
    void givenNullAsFamily_WhenFilter_ThenListCustomerDtoResponseReturn() {
        List<CustomerDto> filter = customerDao.filter("sama", null, "samaseven@gmail.com");
        Assertions.assertNotNull(filter);
        nameMethod = "family null for filter";
    }

    @Test
    void givenNullAsEmail_WhenFilter_ThenListCustomerDtoResponseReturn() {
        List<CustomerDto> filter = customerDao.filter("sama", "seven", null);
        Assertions.assertNotNull(filter);
        nameMethod = "email null for filter";
    }
}
