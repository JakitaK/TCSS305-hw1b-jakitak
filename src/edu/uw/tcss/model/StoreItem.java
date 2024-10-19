package edu.uw.tcss.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * The StoreItem class represents an item that can be sold individually or in bulk.
 * Each item has a name, a price for a single unit, and optionally a bulk quantity
 * and bulk price.
 * <br />
 * If bulk pricing is available, customers can buy a specified quantity of the item
 * at a discounted bulk price. Otherwise, the item is sold individually at the set price.
 *
 * @author Jakita Kaur
 * @version 1.0
 */
public final class StoreItem implements Item {

    /**
     * A static NumberFormat instance to format prices as currency.
     * This formatter is used to display prices in US currency format.
     */
    private static final NumberFormat CURRENCY_FORMATTER =
            NumberFormat.getCurrencyInstance(Locale.US);

    /**
     * The name of the store item.
     */
    private final String myName;

    /**
     * The price of the store item.
     */
    private final BigDecimal myPrice;

    /**
     * The minimum quantity required to apply bulk pricing for the store item.
     * <br />
     * If the bulk quantity is 0, this means bulk pricing is not available for the item.
     */
    private final int myBulkQuantity;

    /**
     * The price for the store item when purchased in bulk quantities.
     * <br />
     * If the bulk price is BigDecimal.ZERO, bulk pricing is not available for the item.
     */
    private final BigDecimal myBulkPrice;


    /**
     * Constructor for a StoreItem with a name and a single item price.
     * <br />
     * This constructor is used when the item does not offer bulk pricing and is sold
     * individually at a set price.
     *
     * @param theName The name of the item (must not be null or empty).
     * @param thePrice The price of a single item (must not be null or negative).
     * @throws IllegalArgumentException if the price is negative or the name is empty.
     * @throws NullPointerException if the name or price is null.
     */
    public StoreItem(final String theName, final BigDecimal thePrice) {
        this(theName, thePrice, 0, BigDecimal.ZERO);
    }

    /**
     * Constructor for a StoreItem with a name, price, bulk quantity, and bulk price.
     * <br />
     * This constructor is used when the item offers both individual pricing and bulk pricing.
     * Customers can buy the item individually at the set price,
     * or in bulk at a discounted price.
     *
     * @param theName The name of the item,must not be null or empty.
     * @param thePrice The price of a single item, must not be null or negative.
     * @param theBulkQuantity The quantity required for bulk pricing, must not be negative.
     * @param theBulkPrice The bulk price for the specified quantity,
     * must not be null or negtive.
     * @throws IllegalArgumentException if any numeric value is negative or the name is empty.
     * @throws NullPointerException if any parameter is null.
     */
    public StoreItem(final String theName, final BigDecimal thePrice,
                     final int theBulkQuantity, final BigDecimal theBulkPrice) {
        super();
        if (theName == null) {
            throw new NullPointerException("Name cannot be null.");
        }
        if (theName.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (thePrice.compareTo(BigDecimal.ZERO) < 0
                || theBulkQuantity < 0 || theBulkPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Prices and quantities must not be negative.");
        }

        myName = theName;
        myPrice = thePrice;
        myBulkQuantity = theBulkQuantity;
        myBulkPrice = theBulkPrice;
    }

    /**
     * Returns the name of the store item.
     * <br />
     * The name serves as a unique identifier for the item and is used in display and
     * comparison operations.
     *
     * @return The name of the store item.
     */
    @Override
    public String getName() {
        return myName;
    }

    /**
     * Returns the price of a single unit of the store item.
     * <br />
     * This price is the cost of purchasing the item individually, without applying
     * any bulk pricing.
     *
     * @return The price of the store item as a BigDecimal.
     */
    @Override
    public BigDecimal getPrice() {
        return myPrice;
    }

    /**
     * Returns the minimum quantity required to apply bulk pricing for the store item.
     * <br />
     * The bulk quantity represents the number of items a customer must buy to receive
     * the discounted bulk price.
     *
     * @return The bulk quantity for the store item, or 0 if bulk pricing is not available.
     */
    @Override
    public int getBulkQuantity() {
        return myBulkQuantity;
    }

    /**
     * Returns the bulk price for the store item.
     * <br />
     * The bulk price is the total cost of purchasing the specified bulk quantity of the item.
     * If no bulk pricing is available, this method returns BigDecimal.ZERO.
     *
     * @return The bulk price for the store item,
     * or BigDecimal.ZERO if no bulk pricing is available.
     */
    @Override
    public BigDecimal getBulkPrice() {
        return myBulkPrice;
    }

    /**
     * Determines whether bulk pricing is available for the store item.
     * <br />
     * An item is considered to have bulk pricing if its bulk quantity is greater than 0
     * and its bulk price is greater than BigDecimal.ZERO.
     *
     * @return True if bulk pricing is available, false otherwise.
     */
    @Override
    public boolean isBulk() {
        return myBulkQuantity > 0;
    }

    /**
     * Returns a string representation of the store item, including its pricing information.
     * <br />
     * If bulk pricing is available, the string will include both the individual price and
     * the bulk price alongside the bulk quantity. If no bulk pricing is available, only the
     * individual price is displayed.
     *
     * @return The string representation of the store item, formatted with pricing details.
     */
    @Override
    public String toString() {
        String result = myName + ", " + CURRENCY_FORMATTER.format(myPrice);
        if (isBulk()) {
            result += " (" + myBulkQuantity + " for "
                    + CURRENCY_FORMATTER.format(myBulkPrice) + ")";
        }
        return result;
    }

    /**
     * Checks whether this StoreItem is equal to another object.
     * <br />
     * Two StoreItems are considered equal if they have the same name, price, bulk quantity,
     * and bulk price.
     *
     * @param theOther The object to compare with this StoreItem.
     * @return True if the two StoreItems are equal, false otherwise.
     */
    @Override
    public boolean equals(final Object theOther) {
        return this == theOther || theOther instanceof final StoreItem other
                && myName.equals(other.myName)
                && myPrice.equals(other.myPrice)
                && myBulkQuantity == other.myBulkQuantity
                && myBulkPrice.equals(other.myBulkPrice);
    }

    /**
     * Returns the hash code value for this StoreItem.
     * <br />
     * The hash code is generated based on the item's name,
     * price, bulk quantity, and bulk price.
     *
     * @return The hash code value for this StoreItem.
     */
    @Override
    public int hashCode() {
        return Objects.hash(myName, myPrice, myBulkQuantity, myBulkPrice);
    }
}
