/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.tutorial.agenda.data;

import com.vaadin.data.util.BeanItemContainer;
import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Eduardo
 */
public class PersonContainer extends BeanItemContainer<Person> implements Serializable {

    public PersonContainer() throws InstantiationException,
            IllegalAccessException {
        super(Person.class);
    }

    public static final Object[] NATURAL_COL_ORDER = new Object[]{
        "nombre", "apPaterno", "email", "numTel", "direccion",
        "codPost", "ciudad"};

    public static final String[] COL_HEADERS_ENGLISH = new String[]{
        "First name", "Last name", "Email", "Phone number",
        "Street Address", "Postal Code", "City"};

    public static PersonContainer createWithTestData() {
        final String[] fnames = {"Pedro", "Alicia", "Josue", "Miguel", "Olivia",
            "Nina", "Alex", "Rita", "Daniel", "Humberto", "Enrique", "Rene",
            "Lisa", "Marcela"};
        final String[] lnames = {"Sanchez", "Gonzalez", "Soria", "Benítez",
            "Clavel", "Suarez", "Velez", "López", "Argüeyez", "Gomez",
            "Rowling", "Barista", "Rosas", "Schneider", "Torres"};
        final String cities[] = {"Amsterdam", "Berlin", "Helsinki",
            "Hong Kong", "London", "Luxemburg", "New York", "Oslo",
            "Paris", "Rome", "Stockholm", "Tokyo", "Turku"};
        final String streets[] = {"4215 Blandit Av.", "452-8121 Sem Ave",
            "279-4475 Tellus Road", "4062 Libero. Av.", "7081 Pede. Ave",
            "6800 Aliquet St.", "P.O. Box 298, 9401 Mauris St.",
            "161-7279 Augue Ave", "P.O. Box 496, 1390 Sagittis. Rd.",
            "448-8295 Mi Avenue", "6419 Non Av.",
            "659-2538 Elementum Street", "2205 Quis St.",
            "252-5213 Tincidunt St.", "P.O. Box 175, 4049 Adipiscing Rd.",
            "3217 Nam Ave", "P.O. Box 859, 7661 Auctor St.",
            "2873 Nonummy Av.", "7342 Mi, Avenue",
            "539-3914 Dignissim. Rd.", "539-3675 Magna Avenue",
            "Ap #357-5640 Pharetra Avenue", "416-2983 Posuere Rd.",
            "141-1287 Adipiscing Avenue", "Ap #781-3145 Gravida St.",
            "6897 Suscipit Rd.", "8336 Purus Avenue", "2603 Bibendum. Av.",
            "2870 Vestibulum St.", "Ap #722 Aenean Avenue",
            "446-968 Augue Ave", "1141 Ultricies Street",
            "Ap #992-5769 Nunc Street", "6690 Porttitor Avenue",
            "Ap #105-1700 Risus Street",
            "P.O. Box 532, 3225 Lacus. Avenue", "736 Metus Street",
            "414-1417 Fringilla Street", "Ap #183-928 Scelerisque Road",
            "561-9262 Iaculis Avenue"};
        PersonContainer c = null;
        Random r = new Random(0);
        try {
            c = new PersonContainer();
            for (int i = 0; i < 100; i++) {
                Person p = new Person();
                p.setNombre(fnames[r.nextInt(fnames.length)]);
                p.setApPaterno(lnames[r.nextInt(lnames.length)]);
                p.setCiudad(cities[r.nextInt(cities.length)]);
                p.setEmail(p.getNombre().toLowerCase() + "."
                        + p.getApPaterno().toLowerCase() + "@vaadin.com");
                p.setNumTel("+358 02 555 " + r.nextInt(10) + r.nextInt(10)
                        + r.nextInt(10) + r.nextInt(10));
                int n = r.nextInt(100000);
                if (n < 10000) {
                    n += 10000;
                }
                p.setCodPost(n);
                p.setDireccion(streets[r.nextInt(streets.length)]);
                c.addItem(p);
            }
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return c;
    }
}
