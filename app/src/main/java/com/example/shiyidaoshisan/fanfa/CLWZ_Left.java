package com.example.shiyidaoshisan.fanfa;

public class CLWZ_Left {
    private String Cp,Cs;
    private int Kf,Fk;

    public CLWZ_Left(String cp, String cs, int kf, int fk) {
        Cp = cp;
        Cs = cs;
        Kf = kf;
        Fk = fk;
    }

    public String getCp() {
        return Cp;
    }

    public void setCp(String cp) {
        Cp = cp;
    }

    public String getCs() {
        return Cs;
    }

    public void setCs(String cs) {
        Cs = cs;
    }

    public int getKf() {
        return Kf;
    }

    public void setKf(int kf) {
        Kf = kf;
    }

    public int getFk() {
        return Fk;
    }

    public void setFk(int fk) {
        Fk = fk;
    }
}
