package com.example.paolo.agendhelp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Paolo2 on 17/01/2018.
 */

public class InserisciActivity extends AppCompatActivity {
    //costanti
    public static final String DEBUG = "DEBUG";
    final String[] scelteRipetizione = {
            "Una volta al giorno",
            "Due volte al giorno",
            "Una volta la settimana",
            "Una volta al mese"
    };

    final String testo_esempio = "---";


    //riferimenti al layout
    private TextView tvAvviso;
    private TextView tvData;
    private TextView tvImportanza;
    private TextView tvOra;
    private TextView tvRipetizione;
    private TextView tvSuoneria;
    private EditText etNomeAttivita;

    private String ora;
    private String ripetizione;
    private boolean importanza;
    private boolean suoneria;
    private String avviso;
    private boolean selezionataDataOdierna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserisci_layout);

        tvAvviso = findViewById(R.id.tvAvviso);
        tvData = findViewById(R.id.tvData);
        tvImportanza = findViewById(R.id.tvImportanza);
        tvOra = findViewById(R.id.tvOra);
        tvRipetizione = findViewById(R.id.tvRipetizione);
        tvSuoneria = findViewById(R.id.tvSuoneria);
        etNomeAttivita = findViewById(R.id.etNomeAttivita);



    }

    public void clickBack(View view) {
        finish();
    }

    public void clickData(View v) {

        selezionataDataOdierna = false;

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,

                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anno, int mese, int giorno) {
                        mese++;
                        String stringaData = giorno + "/" + mese + "/" + anno;

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
                        Date dataScelta = null;
                        try {
                            dataScelta = sdf.parse(stringaData);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date oggi = new Date();

                        Log.d(DEBUG,"Oggi: " + oggi + "\nData scelta: " + dataScelta);
                        if(sdf.format(oggi).equals(sdf.format(dataScelta))){
                            Log.d(DEBUG,"Selezionata la data odierna");
                            selezionataDataOdierna = true;
                            tvData.setText(stringaData);
                        }
                        else if(dataScelta.before(oggi)){
                            Toast.makeText(InserisciActivity.this,
                                    "La data scelta è antecedente alla data attuale!",
                                    Toast.LENGTH_SHORT).show();
                            tvData.setText(testo_esempio);
                        } else {
                            tvData.setText(stringaData);
                        }
                    }

                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void clickOra(View v) {
        String testo = tvData.getText().toString();
        Log.d(DEBUG,testo + "            " + testo_esempio);
        if (testo.equals(testo_esempio)){
            Toast.makeText(this, "Seleziona prima la data", Toast.LENGTH_SHORT).show();
            return;
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int oraSelezionata, int minutoSelezionato) {

                        String stringaOra = oraSelezionata + ":" + minutoSelezionato;
                        if(checkSelezioneOrario(stringaOra)){
                            tvOra.setText(stringaOra);
                        } else {
                            tvOra.setText(testo_esempio);
                        }
                    }
                },
                Calendar.HOUR_OF_DAY,
                Calendar.MINUTE,
                true
        );
        timePickerDialog.show();
    }

    public boolean checkSelezioneOrario(String stringaOra){
        int x = stringaOra.indexOf(":");

        String s1 = stringaOra.substring(0,x);
        String s2 = stringaOra.substring(x+1);

        int oraSelezionata = Integer.parseInt(s1);
        int minutoSelezionato = Integer.parseInt(s2);

        if(selezionataDataOdierna) {
            int oraAttuale = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            oraAttuale++;
            int minutoAttuale = Calendar.getInstance().get(Calendar.MINUTE);

            Log.d(DEBUG,"Ora Attuale: " + oraAttuale + " : " + minutoAttuale
                    + "\nOra Scelta: " + oraSelezionata + " : " + minutoSelezionato);

            if(oraSelezionata > oraAttuale) {
                Toast.makeText(InserisciActivity.this,
                        "L'ora selezionata è antecedente all'ora attuale",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else if (minutoAttuale > minutoSelezionato){
                Toast.makeText(InserisciActivity.this,
                        "L'ora selezionata è antecedente all'ora attuale",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public void clickRipetizione(View view) {

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ripetizione");
        builder.setSingleChoiceItems(scelteRipetizione, 1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                ripetizione = scelteRipetizione[item];
                Log.d(DEBUG, "Scelta: " + scelteRipetizione[item]);
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvRipetizione.setText(ripetizione);
            }
        });

        builder.create().show();

    }

    public void clickImportanza(View view) {

        final String[] scelte = {
                "Alta importanza",
                "Bassa importanza"
        };

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Importanza");
        builder.setSingleChoiceItems(scelte, 1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0)
                    importanza = true;
                else
                    importanza = false;

            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (importanza)
                    tvImportanza.setText(scelte[0]);
                else
                    tvImportanza.setText(scelte[1]);
            }
        });

        builder.create().show();
    }

    public void clickSuoneria(View view) {
        final String[] scelte = {
                "Suoneria attivata",
                "Suoneria disattivata"
        };

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Suoneria");
        builder.setSingleChoiceItems(scelte, 1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0)
                    suoneria = true;
                else
                    suoneria = false;
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (suoneria)
                    tvSuoneria.setText(scelte[0]);
                else
                    tvSuoneria.setText(scelte[1]);
            }
        });

        builder.create().show();
    }

    public void clickAvviso(View view) {

    }

    public void clickInserisciAttivita(View view) {

    }
}
