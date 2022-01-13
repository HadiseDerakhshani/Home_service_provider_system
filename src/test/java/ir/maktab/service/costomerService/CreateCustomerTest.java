package ir.maktab.service.costomerService;

import ir.maktab.data.model.user.Customer;
import ir.maktab.service.CustomerService;
import org.junit.jupiter.api.*;

public class CreateCustomerTest extends BaseTest {


    @BeforeEach
    void init() {

        customerService = context.getBean(CustomerService.class);
    }

    @AfterEach
    void after() {
        System.out.println("******* after test method  *******");
    }

    @DisplayName("email not null")
    @Test
    void givenEmailNotNull_WhenCreateCustomer_ThenCustomerResponseReturn() {
        Customer customer = customerService.createCustomer("dana", "damavanidi", "danDamavandi@gmail.com",
                "dana1234", "09123456789", 30000.0);
        Assertions.assertNotNull(customer);
    }

    @DisplayName("email is null")
    @Test
    void givenNullEmail_WhenIsCreateCustomer_ThenThrowsExceptionResponseReturn() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                customerService.createCustomer("arash", "abasi", null,
                        "arash1234", "09123451111", 30000.0));
        Assertions.assertEquals("-- email is empty --", runtimeException.getMessage());
    }

    @DisplayName("email is empty")
    @Test
    void givenEmptyEmail_WhenIsCreateCustomer_ThenThrowsExceptionResponseReturn() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                customerService.createCustomer("arash", "abasi", "",
                        "arash1234", "09123451111", 30000.0));
        Assertions.assertEquals("-- email is empty --", runtimeException.getMessage());
    }
}
