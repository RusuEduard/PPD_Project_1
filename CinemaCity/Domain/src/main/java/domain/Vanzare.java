package domain;

import java.util.Date;
import java.util.List;

public class Vanzare extends Entity<Long> {

    private Date dataVanzare;
    private int nrBileteVandute;
    private List<Integer> locuriVandute;
    private float suma;

    public Date getDataVanzare() {
        return dataVanzare;
    }

    public void setDataVanzare(Date dataVanzare) {
        this.dataVanzare = dataVanzare;
    }

    public int getNrBileteVandute() {
        return nrBileteVandute;
    }

    public void setNrBileteVandute(int nrBileteVandute) {
        this.nrBileteVandute = nrBileteVandute;
    }

    public List<Integer> getLocuriVandute() {
        return locuriVandute;
    }

    public void setLocuriVandute(List<Integer> locuriVandute) {
        this.locuriVandute = locuriVandute;
    }

    public float getSuma() {
        return suma;
    }

    public void setSuma(float suma) {
        this.suma = suma;
    }
}
