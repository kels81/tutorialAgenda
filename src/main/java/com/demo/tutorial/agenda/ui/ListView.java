/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.tutorial.agenda.ui;

import com.vaadin.ui.VerticalSplitPanel;

/**
 *
 * @author Eduardo
 */
public class ListView extends VerticalSplitPanel {
    
    public ListView(PersonList personList, PersonForm personForm) {
        setFirstComponent(personList);
        setSecondComponent(personForm);
        setSplitPosition(40);
        
    }

    
}
