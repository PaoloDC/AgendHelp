package com.example.paolo.agendhelp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Paolo2 on 18/01/2018.
 */

public class Allarme extends Activity {

    public static final String MESSAGGIO = "MESSAGGIO";
    public static final String TEMPO = "TEMPO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allarme_layout);

        String msg = getIntent().getStringExtra(MESSAGGIO);
        long tempoAllarme = getIntent().getLongExtra(TEMPO,-1);

        TextView tvOraEsatta = findViewById(R.id.tvOraEsatta);
        TextView tvMessaggioAllarme = findViewById(R.id.tvMessaggioAllarme);
        tvMessaggioAllarme.setText(msg);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date oggi = new Date(tempoAllarme);
        System.out.println("attuale: " + sdf.format(oggi));
        String attuale = sdf.format(oggi);

        tvOraEsatta.setText(attuale);
    }

    public void clickEsci(View view) {
        finish();
    }

}
