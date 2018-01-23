package com.example.paolo.agendhelp;

import java.io.Serializable;



/**
 *
 * Created by Paolo on 17/01/2018.
 */

public class Attivita implements Serializable {
    //prova di creazione attività
    private String nome;
    private String data;
    private String ora;
    private Boolean importante;
    private String ripetizione;
    private Boolean suoneria;

    public Attivita (String nome, String data, String ora, Boolean importante,String ripetizione,Boolean suoneria){
    this.nome=nome;
    this.data=data;
    this.ora=ora;
    this.importante=importante;
    this.ripetizione=ripetizione;
    this.suoneria=suoneria;
    }

    public String getNome() {
        return nome;
    }

    public String getData() {
        return data;
    }

    public String getOra() {
        return ora;
    }

    public Boolean getImportante() {
        return importante;
    }

    public String getRipetizione() {
        return ripetizione;
    }

    public Boolean getSuoneria() {
        return suoneria;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  Attivita){
            Attivita a = (Attivita) obj;
            return a.getImportante().equals(importante) &&
                    a.getSuoneria().equals(suoneria) &&
                    a.getData().equals(data) &&
                    a.getNome().equals(nome) &&
                    a.getOra().equals(ora) &&
                    a.getRipetizione().equals(ripetizione);
        }
            return false;


    }

    @Override
    public String toString() {
        return "Attività = "+ " NOME = " + nome + " DATA = " + data +
                " ORA = " + ora + " IMPORTANZA =" + importante + " RIPETIZIONE = " +
                ripetizione + " SUONERIA = " + suoneria;
    }
}
