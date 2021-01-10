package com.ecommerce.eshop.utils.ControllersUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class DateRange {
    @NotNull
    LocalDate from;
    LocalDate to;


    public DateRange(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = Objects.requireNonNullElseGet(to, LocalDate::now);
    }

    public LocalDateTime getDateTimeFrom() {
        return LocalDateTime.of(from, LocalTime.MIDNIGHT);
    }

    public LocalDateTime getDateTimeTo() {
        return LocalDateTime.of(to, LocalTime.MIDNIGHT);
    }

}
