package com.example.paolo.agendhelp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Paolo on 18/01/2018.
 */

public class Allarme extends Activity {

    public static final String MESSAGGIO = "MESSAGGIO";
    private String nomeAttivita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allarme_layout);

        nomeAttivita = getIntent().getStringExtra(MESSAGGIO);

        TextView tvOraEsatta = findViewById(R.id.tvOraEsatta);
        TextView tvMessaggioAllarme = findViewById(R.id.tvMessaggioAllarme);
        tvMessaggioAllarme.setText(nomeAttivita);

       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
       // Date oggi = new Date(tempoAllarme);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        fmt.setTimeZone(TimeZone.getTimeZone("GMT + 1"));

        /**Settaggio anno mese giorno ora minuti e secondi**/
        int anno = c.get(Calendar.YEAR);
        int mese = c.get(Calendar.MONTH) + 1;
        int giorno = c.get(Calendar.DAY_OF_MONTH);
        int ora = c.get(Calendar.HOUR_OF_DAY)  ;
        int minuti = c.get(Calendar.MINUTE);
        int secondi = c.get(Calendar.SECOND);

        String strDateOdierna = giorno + "/" + mese + "/" + anno + " " + ora + ":" + minuti + ":" + secondi;

        tvOraEsatta.setText(strDateOdierna);
    }

    public void clickEsci(View view) {
        MainActivity.eliminaAttivitaDaLista(nomeAttivita);
        finish();
    }

}
