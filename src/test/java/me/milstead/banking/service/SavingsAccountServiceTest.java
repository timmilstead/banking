package me.milstead.banking.service;

import jakarta.validation.ConstraintViolationException;
import me.milstead.banking.entity.SavingsAccount;
import me.milstead.banking.exception.CensorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SavingsAccountServiceTest {

    @Autowired
    private SavingsAccountService savingsAccountService;

    @Test
    void testDI() {
        assertNotNull(savingsAccountService);
    }

    @Test
    void testCreateWithInvalidCustomerName() {
        SavingsAccount savingsAccount = SavingsAccount.builder()
                .customerName(null)
                .nickname(null)
                .build();
        assertThrows(ConstraintViolationException.class, ()->savingsAccountService.createSavingsAccount(savingsAccount));
    }

    @Test
    void testCreateWithOffensiveNickname() {
        SavingsAccount savingsAccount = SavingsAccount.builder()
                .customerName("John Smith")
                .nickname("Flax Account")
                .build();
        assertThrows(CensorException.class, ()->savingsAccountService.createSavingsAccount(savingsAccount));
    }
}