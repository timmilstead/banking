package me.milstead.banking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.milstead.banking.entity.SavingsAccount;
import me.milstead.banking.error.TooManyAccountsException;
import me.milstead.banking.repository.SavingsAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SavingsAccountService {

    private final SavingsAccountRepository savingsAccountRepository;

    private final CensorService censorService;

    @Transactional(readOnly = false)
    public SavingsAccount createSavingsAccount(SavingsAccount savingsAccount) {
        censorService.validate(savingsAccount.getNickname());
        // TODO: This is wide open to race conditions.
        // Prefer a separate customer table with a row lock
        if (savingsAccountRepository.countByCustomerName(savingsAccount.getCustomerName()) >= 5) {
            throw new TooManyAccountsException();
        }
        return savingsAccountRepository.save(savingsAccount);
    }

    @Transactional(readOnly = true)
    public Optional<SavingsAccount> get(Long id) {
        return savingsAccountRepository.findById(id);
    }
}
