package com.example.paolo.agendhelp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Paolo2 on 18/01/2018.
 */

public class Allarme extends Activity {

    public static final String MESSAGGIO = "MESSAGGIO";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allarme_layout);

        String msg = getIntent().getStringExtra("MESSAGGIO");
        TextView tvMessaggioAllarme = findViewById(R.id.tvMessaggioAllarme);
        tvMessaggioAllarme.setText(msg);

        TextView tvOraEsatta = findViewById(R.id.tvOraEsatta);

        GregorianCalendar ora = new GregorianCalendar();

        tvOraEsatta.setText(""+ ora.get(Calendar.HOUR_OF_DAY) + " : " + ora.get(Calendar.MINUTE));
    }

    public void clickEsci(View view) {
        finish();
    }

}
