package io.github.arsrabon.m.smartinventorymanager.data_model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by msrabon on 5/21/17.
 */
@Table(name = "checkins")
public class Check_In extends SugarRecord {
    private String checkin_ID;
    private Product product;
    private String details;
    private String barcode;
    private double unit_price;
    private int quantity;
    private Vendor vendor;
    private String timeStamp;

    public Check_In() {
    }

    public Check_In(String checkin_ID, Product product, String details, String barcode,
                    double unit_price, int quantity, Vendor vendor, String timeStamp) {
        this.checkin_ID = checkin_ID;
        this.product = product;
        this.details = details;
        this.barcode = barcode;
        this.unit_price = unit_price;
        this.quantity = quantity;
        this.vendor = vendor;
        this.timeStamp = timeStamp;
    }

    public String getCheckin_ID() {
        return checkin_ID;
    }

    public void setCheckin_ID(String checkin_ID) {
        this.checkin_ID = checkin_ID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
