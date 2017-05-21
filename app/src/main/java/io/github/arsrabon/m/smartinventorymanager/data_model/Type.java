package io.github.arsrabon.m.smartinventorymanager.data_model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by msrabon on 5/21/17.
 */
@Table(name = "types")
public class Type extends SugarRecord {
    private String name;
    private int value;

    public Type() {
    }

    public Type(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
