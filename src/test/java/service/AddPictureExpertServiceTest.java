package service;

import data.user.Expert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddPictureExpertServiceTest {
    String nameMethod = "";
    ExpertService expertService;

    @BeforeEach
    void init() {
        expertService = new ExpertService();
    }

    @AfterEach
    void after() {
        System.out.println("******* after " + nameMethod + " test method  *******");
    }

    @Test
    void givenPictureByteLess300Kb_WhenAddPicture_ThenTrueResponseReturn() {
        Expert expert = expertService.addPicture(createExpert(), "D:\\picture\\New folder\\New folder (5)\\1338210614634.jpg");
        byte[] image = expert.getImage();
        Assertions.assertNotNull(image);
    }

    @Test
    void givenPictureByteMoreThan300Kb_WhenAddPicture_ThenTrueResponseReturn() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () ->
                expertService.addPicture(createExpert(), "D:\\picture\\New folder\\New folder (5)\\1338210614634.jpg"));
        Assertions.assertEquals("---- the byte of picture is more than 300 Kilobyte----", runtimeException.getMessage());
        nameMethod = "out of bond byte";
    }

    Expert createExpert() {
        return Expert.builder()
                .firstName("ali").build();
    }
}
