package com.pragma.fc.food_curt.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRestaurantRequestDto {
    @PositiveOrZero
    @NotNull
    @NotNull
    private Long nit;

    @NotNull
    @PositiveOrZero
    private Long ownerDocumentNumber;

    @NotNull
    @Size(max = 100)
    @Pattern(
            regexp = "^(?!\\d+$).+",
            message = "The field cannot contain only numbers"
    )
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 250)
    private String address;

    @NotNull
    @NotBlank
    @Size(max = 20)
    @Pattern(
            regexp = "^\\+?\\d{1,13}$",
            message = "Phone number must be up to 13 digits and can optionally start with a '+'"
    )
    private String phone;

    @NotNull
    @NotBlank
    @Size(max = 400)
    private String logoUrl;
}
