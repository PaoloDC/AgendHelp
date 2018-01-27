package com.example.paolo.agendhelp;

import android.app.Activity;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Paolo2 on 17/01/2018.
 */

public class InserisciActivity extends AppCompatActivity {

    ArrayList<Attivita> listaAttivita = new ArrayList<>();
    //costanti
    public static final String DEBUG = "DEBUG";
    final String[] scelteRipetizione = {
            "Nessuna ripetizione",
     /*       "Una volta al giorno",
            "Due volte al giorno",
            "Una volta la settimana",
            "Una volta al mese"
 */   };
    final String[] scelteAvviso = {
            "All'ora esatta",
  /*          "10 minuti prima",
            "5 minuti prima",
            "5 minuti dopo",
            "10 minuti dopo"
   */ };

    final String testo_esempio = "---";


    //riferimenti al layout
   // private TextView tvAvviso;
    private TextView tvData;
    private TextView tvImportanza;
    private TextView tvOra;
    private TextView tvRipetizione;
    private TextView tvSuoneria;
    private EditText etNomeAttivita;

    private String ripetizione;
    private boolean importanza;
    private boolean suoneria;
    //private String avviso;
    private boolean selezionataDataOdierna;


    String stringaData;
    String stringaOra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserisci_layout);

        Intent i = getIntent();
        listaAttivita = (ArrayList<Attivita>) i.getSerializableExtra("LISTAATTIVITA");

        Log.e("ERROR"," Attivita B  = " + listaAttivita.size());

       // tvAvviso = findViewById(R.id.tvAvviso);
        tvData = findViewById(R.id.tvData);
        tvImportanza = findViewById(R.id.tvImportanza);
        tvOra = findViewById(R.id.tvOra);
        tvRipetizione = findViewById(R.id.tvRipetizione);
        tvSuoneria = findViewById(R.id.tvSuoneria);
        etNomeAttivita = findViewById(R.id.etNomeAttivita);

        suoneria = false;
        importanza = false;
        ripetizione = scelteRipetizione[0];
      //  avviso = scelteAvviso[0];

        tvRipetizione.setText(scelteRipetizione[0]);
        tvImportanza.setText("Bassa importanza");
        tvSuoneria.setText("Suoneria disattivata");
       // tvAvviso.setText(scelteAvviso[0]);
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
                        stringaData = anno +"/" + mese + "/"+giorno ;

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ITALY);
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

                         stringaOra = oraSelezionata + ":" + minutoSelezionato;
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
           // oraAttuale++;
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
                "Bassa importanza",
                "Alta importanza"
        };

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Importanza");
        builder.setSingleChoiceItems(scelte, 1, new DialogInterface.OnClickListener() {
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
                    tvImportanza.setText(scelte[1]);
                else
                    tvImportanza.setText(scelte[0]);
            }
        });

        builder.create().show();
    }

    public void clickSuoneria(View view) {
        final String[] scelte = {
                "Suoneria disattivata",
            //    "Suoneria attivata"
        };

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Suoneria");
        builder.setSingleChoiceItems(scelte, 1, new DialogInterface.OnClickListener() {
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
                    tvSuoneria.setText(scelte[1]);
                else
                    tvSuoneria.setText(scelte[0]);
            }
        });

        builder.create().show();
    }
