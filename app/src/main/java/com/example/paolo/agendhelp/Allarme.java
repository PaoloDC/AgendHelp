package com.example.paolo.agendhelp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Paolo2 on 18/01/2018.
 */

public class Allarme extends Activity {

    public static final String MESSAGGIO = "MESSAGGIO";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allarme_layout);

        String msg = getIntent().getStringExtra(MESSAGGIO);
        TextView tvMessaggioAllarme = findViewById(R.id.tvMessaggioAllarme);
        tvMessaggioAllarme.setText(msg);
    }

    public void clickEsci(View view) {
        MainActivity.tvProva.setText("No Alarm");
        finish();
    }

}