package io.github.arsrabon.m.smartinventorymanager.data_model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by msrabon on 5/21/17.
 */
@Table(name = "vendors")
public class Vendor extends SugarRecord {
    private String name;
    private String companyName;
    private String email;
    private String contact_Personal;
    private String contact_Office;
    private String address;

    public Vendor(String name, String companyName, String email, String contact_Personal, String contact_Office, String address) {
        this.name = name;
        this.companyName = companyName;
        this.email = email;
        this.contact_Personal = contact_Personal;
        this.contact_Office = contact_Office;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_Personal() {
        return contact_Personal;
    }

    public void setContact_Personal(String contact_Personal) {
        this.contact_Personal = contact_Personal;
    }

    public String getContact_Office() {
        return contact_Office;
    }

    public void setContact_Office(String contact_Office) {
        this.contact_Office = contact_Office;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
