package com.enterprise.ij.nearish.Models;

/**
 * Created by RIKI on 01/07/2017.
 */

public class UserCategories {
    private String[] types;

    public UserCategories(String[] types) {
        this.types = types;
    }

    public String[] getTypes(){
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String getType(int pos) {
        return types[pos];
    }
}
