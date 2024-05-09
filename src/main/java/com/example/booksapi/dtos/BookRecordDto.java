package com.example.booksapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BookRecordDto(@NotBlank String name, @NotNull BigDecimal value) {
}
