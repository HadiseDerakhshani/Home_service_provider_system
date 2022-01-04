package dao;

import model.serviceSystem.BranchDuty;
import model.serviceSystem.MasterDuty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MasterDutyDaoTest extends BaseTest {

    static MasterDuty createMasterService() {
        List<BranchDuty> branchList = new ArrayList<>();
        branchList.add(BranchDutyDaoTest.createBranchService());
        return MasterDuty.builder()
                .name("nursing")
                .branchDutyList(branchList)
                .build();

    }

    @BeforeEach
    void init() {
        masterDutyDao = new MasterDutyDao();
    }

    @AfterEach
    void after() {
        System.out.println("******* after " + nameMethod + " test method  *******");
    }

    @Test
    void nullAsArgs_WhenSave_ThenExceptionResponseReturn() {
        MasterDuty masterDuty = null;
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                masterDutyDao.save(masterDuty));
        Assertions.assertEquals("masterService is null ", runtimeException.getMessage());
        nameMethod = "null masterService";
    }

    @Test
    void notNullAsArgs_WhenSave_ThenCustomerIdResponseReturn() {
        int save = masterDutyDao.save(createMasterService());
        assertNotEquals(0, save);
        nameMethod = "notNull masterServiceDao";
    }

    @Test
    void masterServiceInsert_WhenSave_ThenMasterServiceIdResponseReturn() {
        int idBeforeSave = masterDutyDao.maxId();
        int idAfterSave = masterDutyDao.save(createMasterService());
        assertEquals(idBeforeSave + 1, idAfterSave);
        nameMethod = "insert masterServiceDao ";
    }
}
