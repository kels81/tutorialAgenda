package com.demo.tutorial.agenda;

import com.demo.tutorial.agenda.data.PersonContainer;
import com.demo.tutorial.agenda.ui.AyudaView;
import com.demo.tutorial.agenda.ui.HelpWindow;
import com.demo.tutorial.agenda.ui.ListView;
import com.demo.tutorial.agenda.ui.NavigationTree;
import com.demo.tutorial.agenda.ui.PersonForm;
import com.demo.tutorial.agenda.ui.PersonList;
import com.demo.tutorial.agenda.ui.SearchFilter;
import com.demo.tutorial.agenda.ui.SearchView;
import com.demo.tutorial.agenda.ui.ShareView;
import com.demo.tutorial.agenda.ui.SharingOptions;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.*;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.Locale;

/**
 *
 */
@Title("Agenda")
@Theme("contacts")  //runo //contacts    //mytheme
@Widgetset("com.demo.tutorial.agenda.MyAppWidgetset")
public class MyUI extends UI implements Button.ClickListener, Property.ValueChangeListener, ItemClickEvent.ItemClickListener {
//public class MyUI extends UI implements Button.ClickListener, Property.ValueChangeEvent, ItemClickEvent.ItemClickListener {

    private Button nvoContacto = new Button("Nuevo Contacto");
    private Button busqueda = new Button("Búsqueda");
    private Button share = new Button("Share");
    private Button ayuda = new Button("Ayuda");

    private HorizontalSplitPanel horizontalSplit = new HorizontalSplitPanel();

    private NavigationTree tree = new NavigationTree(this);
    private ListView listView = null;
    private PersonList personList = null;
    private PersonForm personForm = null;
    private SearchView searchView = null;
    private ShareView shareView = null;
    private AyudaView ayudaView = null;

    private HelpWindow helpWindow = null;
    private SharingOptions sharingOptions = null;

    private PersonContainer dataSource = PersonContainer.createWithTestData();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Locale locale = Locale.getDefault();
        this.setLocale(locale);
        this.getSession().setLocale(locale);
        
