package edu.uw.tcss.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class StoreItemOrderTest {

    /**
     * The name of the product used in testing.
     */
    private static final String PRODUCT_NAME = "Sample Product";

    /**
     * The price of the product used in testing.
     */
    private static final String PRODUCT_COST = "6.50";

    /**
     * The quantity of the product used in testing.
     */
    private static final int PRODUCT_AMOUNT = 15;

    /**
     * The name of a different product for equality tests.
     */
    private static final String OTHER_PRODUCT_NAME = "Other Product";

    /**
     * The price of a different prodict for equality tests.
     */
    private static final BigDecimal OTHER_PRODUCT_COST = new BigDecimal("10.00");

    /**
     * Constant test value positive
     */
    private static final int TEST_VALUE_NEG = -5;

    /**
     * Constant test value positive
     */
    private static final int TEST_VALUE_POS = 5;

    /**
     * A test Product object.
     */
    private Item mySampleProduct;

    /**
     * A test StoreItemOrder object for testing.
     */
    private StoreItemOrder mySampleOrder;

    /**
     * Another StoreItemOrder for testing equality.
     */
    private StoreItemOrder myOtherOrder;

    @BeforeEach
    void init() {
        mySampleProduct = new StoreItem(PRODUCT_NAME, new BigDecimal(PRODUCT_COST));

        final Item otherProduct = new StoreItem(OTHER_PRODUCT_NAME, OTHER_PRODUCT_COST);
        mySampleOrder = new StoreItemOrder(mySampleProduct, PRODUCT_AMOUNT);
        myOtherOrder = new StoreItemOrder(otherProduct, PRODUCT_AMOUNT);
    }

    @Test
    public final void testConstructorWithNullProduct() {
        assertThrows(NullPointerException.class,
                () -> new StoreItemOrder(null, PRODUCT_AMOUNT),
                "Constructor should throw NullPointerException for a null product.");
    }

    @Test
    public final void testConstructorWithNegativeQuantity() {
        assertThrows(IllegalArgumentException.class,
                () -> new StoreItemOrder(mySampleProduct, TEST_VALUE_NEG),
                "Constructor should throw IllegalArgumentException for a negative quantity.");
    }

    @Test
    public final void testGetProduct() {
        assertEquals(mySampleProduct, mySampleOrder.getItem(),
                "getItem() should return the correct Product object.");
    }

    @Test
    public final void testGetProductAmount() {
        assertEquals(PRODUCT_AMOUNT, mySampleOrder.getQuantity(),
                "getQuantity() should return the correct product amount.");
    }

    @Test
    public final void testOrderToString() {
        assertEquals("Item: " + PRODUCT_NAME + ", Quantity: " + PRODUCT_AMOUNT,
                mySampleOrder.toString(),
                "toString() should return the correct string representation of the order.");
    }

    @Test
    public void testEqualsReflexive() {
        assertEquals(mySampleOrder, mySampleOrder,
                "equals() should be reflexive: an object must equal itself.");
    }

    @Test
    public void testEqualsDifferentQuantity() {
        final StoreItemOrder differentQuantityOrder = new StoreItemOrder(mySampleProduct,
                PRODUCT_AMOUNT + TEST_VALUE_POS);
        assertNotEquals(mySampleOrder, differentQuantityOrder,
                "equals() should return false for orders with different quantities.");
    }

    @Test
    public void testEqualsDifferentProduct() {
        assertNotEquals(mySampleOrder, myOtherOrder,
                "equals() should return false for orders with different products.");
    }
}