/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.tutorial.agenda.ui;

import com.demo.tutorial.agenda.MyUI;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Tree;

/**
 *
 * @author Eduardo
 */
public class NavigationTree extends Tree {
    
    public static final  Object MOSTRAR_TODO = "Mostrar Todo";
    public static final  Object BUSCAR = "Buscar";
    
    public NavigationTree(MyUI app) {
        addItem(MOSTRAR_TODO);
        addItem(BUSCAR);
        
        setChildrenAllowed(MOSTRAR_TODO, false);
        
        setSelectable(true);
        setNullSelectionAllowed(false);
        setImmediate(true);
        
        addItemClickListener((ItemClickListener)app);
        
    }
    
    
}
