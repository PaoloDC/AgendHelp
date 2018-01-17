package com.example.paolo.agendhelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String DEBUG = "DEBUG";
    ListView lista;
    CustomAdapter customAdapter;
    ArrayList<Attivita> daEliminare =new ArrayList<>() ;
    ArrayList<Attivita> listaAttività = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daEliminare = new ArrayList<>();

        lista= findViewById(R.id.mialista);
        customAdapter = new CustomAdapter(this, R.layout.elemento_lista, new ArrayList<Attivita>());
        lista.setAdapter(customAdapter);

        customAdapter.add(new Attivita("Pillola","20/01/17","15:00",true,"Ogni giorno",true));
        listaAttività.add(new Attivita("Pillola","20/01/17","15:00",true,"Ogni giorno",true));
        customAdapter.add(new Attivita("Gocce per diabete","17/01/2017","16:00",false,"Una volta",false));
        listaAttività.add(new Attivita("Gocce per diabete","17/01/2017","16:00",false,"Una volta",false));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Attivita a = customAdapter.getItem(position);

                LinearLayout ll = (LinearLayout) view;
                CheckBox cb = (CheckBox) ll.getChildAt(0);

                if(cb.isChecked()){
                    cb.setChecked(false);
                    daEliminare.remove(a);
                } else{
                    cb.setChecked(true);
                    daEliminare.add(a);
                }

                Log.d(DEBUG,"Posizione: " + position + "Attivita: " +a);

            }
        });
    }

    public void clickButtonInserisci(View view) {
        Intent i = new Intent(this,InserisciActivity.class);
        startActivity(i);
    }
    public void onClickElimina(View view){
        if(daEliminare.size() == 0){
            Toast.makeText(this, "Nessuna attività selezionata!", Toast.LENGTH_SHORT).show();
        } else {
         //   for(Attivita a: daEliminare){ // problema qui se elimino due eventi si stoppa l'app
            //    customAdapter.remove(a);
              //  listaAttività.remove(a);
              //  daEliminare.remove(a);// bug risolto :)

           // }
            /*Questo funziona sempre :)*/
            for(int k = 0;k<daEliminare.size();k++){
                customAdapter.remove(daEliminare.get(k));
                listaAttività.remove(daEliminare.get(k));
                daEliminare.remove(k);
                k=k-1; /*L'arraylist diventa minore mentre k aumenta va a finire che non controllo tutti gli elementi */
            }

            customAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putSerializable("LISTAATTIVITA",listaAttività);
        savedInstanceState.putSerializable("LISTADAELIMINARE",daEliminare);
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState!=null){

            listaAttività = (ArrayList<Attivita>) savedInstanceState.getSerializable("LISTAATTIVITA");
            Log.d("DEBUG" , "CI vado");
            for(int x = 0; x<listaAttività.size();x++){
                customAdapter.add(listaAttività.get(x));
                
            }
            /*alla lista aggiungo il set adapter*/
            lista.setAdapter(customAdapter);

            daEliminare=(ArrayList<Attivita>) savedInstanceState.getSerializable("LISTADAELIMINARE");
        }
        super.onRestoreInstanceState(savedInstanceState);
    }
}
