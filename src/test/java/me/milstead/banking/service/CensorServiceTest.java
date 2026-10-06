package me.milstead.banking.service;

import me.milstead.banking.exception.CensorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CensorServiceTest {

    @Autowired
    private CensorService censorService;

    @Test
    void testDi() {
        assertNotNull(censorService);
    }

    @Test
    void testValidate1() {
        String str = "My Flax Account";
        assertThrows(CensorException.class, ()->censorService.validate(str));
    }

    @Test
    void testValidate2() {
        String str = "Orange Account";
        assertThrows(CensorException.class, ()->censorService.validate(str));
    }

    @Test
    void testValidate3() {
        String str = "Martin Fowler Account";
        assertDoesNotThrow(()->censorService.validate(str));
    }

    @Test
    void testValidate4() {
        String str = "";
        assertDoesNotThrow(()->censorService.validate(str));
    }

    @Test
    void testValidate5() {
        String str = null;
        assertDoesNotThrow(()->censorService.validate(str));
    }

}