/*
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
*/
    public void clickInserisciAttivita(View view) {

        /*Controlli inserimenti*/
        final String nome = etNomeAttivita.getText().toString();
        if(nome.equals("")){
            Toast.makeText(this, "Inserisci un nome per l'attività", Toast.LENGTH_SHORT).show();
            return;
        } else if (tvData.getText().toString().equals(testo_esempio)){
            Toast.makeText(this, "Inserisci una data per l'attività", Toast.LENGTH_SHORT).show();
            return;
        } else if (tvOra.getText().toString().equals(testo_esempio)){
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
 /*Cotrollo se atività inserita già esiste*/
        boolean trovatoUguale = false;
        for(int k = 0; k<listaAttivita.size();k++){
            Attivita a = listaAttivita.get(k);
            Attivita b = new Attivita(nome,tvData.getText().toString(),tvOra.getText().toString(),importanza,ripetizione,suoneria);
            if(a.equals(b)){
                Toast.makeText(this, "Evento "+ listaAttivita.get(k).getNome()+ " già presente", Toast.LENGTH_SHORT).show();
                trovatoUguale=true;
            }
        }
        if(trovatoUguale==false){
            String messaggio = "Nome:\t" + nome
                    + "\nData: " + tvData.getText().toString()
                    + "\nOra: " + tvOra.getText().toString()
                    + "\nRipetizione: " + ripetizione
                    + "\nImportanza: " + stringaImportanza
                    + "\nSuoneria: " + stringaSuoneria;
            //              + "\nAvviso: " + avviso;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Riepilogo Inserimento");
            builder.setMessage(messaggio);
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    attivaAllarme(nome);
                    getIntent().putExtra("ATTIVITA",
                            new Attivita(nome,tvData.getText().toString(),tvOra.getText().toString(),importanza,ripetizione,suoneria));
                    setResult(Activity.RESULT_OK,getIntent());
                    finish();
                }
            }) ;

            builder.setNegativeButton("No",null);
            builder.create().show();
        }

    }

    private void attivaAllarme(String messaggio){
        /*Funziona*/
        Intent i = new Intent(getApplicationContext(),Allarme.class);
        i.putExtra(Allarme.MESSAGGIO,messaggio);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        /*intent in attesa*/
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,i,0);
        AlarmManager am = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime()+getMilliSecondi(),pi);
        Log.d(DEBUG,"msg: millisecondiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii "+ getMilliSecondi());


    }
    public long getMilliSecondi(){
        long millisDiff = 0;

                try {
                    Calendar c = Calendar.getInstance();
                  //  System.out.println(c.getTime());	/* Rappresentazione come stringa in base al tuo Locale */
                 //   System.out.println(c.get(Calendar.YEAR)); /* Ottieni l'anno */
                  //  System.out.println("mese"+c.get((Calendar.MONTH))); /* Ottieni il mese */
                 //   System.out.println(c.get(Calendar.DAY_OF_MONTH)); /* Ottieni il giorno */
                 //   System.out.println(c.get(Calendar.HOUR_OF_DAY));
                 //   System.out.println(c.get(Calendar.MINUTE));

                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                    int anno = c.get(Calendar.YEAR);
                    int mese = c.get(Calendar.MONTH)+1;
                    int giorno = c.get(Calendar.DAY_OF_MONTH);
                    int ora= c.get(Calendar.HOUR_OF_DAY)+1;
                    int minuti = c.get(Calendar.MINUTE);
                    int secondi =c.get(Calendar.SECOND) ;


                    String strDate1 = ""+anno+"/"+mese+"/"+giorno+" "+ora+":"+minuti+":"+secondi;
                    Log.d(DEBUG,"msg: Stringaaaa dataaaaaaa111111 "+ strDate1);

                    String strDate2 = stringaData+" "+stringaOra +":"+0+0;
                    Log.d(DEBUG,"msg: Stringaaaa dataaaaaaa2222 "+ strDate2);





                    fmt.setLenient(false);

// Parses the two strings.

                    Date d1 = fmt.parse(strDate1);

                    Date d2 = fmt.parse(strDate2);

// Calculates the difference in milliseconds.

                     millisDiff = d2.getTime() - d1.getTime();
                    Log.d(DEBUG,"msg: Stringaaaa dataaaaaaa "+ strDate2);

// Calculates days/hours/minutes/seconds.

                    int seconds = (int) (millisDiff / 1000 % 60);

                    int minutes = (int) (millisDiff / 60000 % 60);

                    int hours = (int) (millisDiff / 3600000 % 24);

                    int days = (int) (millisDiff / 86400000);



              /*      System.out.println("Between " + strDate1 + " and " + strDate2 + " there are:");

                    System.out.print(days + " days, ");

                    System.out.print(hours + " hours, ");

                    System.out.print(minutes + " minutes, ");

                    System.out.println(seconds + " seconds");*/

                } catch (Exception e) {

                    System.err.println(e);

                }
            return millisDiff;
    }
}
