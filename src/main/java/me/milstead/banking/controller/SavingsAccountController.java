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

// TODO: Security! e.g. JWT - check customer in JWT matches customer number in requests

/**
 * Rest API for creating and getting savings accounts.
 * TODO: For normal projects I prefer to create code from OpenApi specs using the Swagger generator.
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
    public ResponseEntity<GetSavingsAccountResponse> post(@Valid @RequestBody CreateSavingsAccountRequest request) {
        SavingsAccount requestSavingsAccount = SavingsAccount.builder()
                .customerName(request.getCustomerName())
                .nickname(request.getNickname())
                .build();
        // Actually JPA will update requestSavingsAccount too.
        SavingsAccount createdSavingsAccount = savingsAccountService.createSavingsAccount(requestSavingsAccount);

        String formattedStr = String.format("/api/v1/savings-accounts/%d", createdSavingsAccount.getId());
        URI uri = URI.create(formattedStr);
        return ResponseEntity.created(uri).body(domainToDto(createdSavingsAccount));
    }

    /**
     * Get a savings account.
     * @param id ID of the savings account.
     * @return The savings account or 404 if it doesn't exist.
     */
    @GetMapping("/api/v1/savings-accounts/{id}")
    public ResponseEntity<GetSavingsAccountResponse> get(@PathVariable Long id) {
        Optional<SavingsAccount> savingsAccount = savingsAccountService.get(id);
        Optional<GetSavingsAccountResponse> response = savingsAccount.map(this::domainToDto);
        return ResponseEntity.of(response);
    }

    private GetSavingsAccountResponse domainToDto(SavingsAccount savingsAccount) {
        return GetSavingsAccountResponse.builder()
                .id(savingsAccount.getId())
                .accountNumber(savingsAccount.getAccountNumber())
                .customerName(savingsAccount.getCustomerName())
                .nickname(savingsAccount.getNickname())
                .build();
    }
}
