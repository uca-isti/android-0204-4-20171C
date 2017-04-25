package uca.apps.isi.nicamustgo.models;

import io.realm.RealmObject;

/**
 * Created by isi3 on 17/4/2017.
 */

public class Categoria extends RealmObject{

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
