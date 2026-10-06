package me.milstead.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetSavingsAccountResponse {

    private Long id;

    private UUID accountNumber;

    private String customerName;

    private String nickname;

}
