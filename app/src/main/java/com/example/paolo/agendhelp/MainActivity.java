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
    private ListView lista;
    private CustomAdapter customAdapter;
    private ArrayList<Attivita> daEliminare;
    public static String nomeAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean check = GestoreFile.checkAccountEsistente(this);
        if(!check) {
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, "Ciao " + nomeAccount, Toast.LENGTH_SHORT).show();
        }



        daEliminare = new ArrayList<>();

        lista = findViewById(R.id.mialista);
        customAdapter = new CustomAdapter(this, R.layout.elemento_lista, new ArrayList<Attivita>());
        lista.setAdapter(customAdapter);

        ArrayList<Attivita> listaAttivita;

        if (null == savedInstanceState) {
            listaAttivita = new ArrayList<>();

            for(int i=0 ; i < customAdapter.getCount() ; i++){
                listaAttivita.add(customAdapter.getItem(i));
            }

            listaAttivita.add(new Attivita("Pillola", "20/01/17", "15:00", true, "Ogni giorno", true));
            listaAttivita.add(new Attivita("Gocce per diabete", "17/01/2017", "16:00", false, "Una volta", false));
        } else {
            listaAttivita = (ArrayList<Attivita>) savedInstanceState.getSerializable("LISTA_ATTIVITA");
        }

        for (int i=0 ; i < listaAttivita.size() ; i++ ){
            customAdapter.add(listaAttivita.get(i));
        }

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
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            Attivita a = (Attivita) data.getSerializableExtra("ATTIVITA");
            customAdapter.add(a);
            customAdapter.notifyDataSetChanged();
        }
    }

    public void onClickElimina(View view){
        if(daEliminare.size() == 0){
            Toast.makeText(this, "Nessuna attività selezionata per l'eliminazione!", Toast.LENGTH_SHORT).show();
        } else {
            for(int i=0 ; i < daEliminare.size() ; i++){
                customAdapter.remove(daEliminare.get(i));
            }
            daEliminare.clear();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        ArrayList<Attivita> lista = new ArrayList<>();

        for(int i=0 ; i < customAdapter.getCount() ; i++){
            lista.add(customAdapter.getItem(i));
        }

        savedInstanceState.putSerializable("LISTA_ATTIVITA",lista);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void clickButtonEsci(View view) {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}
