package com.example.paolo.agendhelp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Paolo2 on 17/01/2018.
 */

public class InserisciActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserisci_layout);

    }

    public void clickBackButton(View view) {
        finish();
    }

    public void clickInserisciAttivita(View view) {
    }
}
