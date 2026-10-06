package me.milstead.banking.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateSavingsAccountRequest {

    @NotNull
    private String customerName;

    @Nullable
    @Min(5)
    @Max(30)
    private String nickname;

}
