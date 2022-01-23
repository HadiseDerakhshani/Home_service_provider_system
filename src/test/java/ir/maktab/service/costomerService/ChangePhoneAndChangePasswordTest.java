/*
package ir.maktab.service.costomerService;

import ir.maktab.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChangePhoneAndChangePasswordTest extends BaseTest {
    @BeforeEach
    void init() {

        customerService = context.getBean(CustomerService.class);
    }

    @AfterEach
    void after() {
        System.out.println("******* after test method  *******");
    }

 */
/*  @DisplayName("change phoneNumber")
    @Test
    void givenNewPhoneNumber_WhenChangePhoneNumber_ThenTrueResponseReturn() {
        Customer customer = customerService.createCustomer("dana", "damavanidi", "danDamavandi@gmail.com",
                "dana1234", "09112564444", 30000.0);
        String phoneNew = "09112561234";
        customerService.changePhoneNumber(customer, phoneNew);
        Customer customerUpdated = customerService.findByEmail(customer.getEmail());
        Assertions.assertEquals(customerUpdated.getPhoneNumber(), phoneNew);
    }*//*


   */
/* @DisplayName("change password")
    @Test
    void givenNewPassword_WhenChangePassword_ThenTrueExceptionResponseReturn() {
        Customer customer = customerService.createCustomer("dana", "damavanidi", "danDamavandi@gmail.com",
                "dana1234", "09112564444", 30000.0);
        String newPass = "12dana12";
        customerService.changePassword(customer, newPass);
        Customer customerUpdated = customerService.findByEmail(customer.getEmail());
        Assertions.assertEquals(customerUpdated.getPassword(), newPass);
    }
*//*

}
*/
