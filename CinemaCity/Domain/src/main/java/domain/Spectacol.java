package domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Spectacol extends Entity<Long> {

    private LocalDateTime data;
    private String titlu;
    private float pretBilet;
    private List<Vanzare> vanzari;
    private float sold;

    public Spectacol() {
    }

    public Spectacol(LocalDateTime data, String titlu, float pretBilet) {
        this.data = data;
        this.titlu = titlu;
        this.pretBilet = pretBilet;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public float getPretBilet() {
        return pretBilet;
    }

    public void setPretBilet(float pretBilet) {
        this.pretBilet = pretBilet;
    }

    public List<Vanzare> getVanzari() {
        return vanzari;
    }

    public void setVanzari(List<Vanzare> vanzari) {
        this.vanzari = vanzari;
    }

    public float getSold() {
        return sold;
    }

    public void setSold(float sold) {
        this.sold = sold;
    }
}
