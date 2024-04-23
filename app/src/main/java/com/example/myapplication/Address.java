package com.example.myapplication;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
@Table(name = "Address")
public class Address implements Serializable {
    @Column(name = "id" ,autoGen = true,isId = true)
    String id;
    @Column(name = "address" )
    String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
