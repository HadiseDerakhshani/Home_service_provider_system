package dao;

import model.person.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SaveCustomerDaoTest extends BaseTest {
    CustomerDao customerDao;

    @BeforeEach
    void init() {
        customerDao = new CustomerDao();
    }

    @AfterEach
    void after() {
        System.out.println("******* after " + nameMethod + " method  *******");
    }

    @Test
    void nullAsArgs_WhenSave_ThenExceptionResponseReturn() {
        Customer customer = null;
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                customerDao.save(customer));
        Assertions.assertEquals("SECOND NUMBER IS ZERO ", runtimeException.getMessage());
        nameMethod = "null customer";
    }

    @Test
    void notNullAsArgs_WhenSave_ThenCustomerIdResponseReturn() {
        Customer customer = Customer.builder()
                .firstName("ali")
                .build();
        int save = customerDao.save(customer);
        assertNotEquals(0, save);
        nameMethod = "notNull customer";
    }

    @Test
    void customerInsert_WhenSave_ThenCustomerIdResponseReturn() {
        int idBeforeSave = customerDao.maxId();
        Customer customer = Customer.builder()
                .firstName("ali")
                .build();
        int idAfterSave = customerDao.save(customer);
        assertEquals(idBeforeSave + 1, idAfterSave);
        nameMethod = "insert customer test";
    }
}
