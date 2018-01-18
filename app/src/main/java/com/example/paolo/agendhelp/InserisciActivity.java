package com.example.paolo.agendhelp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
            "Nessuna ripetizione",
            "Una volta al giorno",
            "Due volte al giorno",
            "Una volta la settimana",
            "Una volta al mese"
    };
    final String[] scelteAvviso = {
            "All'ora esatta",
            "10 minuti prima",
            "5 minuti prima",
            "5 minuti dopo",
            "10 minuti dopo"
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

        suoneria = false;
        importanza = false;
        ripetizione = scelteRipetizione[0];
        avviso = scelteAvviso[0];

        tvRipetizione.setText(scelteRipetizione[0]);
        tvImportanza.setText("Bassa importanza");
        tvSuoneria.setText("Suoneria disattivata");
        tvAvviso.setText(scelteAvviso[0]);
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

            if(oraAttuale > oraSelezionata) {
                Toast.makeText(InserisciActivity.this,
                        "L'ora selezionata è antecedente all'ora attuale",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else if (oraAttuale > oraSelezionata && minutoAttuale > minutoSelezionato){
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
        builder.setSingleChoiceItems(scelteRipetizione, 0, new DialogInterface.OnClickListener() {
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
        builder.setSingleChoiceItems(scelte, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0)
                    importanza = false;
                else
                    importanza = true;

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
        builder.setSingleChoiceItems(scelte, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0)
                    suoneria = false;
                else
                    suoneria = true;
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


        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Avviso");
        builder.setSingleChoiceItems(scelteAvviso, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                avviso = scelteAvviso[item];
                Log.d(DEBUG,"Avviso: " + scelteAvviso[item]);
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvAvviso.setText(avviso);
            }
        });

        builder.create().show();
    }

    public void clickInserisciAttivita(View view) {
        final String nome = etNomeAttivita.getText().toString();
        if(nome.equals("")){
            Toast.makeText(this, "Inserisci un nome per l'attività", Toast.LENGTH_SHORT).show();
            return;
        } else if (tvData.equals(testo_esempio)){
            Toast.makeText(this, "Inserisci una data per l'attività", Toast.LENGTH_SHORT).show();
            return;
        } else if (tvData.equals(testo_esempio)){
            Toast.makeText(this, "Inserisci un'ora per l'attività", Toast.LENGTH_SHORT).show();
            return;
        }

        String stringaImportanza = "Bassa";
        if(importanza) {
            stringaImportanza = "Alta";
        }
        String stringaSuoneria = "Disattivata";
        if(suoneria){
            stringaSuoneria = "Attivata";
        }

        String messaggio = "Nome:\t" + nome
                + "\nData: " + tvData.getText().toString()
                + "\nOra: " + tvOra.getText().toString()
                + "\nRipetizione: " + ripetizione
                + "\nImportanza: " + stringaImportanza
                + "\nSuoneria: " + stringaSuoneria
                + "\nAvviso: " + avviso;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Riepilogo Inserimento");
        builder.setMessage(messaggio);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                attivaAllarme(nome);
                finish();
            }
        }) ;

        builder.setNegativeButton("No",null);
        builder.create().show();
    }

    private void attivaAllarme(String messaggio){

        Intent intent = new Intent(this,Allarme.class);
        intent.putExtra(Allarme.MESSAGGIO,messaggio);
        Log.d(DEBUG,"msg: "+ messaggio);

        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+2000,pi);

    }
}
