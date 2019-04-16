package com.example.firehunter;

public class Einsatz {
    String art;
    String ort;
    String adresse;
    int alarmStufe;
    int anzahl;
    String time;

    public Einsatz(String art, String ort, String adresse, int alarmStufe, int anzahl, String time) {
        this.art = art;
        this.ort = ort;
        this.adresse = adresse;
        this.alarmStufe = alarmStufe;
        this.anzahl = anzahl;
        this.time = time;
    }

    public String getArt() {
        return art;
    }

    public String getOrt() {
        return ort;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getAlarmStufe() {
        return alarmStufe;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public String getTime() {
        return time;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setAlarmStufe(int alarmStufe) {
        this.alarmStufe = alarmStufe;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public void setTime(String time) {
        this.time = time;
    }

    //einsatzart, ort, adresse, alarmstufe, anzahl der feuerwehren, startzeit
}
