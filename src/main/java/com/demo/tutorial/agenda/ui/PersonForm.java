/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.tutorial.agenda.ui;

import com.demo.tutorial.agenda.MyUI;
import com.demo.tutorial.agenda.data.Person;
import com.demo.tutorial.agenda.data.PersonContainer;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.*;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import java.text.NumberFormat;
import java.util.Locale;
import org.vaadin.csvalidation.CSValidator;

/**
 *
 * @author Eduardo
 */
public class PersonForm extends FormLayout implements Button.ClickListener {

    private Button btnGuardar = new Button("Guardar", (ClickListener) this);
    private Button btnCancelar = new Button("Cancelar", (ClickListener) this);
    private Button btnEditar = new Button("Editar", (ClickListener) this);
    private MyUI app;
    private FieldGroup fldBinder = new FieldGroup();
    private Person nvaPersona = null;
    private boolean newContactMode = false;
    //private final ComboBox cmbCiudad = new ComboBox("City");

    @PropertyId("nombre")
    TextField txtNombre = new TextField("Nombre");
    @PropertyId("apPaterno")
    TextField txtApPaterno = new TextField("Apellido Paterno");
    @PropertyId("email")
    TextField txtEmail = new TextField("Email");
    @PropertyId("numTel")
    TextField txtTelefono = new TextField("Teléfono");
    @PropertyId("direccion")
    TextField txtDireccion = new TextField("Dirección");
    @PropertyId("codPost")
    TextField txtCodPostal = new TextField("Código Postal");
    @PropertyId("ciudad")
    //TextField txtCiudad = new TextField("Ciudad");
    ComboBox cmbCiudad = new ComboBox("Ciudad");

    HorizontalLayout footer = new HorizontalLayout();

    public PersonForm(MyUI app) {
        this.app = app;

        cmbCiudad.setNullSelectionAllowed(false);
        cmbCiudad.setTextInputAllowed(false);

        
        /* Poblar cmbCiudad usando las ciudades en el data container */
        PersonContainer ds = app.getDataSource();
        for (Person person : ds.getItemIds()) {                                         //OPCION 1
            String city = (String) person.getCiudad();
            cmbCiudad.addItem(city);
        }
        /*for (Iterator<Person> it = ds.getItemIds().iterator(); it.hasNext();) {       //OPCION 2
         String city = (it.next()).getCiudad();
         cmbCiudad.addItem(city);
         }*/

        /*setFormFieldFactory(new DefaultFieldFactory() {
         @Override
         public Field createField(Item item, Object propertyId,
         Component uiContext) {
         if (propertyId.equals("city")) {
         return cities;
         }
         Field field = super.createField(item, propertyId, uiContext);
         if (propertyId.equals("postalCode")) {
         TextField tf = (TextField) field;

         tf.setNullRepresentation("");

         tf.addValidator(new RegexpValidator("[1-9][0-9]{4}",
         "Postal code must be a five digit number and cannot start with a zero."));
         tf.setRequired(true);
         }

         return field;
         }
         });*/
        fldBinder.setBuffered(true);
        /**
         * * Si esta en FALSE el buffered actualiza inmediatamente con tan solo
         * seleccionar otra fila **
         */
        //fldBinder.bind(txtNombre, "nombre");
        //fldBinder.bind(txtApPaterno, "apPaterno");
        footer.setSpacing(true);
        footer.addComponent(btnGuardar);
        footer.addComponent(btnCancelar);
        footer.addComponent(btnEditar);
        footer.setVisible(false);

        /**
         * Para dar formato a numeros y no tenga separado de decimales
         */
        txtCodPostal.setNullRepresentation("");
        StringToIntegerConverter plainIntegerConverter = new StringToIntegerConverter() {
            @Override
            protected java.text.NumberFormat getFormat(Locale locale) {
                NumberFormat format = super.getFormat(locale);
                format.setGroupingUsed(false);
                return format;
            }
        };
        txtCodPostal.setConverter(plainIntegerConverter);
        
        String regexp = "[1-9][0-9]{6}";
        String message = "{0} Código Postal debe ser de 5 digitos numéricos y no puede empezar con cero.";
        CSValidator validator = new CSValidator();
        validator.extend(txtCodPostal);
        validator.setRegExp(regexp);
        validator.setPreventInvalidTyping(true);
        validator.setErrorMessage("Deber ser número");
        txtCodPostal.setRequired(true);
        txtCodPostal.setValidationVisible(false);
        Validator postalCodeValidator = new RegexpValidator(regexp, message);
        //txtCodPostal.addValidator(postalCodeValidator);
        //txtCodPostal.addValidator(new StringLengthValidator(
        //        "La longitud debe de ser de 5 digitos (was {0})",
        //        1, 5, false));
        
        
        txtEmail.addValidator(new EmailValidator("Invalido email"));
        
        
        //btnGuardar.addClickListener(this);
        //btnCancelar.addClickListener(this);
        //btnEditar.addClickListener(this);
        addComponent(txtNombre);
        addComponent(txtApPaterno);
        addComponent(txtEmail);
        addComponent(txtTelefono);
        addComponent(txtDireccion);
        addComponent(txtCodPostal);
        addComponent(cmbCiudad);
        addComponent(footer);

    }

