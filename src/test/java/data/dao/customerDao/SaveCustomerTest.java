package data.dao.customerDao;

import data.dao.BaseDaoTest;
import data.dao.CustomerDao;
import data.model.enums.UserStatus;
import data.model.user.Customer;
import org.junit.jupiter.api.*;

public class SaveCustomerTest extends BaseDaoTest {
    CustomerDao customerDao;

    @BeforeEach
    void init() {
        customerDao = getContext().getBean(CustomerDao.class);
    }

    @AfterEach
    void after() {
        System.out.println("******* after  test method  *******");
    }

    @Test
    @DisplayName("Save Customer")
    void givenCustomerAsArgs_WhenSave_ThenNotNullResponseReturn() {
        Customer customer = createCustomer("ali", "amery", "aliamery@gmail.com", "ali1360", "09112345678",
                200000, UserStatus.NEW);
        Assertions.assertNotNull(customerDao.save(customer));
    }

    @Test
    @DisplayName("Save Customer")
    void givenNullAsArgs_WhenSave_ThenNotNullResponseReturn() {
        Assertions.assertNull(customerDao.save(null));
    }

}
