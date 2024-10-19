package edu.uw.tcss.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The StoreCart class represents a shopping cart that holds item orders.
 * It manages individual orders, applies membership discounts (if applicable),
 * and calculates the total cost of the cart, including handling bulk pricing.
 * <br />
 * Customers with membership can benefit from bulk pricing when they purchase
 * sufficient quantities of items that have bulk pricing available.
 * <br />
 * The cart can also be cleared, and its size in terms of both the number of
 * distinct item orders and the total number of items can be retrieved.
 *
 * @author Jakita Kaur
 * @version 1.0
 */
public class StoreCart implements Cart {

    /**
     * A list of item orders in the cart.
     */
    private final List<ItemOrder> myOrders;

    /**
     * Indicates whether the customer has a membership.
     * Membership status can affect the pricing of bulk items.
     */
    private boolean myMembership;

    /**
     * Constructor that creates an empty shopping cart.
     * <br />
     * Initializes the order list and sets the membership to false by default.
     */
    public StoreCart() {
        super();
        myOrders = new ArrayList<>();
        myMembership = false;
    }

    /**
     * Adds a new item order to the cart, replacing any previous order for the same item.
     * <br />
     * If an order for the same item already exists in the cart, it will be removed
     * and replaced with the new order. Orders with a quantity of zero or less are not added.
     *
     * @param theOrder The ItemOrder to add to the cart.
     * @throws NullPointerException if theOrder is null.
     */
    @Override
    public void add(final ItemOrder theOrder) {
        Objects.requireNonNull(theOrder, "ItemOrder cannot be null");
        myOrders.removeIf(order -> order.getItem().equals(theOrder.getItem()));
        Optional.of(theOrder).filter(order -> order.getQuantity() > 0).
                ifPresent(myOrders::add);
    }

    /**
     * Sets whether the customer has a membership.
     * <br />
     * Membership can affect the bulk pricing of items. When membership is active, bulk
     * pricing is applied to eligible items if the customer purchases enough of the item.
     *
     * @param theMembership True if the customer has a membership, false otherwise.
     */
    @Override
    public void setMembership(final boolean theMembership) {
        myMembership = theMembership;
    }

    /**
     * Calculates the total cost of the cart, applying bulk pricing if applicable.
     * <br />
     * If a customer has a membership and purchases items that offer bulk pricing in sufficient
     * quantities, the bulk price will be applied. Otherwise, the regular item price is used.
     * The total cost is rounded to two decimal places using rounding mode HALF_EVEN.
     *
     * @return The total cost of the cart as a BigDecimal.
     */
    @Override
    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (final ItemOrder order : myOrders) {
            final Item item = order.getItem();
            final int quantity = order.getQuantity();

            if (myMembership && item.isBulk() && quantity >= item.getBulkQuantity()) {
                final int bulkSets = quantity / item.getBulkQuantity();
                final int remainder = quantity % item.getBulkQuantity();

                total = total.add(item.getBulkPrice().multiply(BigDecimal.valueOf(bulkSets)));
                total = total.add(item.getPrice().multiply(BigDecimal.valueOf(remainder)));
            } else {
                total = total.add(item.getPrice().multiply(BigDecimal.valueOf(quantity)));
            }
        }
        return total.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Clears all orders from the cart.
     * <br />
     * This method removes all item orders from the cart, leaving it empty.
     */
    @Override
    public void clear() {
        myOrders.clear();
    }

    /**
     * Retrieves the current size of the cart.
     * <br />
     * The size is represented by a CartSize object, which includes the number of distinct
     * item orders and the total number of items in the cart.
     *
     * @return A CartSize record containing the order count and total item count.
     */
    @Override
    public CartSize getCartSize() {
        final int itemCount = myOrders.size();
        int totalItems = 0;

        for (final ItemOrder order : myOrders) {
            totalItems += order.getQuantity();
        }

        return new CartSize(itemCount, totalItems);
    }

    /**
     * Returns a string representation of the shopping cart.
     * <br />
     * The string representation includes the list of item orders and whether or not
     * the customer has a membership.
     *
     * @return The string representation of the cart.
     */
    @Override
    public String toString() {
        return "StoreCart{" + "orders=" + myOrders + ", membership=" + myMembership + '}';
    }

}