    @Override
    public void buttonClick(ClickEvent event) {
        final Button source = event.getButton();
        if (source == btnGuardar) {
            System.out.println("txtCodPostal1 = " + txtCodPostal.getType());
            System.out.println("txtCodPostal2 = " + txtCodPostal.getConverter().getModelType());
            System.out.println("txtCodPostal3 = " + txtCodPostal.getConvertedValue());
            System.out.println("txtCodPostal4 = " + txtCodPostal.getValue());
            try {
                enableValidationMessages();
                fldBinder.commit();
                Notification.show("Changes committed!",
                        Type.TRAY_NOTIFICATION);
                if (newContactMode) {
                    System.out.println("if entrar = ");
                    Item addedItem = app.getDataSource().addItem(nvaPersona);
                    setItemDataSource(addedItem);
                    newContactMode = false;
                }
                setReadOnly(true);
            //} catch (FieldGroup.CommitException e) {
            } catch (FieldGroup.CommitException e) {
                Notification
                        .show("Commit failed: " + e.getCause().getMessage(),
                                Type.TRAY_NOTIFICATION);
            }
        } else if (source == btnCancelar) {
            if (newContactMode) {
                newContactMode = false;
                setItemDataSource(null);
            } else {
                fldBinder.discard();
            }
            setReadOnly(true);
        } else if (source == btnEditar) {
            txtNombre.focus();
            setReadOnly(false);
        }
    }

    /*public Item getItemDataSource(){
     Item item = fldBinder.getItemDataSource();
     return item;
     }*/
    public void setItemDataSource(Item newDataSource) {
        newContactMode = false;

        if (newDataSource != null) {
            for (Field field : fldBinder.getFields()) {
                field.removeAllValidators();
            }
            fldBinder.setItemDataSource(newDataSource);
            fldBinder.bindMemberFields(this);
            setReadOnly(true);
            footer.setVisible(true);

            //System.out.println("txtCodPostal1 = " + txtCodPostal.getType());
            //System.out.println("txtCodPostal2 = " + txtCodPostal.getConverter().getModelType());
            //System.out.println("txtCodPostal3 = " + txtCodPostal.getConvertedValue());
        } else {
            fldBinder.setItemDataSource(null);
            fldBinder.bindMemberFields(this);
        }
    }

    public void editPerson(Person person) {
        if (person != null) {
            BeanItem<Person> item = new BeanItem<Person>(person);
            fldBinder.setItemDataSource(item);
            /**
             * A partir de esta linea este codigo sirve para construir los
             * campos de forma dinamica a partir de los datos que proviene de la
             * tabla
             *
             */
            /*for (Object propertyId : item.getItemPropertyIds()) {
             Field field = fldBinder.buildAndBind(propertyId);
             super.addComponent(field);
             }*/

            //fldBinder.bindMemberFields(this);
            txtNombre.focus();
            setReadOnly(true);
            footer.setVisible(true);
        } else {
            person = new Person();
            footer.setVisible(false);
        }
    }

    public void addContact() {
        // Create a temporary item for the form
        nvaPersona = new Person();
        setItemDataSource(new BeanItem(nvaPersona));
        newContactMode = true;
        setReadOnly(false);
    }

    public void enableValidationMessages() {
        txtCodPostal.setValidationVisible(true);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        fldBinder.setReadOnly(readOnly);    //deshabilita todo el formulario
        btnGuardar.setVisible(!readOnly);
        btnCancelar.setVisible(!readOnly);
        btnEditar.setVisible(readOnly);
    }

}
