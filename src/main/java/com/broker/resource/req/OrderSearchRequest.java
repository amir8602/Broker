package com.broker.resource.req;

import java.util.List;


public class OrderSearchRequest {

    List<Integer> searchOption;

    public List<Integer> getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(List<Integer> searchOption) {
        this.searchOption = searchOption;
    }

    public OrderSearchRequest(List<Integer> searchOption) {
        this.searchOption = searchOption;
    }


}
