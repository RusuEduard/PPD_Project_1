package domain;

import java.time.LocalDateTime;
import java.util.List;

public class Vanzare extends Entity<Long> {

    private LocalDateTime dataVanzare;
    private int nrBileteVandute;
    private List<Integer> locuriVandute;
    private float suma;

    public LocalDateTime getDataVanzare() {
        return dataVanzare;
    }

    public void setDataVanzare(LocalDateTime dataVanzare) {
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
