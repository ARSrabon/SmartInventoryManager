package io.github.arsrabon.m.smartinventorymanager.data_model;

import com.orm.SugarRecord;

/**
 * Created by msrabon on 5/25/17.
 */

public class Move extends SugarRecord {
    private Product product;
    private int count;
    private Location source;
    private Location destination;

    public Move() {
    }

    public Move(Product product, int count, Location source, Location destination) {
        this.product = product;
        this.count = count;
        this.source = source;
        this.destination = destination;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Location getSource() {
        return source;
    }

    public void setSource(Location source) {
        this.source = source;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }
}
