package me.milstead.banking.repository;

import me.milstead.banking.entity.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {

    long countByCustomerName(String customerName);

}
