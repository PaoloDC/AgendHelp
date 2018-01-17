package com.example.paolo.agendhelp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Andrea95 on 17/01/2018.
 */

public class CustomAdapter extends ArrayAdapter<Attivita> {
    private int resource;
    private LayoutInflater inflater;
    int positioncb;
    Boolean checkBoxIsChecked;

    public CustomAdapter(Context context, int resourceId, List<Attivita> objects) {
        super(context, resourceId, objects);
        resource = resourceId;
            /*permette di istanziare una risorsa xml*/
        inflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(null == v){
            Log.d("DEBUG","Inflating view");
            v= inflater.inflate(R.layout.elemento_lista,null);
        }

        /* prendiamo dalla view, il contatto nella posizione position*/
        Attivita a = getItem(position);
        Log.d("DEBUG","Contatto in posizione " + position + a.toString());

        TextView nome;
        TextView data;
        CheckBox checkBox;

        nome = v.findViewById(R.id.elemento_nome);
        data =v.findViewById(R.id.elemento_ora);
        checkBox=v.findViewById(R.id.checkElimina);




        nome.setText(a.getNome());
        data.setText(a.getOra());


        return v;
    }

}
