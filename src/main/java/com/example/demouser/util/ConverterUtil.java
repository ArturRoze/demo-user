package com.example.demouser.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ConverterUtil {

    private ConverterUtil() {
    }

    public static <T> void convert(Consumer<T> consumer, Supplier<T> supplier) {
        if (supplier.get() != null) {
            consumer.accept(supplier.get());
        }
    }

    public static <T> void convertOrDefault(Consumer<T> consumer, Supplier<T> supplier, T defaultValue) {
        consumer.accept(supplier.get() != null ? supplier.get() : defaultValue);
    }
}
