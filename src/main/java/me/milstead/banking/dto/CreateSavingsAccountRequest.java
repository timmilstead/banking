package me.milstead.banking.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating savings accounts.
 */
@Data
@NoArgsConstructor
public class CreateSavingsAccountRequest {

    @NotBlank
    private String customerName;

    @Nullable
    @Size(min = 5, max = 30)
    private String nickname;

}
