/*
package ir.maktab.service.costomerService;

import ir.maktab.data.model.user.Customer;
import ir.maktab.service.CustomerService;
import org.junit.jupiter.api.*;

public class SaveTest extends BaseTest {
    @BeforeEach
    void init() {

        customerService = context.getBean(CustomerService.class);
    }

    @AfterEach
    void after() {
        System.out.println("******* after test method  *******");
    }

    @DisplayName("new email")
    @Test
    void givenNewEmail_WhenCreateCustomer_ThenCustomerResponseReturn() {
        Customer customer = create("ana", "danesh", "anahana@gmail.com",
                "aana1234", "09123456789", 30000.0);
        Customer save = customerService.save(customer);
        Assertions.assertEquals(customer, save);
    }

    @DisplayName("Duplicate email")
    @Test
    void givenDuplicateEmail_WhenIsCreateCustomer_ThenThrowsExceptionResponseReturn() {
        Customer customer = create("arash", "solaty", "arashsolaty@gmail.com",
                "arash1234", "09123456789", 30000.0);
        customerService.save(customer);
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                customerService.save(create("zahra", "abasi", customer.getEmail(),
                        "zahra1234", "09123451111", 30000.0)));
        Assertions.assertEquals("-- Customer is exit for this email --", runtimeException.getMessage());
    }

}
*/
