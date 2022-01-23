/*
package ir.maktab.service.costomerService;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.service.CustomerService;
import org.junit.jupiter.api.*;

import java.util.List;

public class FindByUserStatusTest extends BaseTest {
    @BeforeEach
    void init() {

        customerService = context.getBean(CustomerService.class);
    }

    @AfterEach
    void after() {
        System.out.println("******* after test method  *******");
    }

    @DisplayName("UserStatus is WAITING_CONFIRM When table is not null ")
    @Test
    void givenStatusToNotNullTable_WhenFindByUserStatus_ThenListCustomerDtoResponseReturn() {
        List<CustomerDto> list = customerService.findByUserStatus(UserStatus.WAITING_CONFIRM);
        Assertions.assertNotNull(list);
    }

    @DisplayName("UserStatus is WAITING_CONFIRM When table is null")
    @Test
    void givenStatusToNullTable_WhenFindByUserStatus_ThenThrowsExceptionResponseReturn() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                customerService.findByUserStatus(UserStatus.WAITING_CONFIRM));
        Assertions.assertEquals(" --- customer is not by userStatus " + UserStatus.NEW.name() +
                " ---", runtimeException.getMessage());
    }

    @DisplayName("UserStatus is NEW")
    @Test
    void givenStatusNew_WhenFindByUserStatus_ThenThrowsExceptionResponseReturn() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                customerService.findByUserStatus(UserStatus.NEW));
        Assertions.assertEquals(" --- customer is not by userStatus " + UserStatus.NEW.name() +
                " ---", runtimeException.getMessage());
    }

    @DisplayName("UserStatus is NONE")
    @Test
    void givenStatusNone_WhenFindByUserStatus_ThenThrowsExceptionResponseReturn() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                customerService.findByUserStatus(UserStatus.NONE));
        Assertions.assertEquals(" --- customer is not by userStatus " + UserStatus.NONE.name() +
                " ---", runtimeException.getMessage());
    }
}
*/
