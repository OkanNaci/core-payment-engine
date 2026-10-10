package com.deneme.payment.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProductWithValidNameAndPrice() {
        Product product = new Product(
                "Laptop",
                new BigDecimal("1500.00")
        );

        assertEquals("Laptop", product.getName());
        assertEquals(
                0,
                new BigDecimal("1500.00").compareTo(product.getPrice())
        );
    }

    @Test
    void shouldRejectNullName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(null, new BigDecimal("100.00"))
        );
    }

    @Test
    void shouldRejectBlankName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("   ", new BigDecimal("100.00"))
        );
    }

    @Test
    void shouldRejectNullPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("Laptop", null)
        );
    }

    @Test
    void shouldRejectZeroPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("Laptop", BigDecimal.ZERO)
        );
    }

    @Test
    void shouldRejectNegativePrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(
                        "Laptop",
                        new BigDecimal("-100.00")
                )
        );
    }
    @Test
    void shouldNotConsiderDifferentTransientProductsEqual() {
        Product product1 = new Product(
                "Laptop",
                new BigDecimal("1000.00")
        );

        Product product2 = new Product(
                "Mouse",
                new BigDecimal("500.00")
        );

        assertNotEquals(product1, product2);
    }

    @Test
    void shouldBeEqualToItself() {
        Product product = new Product(
                "Laptop",
                new BigDecimal("1000.00")
        );

        assertEquals(product, product);
    }

    @Test
    void shouldNotBeEqualToNull() {
        Product product = new Product(
                "Laptop",
                new BigDecimal("1000.00")
        );

        assertNotEquals(null, product);
    }
}