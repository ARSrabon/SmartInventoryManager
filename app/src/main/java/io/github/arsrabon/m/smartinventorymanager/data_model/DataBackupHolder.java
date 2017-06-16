package io.github.arsrabon.m.smartinventorymanager.data_model;

import java.util.List;

/**
 * Created by msrabon on 6/14/17.
 */

public class DataBackupHolder {
    private List<Type> types;
    private List<Location> locations;
    private List<Product_Category> productCategories;
    private List<Product> products;
    private List<Check_In> checkIns;
    private List<Check_Out> checkOuts;
    private List<Move> moves;
    private List<Buyer> buyers;
    private List<Vendor> vendors;
    private List<Sales> sales;

    public DataBackupHolder() {

    }

    public void createBackup() {
        this.types = Type.listAll(Type.class);
        this.locations = Location.listAll(Location.class);
        this.buyers = Buyer.listAll(Buyer.class);
        this.vendors = Vendor.listAll(Vendor.class);
        this.checkIns = Check_In.listAll(Check_In.class);
        this.checkOuts = Check_Out.listAll(Check_Out.class);
        this.productCategories = Product_Category.listAll(Product_Category.class);
        this.products = Product.listAll(Product.class);
        this.moves = Move.listAll(Move.class);
        this.sales = Sales.listAll(Sales.class);
    }

    public void restoreBackup() {
        for (Type type : types){
            type.save();
        }
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Product_Category> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<Product_Category> productCategories) {
        this.productCategories = productCategories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Check_In> getCheckIns() {
        return checkIns;
    }

    public void setCheckIns(List<Check_In> checkIns) {
        this.checkIns = checkIns;
    }

    public List<Check_Out> getCheckOuts() {
        return checkOuts;
    }

    public void setCheckOuts(List<Check_Out> checkOuts) {
        this.checkOuts = checkOuts;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<Buyer> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<Buyer> buyers) {
        this.buyers = buyers;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }
}
