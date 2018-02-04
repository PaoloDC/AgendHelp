package com.example.paolo.agendhelp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Paolo on 23/01/2018.
 */

public class GestoreFile {

    public static final String FILENAME = "utente.dat";

    public static void salvaImpostazioniUtente
            (Context context, String nome, String cognome, String telefono, String password, String loggato) {

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            loggato += ",";
            nome += ",";
            cognome += ",";
            telefono += ",";

            fos.write(loggato.getBytes());
            fos.write(nome.getBytes());
            fos.write(cognome.getBytes());
            fos.write(telefono.getBytes());
            fos.write(password.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean login(Context context, String tel, String pass) {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            String data = "";
            while (null != (line = br.readLine())) {
                data += line;
            }
            br.close();
            int x = data.indexOf(",");
            String[] dati = data.split(",");

            System.out.println("DATA VALE: " + data);

            String nome = dati[1];
            String cognome = dati[2];
            String telefono = dati[3];
            String password = dati[4];

            if (tel.equals(telefono) && pass.equals(password)) {

                FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

                String loggato = "si,";
                nome += ",";
                cognome += ",";
                telefono += ",";

                fos.write(loggato.getBytes());
                fos.write(nome.getBytes());
                fos.write(cognome.getBytes());
                fos.write(telefono.getBytes());
                fos.write(password.getBytes());
                fos.close();
            }

            MainActivity.nomeAccount = nome + " " + cognome;
            System.out.println("nome account: " + MainActivity.nomeAccount);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void logout(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            String data = "";
            while (null != (line = br.readLine())) {
                data += line;
            }
            br.close();
            int x = data.indexOf(",");
            String[] dati = data.split(",");

            System.out.println("DATA VALE: " + data);

            String nome = dati[1];
            String cognome = dati[2];
            String telefono = dati[3];
            String password = dati[4];

            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            String loggato = "no,";
            nome += ",";
            cognome += ",";
            telefono += ",";

            fos.write(loggato.getBytes());
            fos.write(nome.getBytes());
            fos.write(cognome.getBytes());
            fos.write(telefono.getBytes());
            fos.write(password.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkAccountEsistente(Context context) {
        File parentDirectory = context.getFilesDir();

        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDirectory.listFiles();

        if (files.length != 0) {
            for (File file : files) {
                if (file.getName().equals(GestoreFile.FILENAME)) {
                    try {
                        FileInputStream fis = context.openFileInput(FILENAME);
                        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                        String line;
                        String data = "";
                        while (null != (line = br.readLine())) {
                            data += line;
                        }
                        br.close();
                        int x = data.indexOf(",");
                        String[] dati = data.split(",");
                        String loggato = dati[0];

                        if (loggato.equals("si")) {
                            String nome = dati[1];
                            String cognome = dati[2];
                            MainActivity.nomeAccount = nome + " " + cognome;
                            return true;
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            }
        }
        return false;
    }
}
