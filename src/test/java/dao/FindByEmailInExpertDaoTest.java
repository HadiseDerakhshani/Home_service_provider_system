package dao;

import org.junit.jupiter.api.*;

public class FindByEmailInExpertDaoTest extends BaseTest {
    @BeforeEach
    void init() {
        expertDao = new ExpertDao();
    }

    @AfterEach
    void after() {
        System.out.println("*******  test  method  successfully *******");
    }

    @Test
    @DisplayName("email is not null for findByEmail method")
    void notNullAsEmail_WhenFindByEmail_ThenExpertResponseReturn() {
        Assertions.assertNotNull(expertDao.findByEmail("samaseven@gmail.com"));
    }

    @Test
    @DisplayName("email is null for findByEmail method")
    void nullAsEmail_WhenFindByEmail_ThenExpertResponseReturn() {
        Assertions.assertNull(expertDao.findByEmail("samaseven@gmail.com"));
    }

}
