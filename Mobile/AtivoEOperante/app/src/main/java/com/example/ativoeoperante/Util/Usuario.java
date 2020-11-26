package com.example.ativoeoperante.Util;

import android.app.Application;

public class Usuario extends Application {

    private String key;
    private int id;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
