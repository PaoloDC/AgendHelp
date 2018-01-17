package com.example.paolo.agendhelp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    CustomAdapter customAdapter;
    int positionClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          /*Lista */
        lista= findViewById(R.id.mialista);


        /*Custom adapter*/
        customAdapter = new CustomAdapter(this, R.layout.elemento_lista, new ArrayList<Attivita>());

        lista.setAdapter(customAdapter);
        for(int x =0;x<4;x++){
            Attivita a  = new Attivita("Pillola","20/11/17","15:00",true,"Ogni giorni",true);

            customAdapter.add(a);
        }
/*
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Attivita a = (Attivita) customAdapter.getItem(position);
                    String  str  = lista.getItemAtPosition(position).toString();
                    AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                    b.setTitle("Titolo");
                    b.setMessage("Sei sicuro di volere eliminare ? ");
                    b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            customAdapter.remove(a);

                            customAdapter.notifyDataSetChanged();
                        }
                    });
                    b.setNegativeButton("NO",null);
                    AlertDialog al = b.create();
                    al.show();




                }
            });*/

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionClicked=position;
                Log.d("DEBUG","Posizione= "+ positionClicked);

            }
        });


    }

    public void clickButtonInserisci(View view) {
        Intent i = new Intent(this,InserisciActivity.class);
        startActivity(i);
    }
    public void onClickElimina(View view){
            final Attivita a = (Attivita) customAdapter.getItem(positionClicked);
        Log.d("DEBUG","Posizione rimossa =  "+ positionClicked);
            customAdapter.remove(a);
            customAdapter.notifyDataSetChanged();
    }
}
