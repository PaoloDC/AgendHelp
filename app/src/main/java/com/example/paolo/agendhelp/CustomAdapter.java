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
    private LayoutInflater inflater;

    public CustomAdapter(Context context, int resourceId, List<Attivita> objects) {
        super(context, resourceId, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (null == v) {
            Log.d("DEBUG", "Inflating view");
            v = inflater.inflate(R.layout.elemento_lista, null);
        }

        // prendiamo dalla view, il contatto nella posizione position
        Attivita a = getItem(position);
        Log.d("DEBUG", "attività in posizione: " + position + ", " + a.toString());

        TextView nome;
        TextView ora;
        CheckBox checkBox;

        nome = v.findViewById(R.id.elemento_nome);
        ora = v.findViewById(R.id.elemento_ora);
        checkBox = v.findViewById(R.id.checkElimina);

        TextView data = v.findViewById(R.id.elemento_data);

        nome.setText(a.getNome());
        data.setText(a.getData());
        ora.setText(a.getOra());
        checkBox.setChecked(false);

        return v;
    }
}
