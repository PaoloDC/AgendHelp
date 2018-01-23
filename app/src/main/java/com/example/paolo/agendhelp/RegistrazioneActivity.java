package com.example.paolo.agendhelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Paolo2 on 23/01/2018.
 */

public class RegistrazioneActivity extends Activity {

    private static final String FILENAME = "utente.dat";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_layout);

    }

    public void clickRegistrazione(View v){
        EditText etTelefono = findViewById(R.id.etTelefono);
        EditText etNome = findViewById(R.id.etNome);
        EditText etCognome = findViewById(R.id.etCognome);
        EditText etPassword = findViewById(R.id.etPassword);

        String telefono = etTelefono.getText().toString();
        String nome = etNome.getText().toString();
        String cognome = etCognome.getText().toString();
        String password = etPassword.getText().toString();

        if(telefono.equals("")) {
            Toast.makeText(this, "Il campo telefono è vuoto", Toast.LENGTH_SHORT).show();
        } else if(nome.equals("")) {
            Toast.makeText(this, "Il campo nome è vuoto", Toast.LENGTH_SHORT).show();
        } else if(cognome.equals("")) {
            Toast.makeText(this, "Il campo cognome è vuoto", Toast.LENGTH_SHORT).show();
        } else if(password.equals("")) {
            Toast.makeText(this, "Il campo password è vuoto", Toast.LENGTH_SHORT).show();
        } else {
            GestoreFile.salvaImpostazioniUtente(this,nome,cognome,telefono,password,"no");
            Toast.makeText(this, "Registrazione avvenuta correttamente", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}
