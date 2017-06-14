package io.github.arsrabon.m.smartinventorymanager.data_model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by msrabon on 5/21/17.
 */

@Table(name = "products")
public class Product extends SugarRecord {
    private String name;
    private String details;
    private String productCode;
    private int quantity;
    private double currentPrice;
    private Product_Category product_category;

    public Product() {
    }

    public Product(String name, String details, String productCode, int quantity, double currentPrice, Product_Category product_category) {
        this.name = name;
        this.details = details;
        this.productCode = productCode;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.product_category = product_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Product_Category getProduct_category() {
        return product_category;
    }

    public void setProduct_category(Product_Category product_category) {
        this.product_category = product_category;
    }

    @Override
    public String toString() {
        return productCode;
    }
}
