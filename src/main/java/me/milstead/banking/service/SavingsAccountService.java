package me.milstead.banking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.milstead.banking.dto.GetSavingsAccountResponse;
import me.milstead.banking.entity.SavingsAccount;
import me.milstead.banking.exception.TooManyAccountsException;
import me.milstead.banking.repository.SavingsAccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SavingsAccountService {

    private final SavingsAccountRepository savingsAccountRepository;

    private final CensorService censorService;

    public SavingsAccount createSavingsAccount(SavingsAccount savingsAccount) {
        censorService.validate(savingsAccount.getNickname());
        if (savingsAccountRepository.countByCustomerName(savingsAccount.getCustomerName()) >= 5) {
            throw new TooManyAccountsException();
        }
        return savingsAccountRepository.save(savingsAccount);
    }


    public Optional<SavingsAccount> get(Long id) {
        return savingsAccountRepository.findById(id);
    }
}
