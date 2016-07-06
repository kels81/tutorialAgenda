/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.tutorial.agenda.ui;

import java.io.Serializable;

/**
 *
 * @author Eduardo
 */
public class SearchFilter implements Serializable {

    private String term;
    private Object propertyId;
    private String searchName;
    private Object itemCaption;

    public SearchFilter(Object propertyId, String searchTerm, String name, Object itemCaption) {
        this.propertyId = propertyId;
        this.term = searchTerm;
        this.searchName = name;
        this.itemCaption = itemCaption;
    }

    public String getTerm() {
        return term;
    }

    public Object getPropertyId() {
        return propertyId;
    }

    public String getSearchName() {
        return searchName;
    }
    
    @Override
    public String toString() {
        return getSearchName();
    }

    public Object getItemCaption() {
        return itemCaption;
    }


}
