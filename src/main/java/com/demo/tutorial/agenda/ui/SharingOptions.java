/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.tutorial.agenda.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author Eduardo
 */
public class SharingOptions extends Window {
    
    private static final String SHARING_HTML_SNNIPET = "With these setting you can modify "
            + "contact sharing options. (non-functional, example of modal dialog)";
    
    public SharingOptions() {
        setModal(true);
        setWidth("50%");
        center();
        
        setCaption("Sharing Options");
        
        VerticalLayout subContent = new VerticalLayout();
        subContent.addComponent(new Label(SHARING_HTML_SNNIPET));
        subContent.addComponent(new CheckBox("Gmail"));
        subContent.addComponent(new CheckBox(".Mac"));
        
        Button close = new Button("Ok");
        close.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                close();
            }
        });
        subContent.addComponent(close);
        setContent(subContent);
    }
    
}
