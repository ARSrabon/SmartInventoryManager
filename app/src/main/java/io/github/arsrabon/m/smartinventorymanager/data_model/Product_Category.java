package io.github.arsrabon.m.smartinventorymanager.data_model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.List;

/**
 * Created by msrabon on 5/20/17.
 */

@Table(name = "categories")
public class Product_Category extends SugarRecord {
    private String categoryName;
    private String description;
    private String shortCode;

    public Product_Category() {
    }

    public Product_Category(String categoryName, String description, String shortCode) {
        this.categoryName = categoryName;
        this.description = description;
        this.shortCode = shortCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}
