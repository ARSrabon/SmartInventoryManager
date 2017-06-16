package io.github.arsrabon.m.smartinventorymanager.data_model;

import com.orm.SugarRecord;

/**
 * Created by msrabon on 6/14/17.
 */

public class Sales extends SugarRecord {
    private Location shopLocation;
    private Product product;

    public Sales() {
    }

}
