package cn.com.zhihetech.online.commons;

import java.io.Serializable;

/**
 * Created by ShenYunjie on 2015/11/17.
 */
public class RequestParam implements Serializable,Cloneable {
    private String searchName;
    private String searchValue;
    private String sort;
    private String order;

    public RequestParam() {
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
