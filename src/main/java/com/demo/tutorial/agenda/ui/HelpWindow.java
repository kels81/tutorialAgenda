/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.tutorial.agenda.ui;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author Eduardo
 */
public class HelpWindow extends Window {

    private static final String HELP_HTML_SNIPPET = "This is "
            + "an application built during <strong><a href=\""
            + "https://vaadin.com/tutorial/\">Vaadin</a></strong> "
            + "tutorial. Hopefully it doesn't need any real help.";
    
    public  HelpWindow() {
        setCaption("Address Book Help");
        
        VerticalLayout subContent = new VerticalLayout();
        subContent.addComponent(new Label(HELP_HTML_SNIPPET));
        subContent.setMargin(true);
        
        setContent(subContent);
        
        //DESHABILITAR LA X CERRAR
        setClosable(true);  //false
        
        //DESHABILITAR EL AGRANDAR VENTANA
        setResizable(false);
    }
}
