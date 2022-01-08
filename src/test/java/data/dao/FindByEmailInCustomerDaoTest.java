package data.dao;

import org.junit.jupiter.api.*;

public class FindByEmailInCustomerDaoTest extends BaseTest {
    @BeforeEach
    void init() {
        customerDao = new CustomerDao();
    }

    @AfterEach
    void after() {
        System.out.println("*******  Test  Method  Successfully *******");
    }

    @Test
    @DisplayName("Email Is Not Null For FindByEmail Method")
    void notNullAsEmail_WhenFindByEmail_ThenCustomerResponseReturn() {
        Assertions.assertNotNull(customerDao.findByEmail("samaseven@gmail.com"));
    }

    @Test
    @DisplayName(" Email Is Null For FindByEmail Method")
    void nullAsEmail_WhenFindByEmail_ThenCustomerResponseReturn() {
        Assertions.assertNull(customerDao.findByEmail("samaseven@gmail.com"));
    }

}
