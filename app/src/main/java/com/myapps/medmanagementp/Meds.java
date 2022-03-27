package com.myapps.medmanagementp;

public class Meds {
    private String naMe;
    private String doSe;
    private String instructions;
    private String providEr;

    public Meds(String naMe, String doSe, String instructions, String providEr) {
        this.naMe = naMe;
        this.doSe = doSe;
        this.instructions = instructions;
        this.providEr = providEr;
    }

    public String getNaMe() {
        return naMe;
    }

    public void setNaMe(String naMe) {
        this.naMe = naMe;
    }

    public String getDoSe() {
        return doSe;
    }

    public void setDoSe(String doSe) {
        this.doSe = doSe;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getProvidEr() {
        return providEr;
    }

    public void setProvidEr(String providEr) {
        this.providEr = providEr;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Meds) {
            Meds meds = (Meds) obj;
            if(naMe.equals(meds.naMe) &&
                    doSe.equals(meds.doSe) &&
                    instructions.equals(meds.instructions) &&
                    providEr.equals(meds.providEr))
                return true;}
        return false;}

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return (this.naMe.hashCode() + this.doSe.hashCode() + this.instructions.hashCode() + this.providEr.hashCode());
    }
}