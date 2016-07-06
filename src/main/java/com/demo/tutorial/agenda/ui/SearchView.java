/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.tutorial.agenda.ui;

import com.demo.tutorial.agenda.MyUI;
import com.demo.tutorial.agenda.data.PersonContainer;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;

/**
 *
 * @author Eduardo
 */
public class SearchView extends Panel {

    private TextField txtTextField;
    private NativeSelect cmbFieldtoSearch;
    private CheckBox chkSaveSearch;
    private TextField txtSearchName;
    private MyUI app;

    public SearchView(final MyUI app) {
        this.app = app;

        setCaption("Buscar Contactos");
        setSizeFull();
        addStyleName("view");

        /* Usar un FormLayout como layout principal para este Panel*/
        FormLayout formLayout = new FormLayout();

        /* Creando componente UI */
        txtTextField = new TextField("Buscar");
        cmbFieldtoSearch = new NativeSelect("Campo por buscar");
        chkSaveSearch = new CheckBox("Guardar Búsqueda");
        txtSearchName = new TextField("Buscar nombre");
        Button btnBuscar = new Button("Buscar");

        btnBuscar.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                performSearch();
            }
        });

        /* Inicializar combo */
        for (int i = 0; i < PersonContainer.NATURAL_COL_ORDER.length; i++) {

            cmbFieldtoSearch.addItem(PersonContainer.NATURAL_COL_ORDER[i]);
            cmbFieldtoSearch.setItemCaption(PersonContainer.NATURAL_COL_ORDER[i], PersonContainer.COL_HEADERS_ENGLISH[i]);
        }

        cmbFieldtoSearch.setValue("apPaterno");
        cmbFieldtoSearch.setNullSelectionAllowed(false);

        /* Inicializando save checkbox */
        chkSaveSearch.setValue(true);
        chkSaveSearch.setImmediate(true);
        chkSaveSearch.addListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                boolean value = (Boolean) event.getProperty().getValue();
                txtSearchName.setVisible(value);
            }
        });

        /*
         chkSaveSearch.addValueChangeListener(new Property.ValueChangeListener() {
         @Override
         public void valueChange(ValueChangeEvent event) {
         boolean value = (Boolean) event.getProperty().getValue();
         txtSearchName.setVisible(value);
         }
         });*/

        /* Añadiendo los componentes creados al formulario */
        formLayout.addComponent(txtTextField);
        formLayout.addComponent(cmbFieldtoSearch);
        formLayout.addComponent(chkSaveSearch);
        formLayout.addComponent(txtSearchName);
        formLayout.addComponent(btnBuscar);

        setContent(formLayout);
    }
    
    private void performSearch(){
        String searchTerm = (String) txtTextField.getValue();
        
        SearchFilter searchFilter = new SearchFilter(cmbFieldtoSearch.getValue(), searchTerm, txtSearchName.getValue(), cmbFieldtoSearch.getItemCaption(cmbFieldtoSearch.getValue()));  //LINEA PARA MOSTRAR EL PROPERTYID DEL COMBO
        //SearchFilter searchFilter = new SearchFilter(cmbFieldtoSearch.getItemCaption(cmbFieldtoSearch.getValue()), searchTerm, txtSearchName.getValue());   //LINEA PARA MOSTRAR 
        
        if (chkSaveSearch.getValue()) {
            app.saveSearch(searchFilter);
        }
        app.search(searchFilter);
    }

}
