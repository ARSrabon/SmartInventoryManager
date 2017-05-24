package io.github.arsrabon.m.smartinventorymanager.data_model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.List;

/**
 * Created by msrabon on 5/21/17.
 */
@Table(name = "locations")
public class Location extends SugarRecord {
    private String name;
    private Type type;
    private String address;

    public Location() {
    }

    public Location(String name, Type type, String address) {
        this.name = name;
        this.type = type;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Location> getLocationList(){
        return Location.listAll(Location.class);
    }
}
