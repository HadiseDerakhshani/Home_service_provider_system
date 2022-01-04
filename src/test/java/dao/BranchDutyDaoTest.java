package dao;

import model.serviceSystem.BranchDuty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BranchDutyDaoTest extends BaseTest {


    static BranchDuty createBranchService() {
        Map<String, String> branchMap = new HashMap<>();
        branchMap.put("kids", " age kids 3-12");
        return BranchDuty.builder()
                .price(12000.0)
                .branchServiceMap(branchMap).build();
    }

    @BeforeEach
    void init() {
        branchDutyDao = new BranchDutyDao();
    }

    @AfterEach
    void after() {
        System.out.println("******* after " + nameMethod + " test method  *******");
    }

    @Test
    void nullAsArgs_WhenSave_ThenExceptionResponseReturn() {
        BranchDuty branchDuty = null;
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                branchDutyDao.save(branchDuty));
        Assertions.assertEquals("branchService is null ", runtimeException.getMessage());
        nameMethod = "null branchService";
    }

    @Test
    void notNullAsArgs_WhenSave_ThenCustomerIdResponseReturn() {
        int save = branchDutyDao.save(createBranchService());
        assertNotEquals(0, save);
        nameMethod = "notNull branchServiceDao";
    }

    @Test
    void branchServiceInsert_WhenSave_ThenMasterServiceIdResponseReturn() {
        int idBeforeSave = branchDutyDao.maxId();
        int idAfterSave = branchDutyDao.save(createBranchService());
        assertEquals(idBeforeSave + 1, idAfterSave);
        nameMethod = "insert branchServiceDao ";
    }
}
