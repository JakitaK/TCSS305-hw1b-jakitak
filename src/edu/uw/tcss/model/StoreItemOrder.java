package edu.uw.tcss.model;

import java.util.Objects;

/**
 * The StoreItemOrder class represents an order for a specific item with a specified quantity.
 * <br />
 * Each order includes an item (represented by the {@link Item} interface) and the number of
 * units of that item that have been ordered. The class supports comparison of orders
 * for equality and generates a hash code for efficient storage in collections.
 *
 * @author Jakita Kaur
 * @version 1.0
 */
public class StoreItemOrder implements ItemOrder {

    /**
     * The item being ordered.
     * <br />
     * This field holds the specific item for which the order has been placed.
     * The item is represented as an object that implements the {@link Item} interface.
     */
    private final Item myItem;

    /**
     * The quantity of the item ordered.
     * <br />
     * This field holds the number of units of the item that have been ordered.
     * The quantity must be non-negative.
     */
    private final int myQuantity;

    /**
     * Constructs a StoreItemOrder with the specified item and quantity.
     * <br />
     * This constructor initializes the order with the item to be ordered and the number
     * of units of that item. The item must not be null, and the quantity must not be negative.
     *
     * @param theItem     The item being ordered (must not be null).
     * @param theQuantity The quantity of the item ordered (must be non-negative).
     * @throws NullPointerException     If theItem is null.
     * @throws IllegalArgumentException If theQuantity is negative.
     */
    public StoreItemOrder(final Item theItem, final int theQuantity) {
        super();
        Objects.requireNonNull(theItem, "Item cannot be null.");
        if (theQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        myItem = theItem;
        myQuantity = theQuantity;
    }

    /**
     * Returns the item associated with this order.
     * <br />
     * This method retrieves the item for which this order was placed.
     * The item is represented by an object that implements the {@link Item} interface.
     *
     * @return The item being ordered.
     */
    @Override
    public Item getItem() {
        return myItem;
    }

    /**
     * Returns the quantity of the item ordered.
     * <br />
     * This method retrieves the number of units of the item that have been ordered.
     *
     * @return The quantity of the item ordered.
     */
    @Override
    public int getQuantity() {
        return myQuantity;
    }

    /**
     * Returns a string representation of this item order.
     * <br />
     * The string representation includes the name of the item and the quantity ordered,
     * formatted as: "Item: [Item Name], Quantity: [Quantity]".
     *
     * @return A string containing the item's name and quantity ordered.
     */
    @Override
    public String toString() {
        return String.format("Item: %s, Quantity: %d", myItem.getName(), myQuantity);
    }
}
