/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.tutorial.agenda.ui;

import com.demo.tutorial.agenda.MyUI;
import com.demo.tutorial.agenda.data.PersonContainer;
import com.vaadin.data.Property;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

/**
 *
 * @author Eduardo
 */
public class PersonList extends Table {

    /*public PersonList() {
     addContainerProperty("Nombre", String.class, "Adriana");
     addContainerProperty("Apellido. Paterno", String.class, "Perez");
     addItem();
     addItem();
     setSelectable(true);
     setSizeFull();
     }*/
    public PersonList(MyUI app) {

        setSizeFull();
        setContainerDataSource(app.getDataSource());
        setVisibleColumns(PersonContainer.NATURAL_COL_ORDER);
        setColumnHeaders(PersonContainer.COL_HEADERS_ENGLISH);
        setColumnCollapsingAllowed(true);
        setColumnReorderingAllowed(true);

        addGeneratedColumn("codPost", new ColumnGenerator() {

         public Object generateCell(Table source, Object itemId, Object columnId) {
                Integer codPost = (Integer) getContainerProperty(itemId, "codPost").getValue();
                return codPost;
         }
         });
        
        addGeneratedColumn("email", new ColumnGenerator() {
            public Component generateCell(Table source, Object itemId, Object columnId) {
                String email = (String) getContainerProperty(itemId, "email").getValue();
                Link l = new Link();
                l.setResource(new ExternalResource("mailto:" + email));
                l.setCaption(email);
                return l;
            }
        });

        setSelectable(true);
        setImmediate(true);
        //NO PERMITE DE-SELECCIONAR UNA FILA
        setNullSelectionAllowed(false);

        addValueChangeListener((Property.ValueChangeListener) app);
        
    }

}
