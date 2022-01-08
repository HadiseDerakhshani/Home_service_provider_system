package data.dao;

import data.serviceSystem.Service;
import data.serviceSystem.SubService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceDaoTest extends BaseTest {

    static Service createMasterService() {
        List<SubService> branchList = new ArrayList<>();
        branchList.add(SubServiceDaoTest.createBranchService());
        return Service.builder()
                .name("nursing")
                .subServiceList(branchList)
                .build();

    }

    @BeforeEach
    void init() {
        serviceDao = new ServiceDao();
    }

    @AfterEach
    void after() {
        System.out.println("******* after " + nameMethod + " test method  *******");
    }

    @Test
    void nullAsArgs_WhenSave_ThenExceptionResponseReturn() {
        Service service = null;
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                serviceDao.save(service));
        Assertions.assertEquals("masterService is null ", runtimeException.getMessage());
        nameMethod = "null masterDuty";
    }

    @Test
    void notNullAsArgs_WhenSave_ThenCustomerIdResponseReturn() {
        int save = serviceDao.save(createMasterService());
        assertNotEquals(0, save);
        nameMethod = "notNull masterDutyDao";
    }

    @Test
    void masterServiceInsert_WhenSave_ThenMasterServiceIdResponseReturn() {
        int idBeforeSave = serviceDao.maxId();
        int idAfterSave = serviceDao.save(createMasterService());
        assertEquals(idBeforeSave + 1, idAfterSave);
        nameMethod = "insert masterDutyDao ";
    }

    @ParameterizedTest
    @CsvSource({"moving"})
    void givenNameRepeated_WhenFindByName_ThenTrueResponseReturn(String name) {
        assertTrue(serviceDao.findByName(name));
        nameMethod = "find by name ";
    }

    @ParameterizedTest
    @CsvSource({"moving"})
    void givenNameNotRepeated_WhenFindByName_ThenTrueResponseReturn(String name) {
        assertFalse(serviceDao.findByName(name));
        nameMethod = "find by name ";
    }
}
