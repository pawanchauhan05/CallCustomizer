package com.pawansinghchouhan05.callcustomizer.home.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fitterfox-Pawan on 6/28/2016.
 */
public class CustomNumberList {
    private List<CustomNumber> customNumberList = new ArrayList<>();

    public CustomNumberList(List<CustomNumber> customNumberList) {
        this.customNumberList = customNumberList;
    }

    public CustomNumberList() {
        this.customNumberList = new ArrayList<>();
    }

    public List<CustomNumber> getCustomNumberList() {
        return customNumberList;
    }

    public void setCustomNumberList(List<CustomNumber> customNumberList) {
        this.customNumberList = customNumberList;
    }

    @Override
    public String toString() {
        return "CustomNumberList{" +
                "customNumberList=" + customNumberList +
                '}';
    }
}
