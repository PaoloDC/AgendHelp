package com.example.paolo.agendhelp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Paolo2 on 23/01/2018.
 */

public class GestoreFile {

    public static final String FILENAME = "utente.dat";

    public static void salvaImpostazioniUtente(String nome, String cognome, String telefono, String password) {

            try {
                File file = new File(FILENAME);
                if (file.exists()) {
                    file.delete();
                }
            file.createNewFile();

            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(nome);
            out.writeObject(cognome);
            out.writeObject(telefono);
            out.writeObject(password);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean login(String tel,String pass){
        try {
            File file = new File(FILENAME);
            if (file.exists()) {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));

                String nome = (String) in.readObject();
                String cognome = (String) in.readObject();
                String telefono  = (String) in.readObject();
                String password = (String) in.readObject();

                System.out.println("INSERITE: tel: "+ tel + ", pass: " + pass);
                System.out.println("nome: " +nome + ", cognome: " +cognome
                        + ", telefono: " +telefono + ", password: " +password);

                if(tel.equals(telefono) && pass.equals(password)) {
                    MainActivity.nomeAccount = nome + " " + cognome;
                    return true;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void salvaImpostazioniUtente(Context context, String nome, String cognome, String telefono, String password) {

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(nome.getBytes());
            fos.write(cognome.getBytes());
            fos.write(telefono.getBytes());
            fos.write(password.getBytes());
            fos.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean login(Context context,String tel,String pass){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(FILENAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            String data = "";
            while (null != (line = br.readLine())) {
                data += line+"\n";
            }
            br.close();
            System.out.println("DATI: " + data);
            return true;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
