package me.milstead.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for returning savings accounts.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetSavingsAccountResponse {

    /** Unique ID of the sacings account record. */
    private Long id;

    /** Account number. */
    private UUID accountNumber;

    /** Owner of the account. */
    private String customerName;

    /** Optional account nickname. */
    private String nickname;

}
