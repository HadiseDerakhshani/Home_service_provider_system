package data.dao;

import data.user.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SaveCustomerDaoTest extends BaseTest {


    @BeforeEach
    void init() {
        customerDao = new CustomerDao();
    }

    @AfterEach
    void after() {
        System.out.println("******* after " + nameMethod + " test method  *******");
    }

    @Test
    void nullAsArgs_WhenSave_ThenExceptionResponseReturn() {
        Customer customer = null;
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                customerDao.save(customer));
        Assertions.assertEquals("Customer is null ", runtimeException.getMessage());
        nameMethod = "null customer";
    }

    @Test
    void notNullAsArgs_WhenSave_ThenCustomerIdResponseReturn() {
        int save = customerDao.save(creatCustomer());
        assertNotEquals(0, save);
        nameMethod = "notNull customer";
    }

    @Test
    void customerInsert_WhenSave_ThenCustomerIdResponseReturn() {
        int idBeforeSave = customerDao.maxId();
        int idAfterSave = customerDao.save(creatCustomer());
        assertEquals(idBeforeSave + 1, idAfterSave);
        nameMethod = "insert customer ";
    }

    Customer creatCustomer() {
        return Customer.builder()
                .firstName("ali")
                .build();

    }
}
