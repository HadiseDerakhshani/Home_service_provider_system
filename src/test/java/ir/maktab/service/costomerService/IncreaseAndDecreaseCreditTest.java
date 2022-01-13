package ir.maktab.service.costomerService;

import ir.maktab.data.model.user.Customer;
import ir.maktab.service.CustomerService;
import org.junit.jupiter.api.*;

public class IncreaseAndDecreaseCreditTest extends BaseTest {
    Customer customer = create("arash", "solaty", "arashsolaty@gmail.com",
            "arash1234", "09123456789", 30000.0);

    @BeforeEach
    void init() {

        customerService = context.getBean(CustomerService.class);

    }

    @AfterEach
    void after() {
        System.out.println("******* after test method  *******");
    }

    @DisplayName("new amount to IncreaseCredit method")
    @Test
    void givenAmount_WhenIncreaseCredit_ThenTrueResponseReturn() {
        double amount = customer.getCredit() * 2;
        double result = customer.getCredit() + amount;
        customerService.increaseCredit(customer, amount);
        Customer customerUpdated = customerService.findByEmail(customer.getEmail());
        Assertions.assertEquals(customerUpdated.getCredit(), result);
    }

    @DisplayName("new amount to DecreaseCredit method")
    @Test
    void givenAmount_WhenDecreaseCredit_ThenTrueResponseReturn() {
        double amount = customer.getCredit() - (customer.getCredit() / 3);
        double result = customer.getCredit() - amount;
        customerService.decreaseCredit(customer, amount);
        Customer customerUpdated = customerService.findByEmail(customer.getEmail());
        Assertions.assertEquals(customerUpdated.getCredit(), result);
    }
}
