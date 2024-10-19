package edu.uw.tcss.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class StoreCartTest {
    /** The name of the laptop item used in tests. */
    private static final String LAPTOP_NAME = "Laptop";

    /** The name of the mouse item used in tests. */
    private static final String MOUSE_NAME = "Mouse";

    /** The price of the laptop item used in tests. */
    private static final String LAPTOP_PRICE = "999.99";

    /** The regular price of the mouse item used in tests. */
    private static final String MOUSE_PRICE = "25.00";

    /** The bulk price of the mouse item used in tests. */
    private static final String MOUSE_BULK_PRICE = "200.00";

    /** The quantity threshold for bulk pricing for the mouse. */
    private static final int MOUSE_BULK_QUANTITY = 12;

    /** The StoreCart instance used for testing. */
    private static final StoreCart TEST_CART = new StoreCart();

    /** A non-bulk StoreItem instance representing a laptop used in tests. */
    private static final StoreItem TEST_LAPTOP =
            new StoreItem(LAPTOP_NAME, new BigDecimal(LAPTOP_PRICE));

    /** A bulk StoreItem instance representing a mouse used in tests. */
    private static final StoreItem TEST_MOUSE =
            new StoreItem(MOUSE_NAME, new BigDecimal(MOUSE_PRICE),
                    MOUSE_BULK_QUANTITY, new BigDecimal(MOUSE_BULK_PRICE));

    /** An ItemOrder instance representing an order of 1 laptop used in tests. */
    private static final ItemOrder ORDER_LAPTOP = new StoreItemOrder(TEST_LAPTOP, 1);

    /** An ItemOrder instance representing an order of 12 mice used in tests. */
    private static final ItemOrder ORDER_MOUSE = new StoreItemOrder(TEST_MOUSE, 12);

    /** Integer test value for testing different quantities in test cases, int is 3 to test.*/
    private static final int TEST_VALUE_3 = 3;

    /** Integer test value for testing different quantities in test cases, int is 4 to test.*/
    private static final int TEST_VALUE_4 = 4;

    /** Integer test value for testing different quantities in test cases, int is 5 to test.*/
    private static final int TEST_VALUE_5 = 5;

    /** Integer test value for testing different quantities in test cases, int is 8 to test.*/
    private static final int TEST_VALUE_8 = 8;

    /** Integer test value for testing different quantities in test cases, int is 12 to test.*/
    private static final int TEST_VALUE_12 = 12;

    /** Integer test value for testing different quantities in test cases, int is 13 to test.*/
    private static final int TEST_VALUE_13 = 13;

    /**
     * Tests adding valid orders to the cart.
     */
    @Test
    void testAddValidOrders() {
        TEST_CART.clear();
        TEST_CART.add(ORDER_LAPTOP);
        TEST_CART.add(ORDER_MOUSE);
        assertEquals(2, TEST_CART.getCartSize().itemOrderCount(),
                "Cart should contain 2 orders.");
    }

    /**
     * Tests adding a null order to the cart.
     */
    @Test
    void testAddNullOrder() {
        TEST_CART.clear();
        assertThrows(NullPointerException.class, () -> TEST_CART.add(null),
                "Adding null should throw NullPointerException.");
    }

    /**
     * Tests replacing an order in the cart.
     */
    @Test
    void testReplaceOrder() {
        TEST_CART.clear();
        TEST_CART.add(ORDER_LAPTOP);
        TEST_CART.add(new StoreItemOrder(TEST_LAPTOP, TEST_VALUE_3));
        assertEquals(TEST_VALUE_3, TEST_CART.getCartSize().itemCount(),
                "Cart should contain 3 items after replacing the order.");
    }

    /**
     * Tests adding a zero-quantity order to the cart.
     */
    @Test
    void testAddZeroQuantityOrder() {
        TEST_CART.clear();
        TEST_CART.add(new StoreItemOrder(TEST_LAPTOP, 0));
        assertEquals(0, TEST_CART.getCartSize().itemOrderCount(),
                "Cart should not count zero-quantity orders.");
    }

    /**
     * Tests adding an order with quantity less than bulk threshold with membership.
     */
    @Test
    void testAddNonBulkWithMembership() {
        TEST_CART.clear();
        TEST_CART.setMembership(true);
        TEST_CART.add(new StoreItemOrder(TEST_MOUSE, TEST_VALUE_5));
        assertEquals(new BigDecimal("125.00"), TEST_CART.calculateTotal(),
                "Total should be $125.00 for 5 mice "
                        + "with membership but without bulk pricing.");
    }

    /**
     * Tests calculateTotal() for a non-bulk item with membership.
     */
    @Test
    void testCalculateTotalWithNonBulkItemWithMembership() {
        TEST_CART.clear();
        TEST_CART.setMembership(true);

        final StoreItem nonBulkMouse =
                new StoreItem(MOUSE_NAME, new BigDecimal(MOUSE_PRICE));

        TEST_CART.add(new StoreItemOrder(nonBulkMouse, TEST_VALUE_3));
        assertEquals(new BigDecimal("75.00"), TEST_CART.calculateTotal(),
                "Total should be $75.00 for 3 non-bulk mice with membership.");
    }

    /**
     * Tests setting membership status.
     */
    @Test
    void testSetMembership() {
        TEST_CART.clear();
        TEST_CART.setMembership(true);
        assertTrue(TEST_CART.calculateTotal().compareTo(BigDecimal.ZERO) >= 0,
                "Membership should be set correctly.");
    }

    /**
     * Tests calculateTotal() for multiple non-bulk items to increase false
     * hits for item.isBulk().
     */
    @Test
    void testCalculateTotalWithMultipleNonBulkItems() {
        TEST_CART.clear();
        TEST_CART.setMembership(false);
        final StoreItem nonBulkMouse =
                new StoreItem(MOUSE_NAME, new BigDecimal(MOUSE_PRICE));
        TEST_CART.add(new StoreItemOrder(nonBulkMouse, TEST_VALUE_4));
        TEST_CART.add(ORDER_LAPTOP);
        assertEquals(new BigDecimal("1099.99"), TEST_CART.calculateTotal(),
                "Total should be $1099.99 for 4 non-bulk mice and 1 laptop.");
    }

    /**
     * Tests calculateTotal() for a bulk item with quantity less than the bulk threshold.
     */
    @Test
    void testCalculateTotalBulkItemWithQuantityLessThanThreshold() {
        TEST_CART.clear();
        TEST_CART.setMembership(true);
        TEST_CART.add(new StoreItemOrder(TEST_MOUSE, TEST_VALUE_8));
        assertEquals(new BigDecimal("200.00"), TEST_CART.calculateTotal(),
                "Total should be $200.00 for 8 mice (bulk pricing not applied).");
    }

    /**
     * Tests calculateTotal() for a bulk item without membership.
     */
    @Test
    void testCalculateTotalWithBulkItemWithoutMembership() {
        TEST_CART.clear();
        TEST_CART.setMembership(false);
        TEST_CART.add(new StoreItemOrder(TEST_MOUSE, TEST_VALUE_12));
        assertEquals(new BigDecimal("300.00"), TEST_CART.calculateTotal(),
                "Total should be $300.00 for a bulk item without membership.");
    }

    /**
     * Tests calculateTotal() with membership and bulk pricing.
     */
    @Test
    void testCalculateTotalWithMembershipBulk() {
        TEST_CART.clear();
        TEST_CART.setMembership(true);
        TEST_CART.add(new StoreItemOrder(TEST_MOUSE, TEST_VALUE_12));
        assertEquals(new BigDecimal("200.00"), TEST_CART.calculateTotal(),
                "Total should be $200.00 with bulk pricing and membership.");
    }

    /**
     * Tests calculateTotal() with bulk pricing and remainder.
     */
    @Test
    void testCalculateTotalBulkWithRemainder() {
        TEST_CART.clear();
        TEST_CART.setMembership(true);
        TEST_CART.add(new StoreItemOrder(TEST_MOUSE, TEST_VALUE_13));
        assertEquals(new BigDecimal("225.00"), TEST_CART.calculateTotal(),
                "Total should be $225.00 for 13 mice with bulk pricing and remainder.");
    }

    /**
     * Tests calculateTotal() for a non-bulk item.
     */
    @Test
    void testCalculateTotalWithNonBulkItem() {
        TEST_CART.clear();
        TEST_CART.setMembership(false);

        final StoreItem nonBulkMouse =
                new StoreItem(MOUSE_NAME, new BigDecimal(MOUSE_PRICE));

        TEST_CART.add(new StoreItemOrder(nonBulkMouse, 2));
        assertEquals(new BigDecimal("50.00"), TEST_CART.calculateTotal(),
                "Total should be $50.00 for 2 non-bulk mice.");
    }

    /**
     * Tests calculateTotal() with an empty cart.
     */
    @Test
    void testCalculateTotalWithEmptyCart() {
        TEST_CART.clear();
        assertEquals(new BigDecimal("0.00"), TEST_CART.calculateTotal(),
                "Total should be $0.00 for an empty cart.");
    }

    /**
     * Tests getCartSize() for a cart with non-zero quantities.
     */
    @Test
    void testGetCartSizeNonZeroQuantities() {
        TEST_CART.clear();
        TEST_CART.add(ORDER_LAPTOP);
        TEST_CART.add(ORDER_MOUSE);
        assertEquals(2, TEST_CART.getCartSize().itemOrderCount(),
                "Cart should contain 2 orders.");
        assertEquals(TEST_VALUE_13, TEST_CART.getCartSize().itemCount(),
                "Cart should contain 13 items.");
    }

    /**
     * Tests getCartSize() for a cart with both zero-quantity and non-zero-quantity orders.
     */
    @Test
    void testGetCartSizeMixedQuantities() {
        TEST_CART.clear();
        TEST_CART.add(new StoreItemOrder(TEST_LAPTOP, 0));
        TEST_CART.add(ORDER_MOUSE);
        assertEquals(1, TEST_CART.getCartSize().itemOrderCount(),
                "Cart should contain 1 valid order (non-zero quantity).");
        assertEquals(TEST_VALUE_12, TEST_CART.getCartSize().itemCount(),
                "Cart should contain 12 items, ignoring zero-quantity orders.");
    }

    /**
     * Tests getCartSize() with an empty cart.
     */
    @Test
    void testGetCartSizeWithEmptyCart() {
        TEST_CART.clear();
        assertEquals(0, TEST_CART.getCartSize().itemOrderCount(),
                "Cart should contain 0 orders when empty.");
        assertEquals(0, TEST_CART.getCartSize().itemCount(),
                "Cart should contain 0 items when empty.");
    }

    /**
     * Tests the clear() method.
     */
    @Test
    void testClear() {
        TEST_CART.clear();
        TEST_CART.add(ORDER_LAPTOP);
        TEST_CART.add(ORDER_MOUSE);
        TEST_CART.clear();
        assertEquals(0, TEST_CART.getCartSize().itemOrderCount(),
                "Cart should be empty after clearing.");
    }

    /**
     * Tests the toString() method.
     */
    @Test
    void testToString() {
        TEST_CART.clear();
        TEST_CART.add(ORDER_LAPTOP);
        final String cartString = TEST_CART.toString();
        assertTrue(cartString.contains(LAPTOP_NAME), "toString should mention 'Laptop'.");
    }
}