        buildMainLayout();
    }

    private void buildMainLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        layout.addComponent(createToolbar());
        layout.addComponent(horizontalSplit);

        layout.setExpandRatio(horizontalSplit, 1);

        horizontalSplit.setSplitPosition(200, Unit.PIXELS);
        horizontalSplit.setFirstComponent(tree);

        setMainComponent(getListView());

        setContent(layout);
        //setTheme("runo");
        //addWindow(new HelpWindow());

    }

    public HorizontalLayout createToolbar() {
        HorizontalLayout lo = new HorizontalLayout();
        lo.addComponent(nvoContacto);
        lo.addComponent(busqueda);
        lo.addComponent(share);
        lo.addComponent(ayuda);
        lo.setMargin(true);
        lo.setSpacing(true);
        
        lo.setWidth("100%");
        Embedded em = new Embedded("", new ThemeResource("images/logo.png"));
        lo.addComponent(em);
        lo.setComponentAlignment(em, Alignment.MIDDLE_RIGHT);
        lo.setExpandRatio(em, 1);
        lo.setStyleName("toolbar");
        
        //nvoContacto.setIcon(FontAwesome.PLUS);
        nvoContacto.setIcon(new ThemeResource("icons/32/document-add.png"));
        busqueda.addClickListener(this);
        //busqueda.setIcon(FontAwesome.SEARCH);
        busqueda.setIcon(new ThemeResource("icons/32/folder-add.png"));
        share.addClickListener(this);
        //share.setIcon(FontAwesome.SHARE);
        share.setIcon(new ThemeResource("icons/32/users.png"));
        ayuda.addClickListener(this);
        //ayuda.setIcon(FontAwesome.QUESTION);
        ayuda.setIcon(new ThemeResource("icons/32/help.png"));

        /*busqueda.addClickListener(new ClickListener() {
         @Override
         public void buttonClick(ClickEvent event) {
         showSearchView();
         }
         });*/
        return lo;

    }

    @Override
    public void buttonClick(ClickEvent event) {
        final Button source = event.getButton();
        if (source == busqueda) {
            showSearchView();
        } else if (source == share) {
            //EJEMPLO SUB-WINDOW
            showShareWindow();

            //EJEMPLO CAMBIAR PANEL
            //showShare();
        } else if (source == ayuda) {
            //EJEMPLO SUB-WINDOW
            showHelpWindow();

            //EJMPLO CAMBIAR PANEL
            //showAyuda();
        } else if (source == nvoContacto) {
            addNewContanct();
        }

    }

    //EJEMPLO CAMBIAR PANEL
    private void showSearchView() {
        setMainComponent(getSearchView());
    }

    private void showListView() {
        setMainComponent(getListView());
    }

    private void addNewContanct() {
        showListView();
        personForm.addContact();
    }

    //EJEMPLO SUB-WINDOW
    private void showShareWindow() {
        addWindow(getSharingOptions());
    }

    //EJEMPLO SUB-WINDOW
    private void showHelpWindow() {
        addWindow(getHelpWindow());
    }
    /*
     //EJEMPLO CAMBIAR PANEL
     private void showShare() {
     setMainComponent(getShare());
     }
     //EJEMPLO CAMBIAR PANEL
     private void showAyuda() {
     setMainComponent(getAyuda());
     }
     */

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        Property property = event.getProperty();
        if (property == personList) {

            //personForm.editPerson((Person) personList.getValue());
            Item item = personList.getItem(personList.getValue());
            //Notification.show(item.toString(), Notification.Type.HUMANIZED_MESSAGE);
            personForm.setItemDataSource(item);

        }
    }

    @Override
    public void itemClick(ItemClickEvent event) {
        if (event.getSource() == tree) {
            Object itemId = event.getItemId();
            if (itemId != null) {
                if (NavigationTree.MOSTRAR_TODO.equals(itemId)) {
                    showListView();
                } else if (NavigationTree.BUSCAR.equals(itemId)) {
                    showSearchView();
                } else if (itemId instanceof SearchFilter) {
                    search((SearchFilter) itemId);
                } else if (itemId instanceof SearchFilter) {
                    search((SearchFilter) itemId);
                }
            }
        }
    }

    private ListView getListView() {
        if (listView == null) {
            personList = new PersonList(this);
            personForm = new PersonForm(this);
            listView = new ListView(personList, personForm);
        }
        return listView;
    }

    //EJEMPLO SUB-WINDOW
    private HelpWindow getHelpWindow() {
        if (helpWindow == null) {
            helpWindow = new HelpWindow();
        }
        return helpWindow;
    }

    //EJEMPLO SUB-WINDOW
    private SharingOptions getSharingOptions() {
        if (sharingOptions == null) {
            sharingOptions = new SharingOptions();
        }
        return sharingOptions;
    }

    //EJEMPLO CAMBIAR PANEL
    private SearchView getSearchView() {
        if (searchView == null) {
            searchView = new SearchView(this);
        }
        return searchView;
    }
    /*
     //EJEMPLO CAMBIAR PANEL
     private ShareView getShare() {
     if (shareView == null) {
     shareView = new ShareView(this);
     }
     return shareView;
     }
     //EJEMPLO CAMBIAR PANEL
     private AyudaView getAyuda() {
     if (ayudaView == null) {
     ayudaView = new AyudaView(this);
     }
     return ayudaView;
     }
     */

    public PersonContainer getDataSource() {
        return dataSource;
    }

    private void setMainComponent(Component c) {
        horizontalSplit.setSecondComponent(c);

    }

    public void search(SearchFilter searchFilter) {
        //limpiar filtros previos
        getDataSource().removeAllContainerFilters();
        getDataSource().addContainerFilter(searchFilter.getPropertyId(), searchFilter.getTerm(), true, true);
        showListView();
        showNotification(searchFilter);
    }

    public void saveSearch(SearchFilter searchFilter) {
        tree.addItem(searchFilter);
        tree.setParent(searchFilter, NavigationTree.BUSCAR);
        tree.setChildrenAllowed(searchFilter, false);

        tree.expandItem(NavigationTree.BUSCAR);
        tree.setValue(searchFilter);
    }

    private void showNotification(SearchFilter searchFilter) {
        if (getDataSource().size() > 0) {
            //getMainWindow().showNotification(
            Notification.show(
                    "Buscado por " + searchFilter.getItemCaption() + "=*"
                    + searchFilter.getTerm() + "*, encontrado "
                    + getDataSource().size() + " resultado(s).",
                    Notification.Type.TRAY_NOTIFICATION);
        } else {
            //getMainWindow().showNotification(
            Notification.show(
                    "Buscado por " + searchFilter.getItemCaption() + "=*"
                    + searchFilter.getTerm() + "*, persona no encontrada.",
                    Notification.Type.TRAY_NOTIFICATION);
        }
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
