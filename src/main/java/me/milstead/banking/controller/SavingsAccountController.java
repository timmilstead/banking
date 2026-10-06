package me.milstead.banking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.milstead.banking.dto.CreateSavingsAccountRequest;
import me.milstead.banking.dto.GetSavingsAccountResponse;
import me.milstead.banking.entity.SavingsAccount;
import me.milstead.banking.service.SavingsAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.Optional;

/**
 * Rest API for creating and getting savings accounts.
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class SavingsAccountController {

    private final SavingsAccountService savingsAccountService;

    /**
     * Create a new savings account.
     * @param request The POST request containing the customer name and optional account nickname.
     * @return 201 i
     */
    @PostMapping("/api/v1/savings-accounts")
    public ResponseEntity<Void> post(@Valid @RequestBody CreateSavingsAccountRequest request) {
        SavingsAccount savingsAccount = SavingsAccount.builder()
                .customerName(request.getCustomerName())
                .nickname(request.getNickname())
                .build();
        SavingsAccount savedSavingsAccount = savingsAccountService.createSavingsAccount(savingsAccount);
        String formattedStr = String.format("/api/v1/savings-accounts/%d", savedSavingsAccount.getId());
        URI uri = URI.create(formattedStr);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/api/v2/savings-account/{id}")
    public ResponseEntity<GetSavingsAccountResponse> get(@PathVariable Long id) {
        Optional<SavingsAccount> savingsAccount = savingsAccountService.get(id);
        Optional<GetSavingsAccountResponse> response = savingsAccount.map(this::convert);
        return ResponseEntity.of(response);
    }

    private GetSavingsAccountResponse convert(SavingsAccount savingsAccount) {
        return GetSavingsAccountResponse.builder()
                .id(savingsAccount.getId())
                .accountNumber(savingsAccount.getAccountNumber())
                .customerName(savingsAccount.getCustomerName())
                .nickname(savingsAccount.getNickname())
                .build();
    }
}
