package com.example.weather;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.StringJoiner;

class Temperature {

    private final BigDecimal value;

    private Temperature(BigDecimal value, int scale) {
        this.value = value.setScale(scale, RoundingMode.HALF_UP);
    }

    static Temperature ofCelsius(String value, int scale) {
        return new Temperature(new BigDecimal(value), scale);
    }

    public BigDecimal celsius() {
        return value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Temperature.class.getSimpleName() + "[", "]")
                .add("value=" + value)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temperature that = (Temperature) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
