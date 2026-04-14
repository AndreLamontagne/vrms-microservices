package com.champsoft.vrms.registration.domain.model;

import com.champsoft.vrms.registration.domain.exception.ExpiryDateMustBeFutureException;

import java.time.LocalDate;

public record ExpiryDate(LocalDate value) {
    public ExpiryDate {
        if (value == null) {
            throw new IllegalArgumentException("expiry is required");
        }
    }

    public static ExpiryDate forNewRegistration(LocalDate value) {
        if (!value.isAfter(LocalDate.now())) {
            throw new ExpiryDateMustBeFutureException("Expiry must be in the future");
        }
        return new ExpiryDate(value);
    }

    public static ExpiryDate reconstruct(LocalDate value) {
        return new ExpiryDate(value);
    }
}
