package me.milstead.banking.controller;

import lombok.extern.slf4j.Slf4j;
import me.milstead.banking.entity.SavingsAccount;
import me.milstead.banking.service.SavingsAccountService;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test the savings account controller.
 * N.B. We don't test offensive nicknames in this class because it is service and not controller functionality.
 */
@WebMvcTest(SavingsAccountController.class)
@Slf4j
class SavingsAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SavingsAccountService savingsAccountService;

    @Test
    void testCreate() throws Exception {
        when(savingsAccountService.createSavingsAccount(any(SavingsAccount.class))).thenAnswer(
                (invocation)->{
                    SavingsAccount savingsAccount = invocation.getArgument(0);
                    savingsAccount.setId(1L);
                    return savingsAccount;
                }
        );
        mockMvc.perform(post("/api/v1/savings-accounts")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content("""
                                {"customerName": "John Smith", "nickname": "Holiday Savings"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/savings-accounts/1"))
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$.customerName").value("John Smith"))
                .andExpect(jsonPath("$.nickname").value("Holiday Savings"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.accountNumber").isNotEmpty());
    }

    @Test
    void testMissingCustomerNameCreate() throws Exception {
        when(savingsAccountService.createSavingsAccount(any(SavingsAccount.class))).thenAnswer(
                (invocation)->{
                    SavingsAccount savingsAccount = invocation.getArgument(0);
                    savingsAccount.setId(1L);
                    return savingsAccount;
                }
        );
        mockMvc.perform(post("/api/v1/savings-accounts")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content("""
                                {"customerName": "", "nickname": "Nicky"}
                                """))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.fieldErrors.customerName").value("must not be blank"));
    }

    @Test
    void testShortNicknameCreate() throws Exception {
        when(savingsAccountService.createSavingsAccount(any(SavingsAccount.class))).thenAnswer(
                (invocation)->{
                    SavingsAccount savingsAccount = invocation.getArgument(0);
                    savingsAccount.setId(1L);
                    return savingsAccount;
                }
        );
        mockMvc.perform(post("/api/v1/savings-accounts")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content("""
                                {"customerName": "John Smith", "nickname": "Tim"}
                                """))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.fieldErrors.nickname").value("size must be between 5 and 30"));
    }

    @Test
    void testLongNicknameCreate() throws Exception {
        when(savingsAccountService.createSavingsAccount(any(SavingsAccount.class))).thenAnswer(
                (invocation)->{
                    SavingsAccount savingsAccount = invocation.getArgument(0);
                    savingsAccount.setId(1L);
                    return savingsAccount;
                }
        );
        mockMvc.perform(post("/api/v1/savings-accounts")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content("""
                                {"customerName": "John Smith", "nickname": "This is a very long nickname indeed and should create an error"}
                                """))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.fieldErrors.nickname").value("size must be between 5 and 30"));
    }

    @Test
    void testGet() throws Exception {
        SavingsAccount testSavingsAccount = getTestSavingsAccount();
        when(savingsAccountService.get(any())).thenReturn(Optional.of(testSavingsAccount));
        mockMvc.perform(get("/api/v1/savings-accounts/{id}", 1L)
                        .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$.customerName").value(testSavingsAccount.getCustomerName()))
                .andExpect(jsonPath("$.nickname").value(testSavingsAccount.getNickname()))
                .andExpect(jsonPath("$.id").value(testSavingsAccount.getId().toString()))
                .andExpect(jsonPath("$.accountNumber").value(testSavingsAccount.getAccountNumber().toString()));
    }

    private SavingsAccount getTestSavingsAccount() {
        return SavingsAccount.builder()
                .accountNumber(UUID.randomUUID())
                .id(1L)
                .customerName("John Smith")
                .nickname("Nicky")
                .build();
    }

}