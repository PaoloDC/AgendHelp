package com.example.paolo.agendhelp;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Andrea95 on 17/01/2018.
 */

public class CustomAdapter extends ArrayAdapter<Attivita> {
    private int resource;
    private LayoutInflater inflater;


    public CustomAdapter(Context context, int resourceId, List<Attivita> objects) {
        super(context, resourceId, objects);
        resource = resourceId;
            /*permette di istanziare una risorsa xml*/
        inflater = LayoutInflater.from(context);
    }
}
