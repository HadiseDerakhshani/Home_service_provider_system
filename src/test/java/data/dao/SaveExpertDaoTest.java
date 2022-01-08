package data.dao;

import data.user.Expert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SaveExpertDaoTest extends BaseTest {
    @BeforeEach
    void init() {
        expertDao = new ExpertDao();
    }

    @AfterEach
    void after() {
        System.out.println("******* after " + nameMethod + " test method  *******");
    }

    @Test
    void nullAsArgs_WhenSave_ThenExceptionResponseReturn() {
        Expert expert = null;
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                expertDao.save(expert));
        Assertions.assertEquals("Customer is null ", runtimeException.getMessage());
        nameMethod = "null expert";
    }

    @Test
    void notNullAsArgs_WhenSave_ThenExpertIdResponseReturn() {
        int save = expertDao.save(createExpert());
        assertNotEquals(0, save);
        nameMethod = "notNull expert";
    }

    @Test
    void expertInsert_WhenSave_ThenExpertIdResponseReturn() {
        int idBeforeSave = expertDao.maxId();
        int idAfterSave = expertDao.save(createExpert());
        assertEquals(idBeforeSave + 1, idAfterSave);
        nameMethod = "insert expert ";
    }

    Expert createExpert() {
        return Expert.builder()
                .firstName("ali").build();
    }
}
