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

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


    }

    public void clickAccedi(View view) {
        EditText etTel = findViewById(R.id.etTel);
        EditText etPass = findViewById(R.id.etPass);

        String telefono = etTel.getText().toString();
        String password = etPass.getText().toString();

        if(telefono.equals("")){
            Toast.makeText(this, "Digita il tuo numero di telefono per accedere", Toast.LENGTH_SHORT).show();
        } else if(password.equals("")){
            Toast.makeText(this, "Digita la tua password per accedere", Toast.LENGTH_SHORT).show();
        } else {
            boolean login = GestoreFile.login(this,telefono,password);
            if(login){
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Credenziali errate, accesso non eseguito", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clickRegistrati(View view) {
        Intent i = new Intent(this, RegistrazioneActivity.class);
        startActivity(i);
    }
}
