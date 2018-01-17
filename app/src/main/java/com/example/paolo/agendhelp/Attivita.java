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
    Boolean suoneria;
    public Attivita (String nome, String data, String ora, Boolean importante,String ripetizione,Boolean suoneria){
    this.nome=nome;
    this.data=data;
    this.ora=ora;
    this.importante=importante;
    this.ripetizione=ripetizione;
    this.suoneria=suoneria;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getOra() {
        return ora;
    }

    public void setAlta(Boolean alta) {
        this.importante=importante;
    }

    public Boolean getAlta() {
        return importante;
    }

    public void setRipetizione(String ripetizione) {
        this.ripetizione = ripetizione;
    }

    public String getRipetizione() {
        return ripetizione;
    }

    public void setSuoneria(Boolean suoneria) {
        this.suoneria = suoneria;
    }

    public Boolean getSuoneria() {
        return suoneria;
    }
}
