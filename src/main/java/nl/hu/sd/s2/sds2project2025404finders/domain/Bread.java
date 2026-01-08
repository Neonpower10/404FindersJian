// src/main/java/nl/hu/sd/s2/sds2project2025404finders/domain/Bread.java
package nl.hu.sd.s2.sds2project2025404finders.domain;

public class Bread {
    // Note: The fields remain the same as defined in the frontend
    private int id;
    private String naam;
    private double prijs;
    private String beschrijving;
    private String foto;

    /** Default constructor required for JAX-RS/Jackson JSON deserialization. */
    public Bread() {
    }

    public Bread(int id, String naam, double prijs, String beschrijving, String foto) {
        this.id = id;
        this.naam = naam;
        this.prijs = prijs;
        this.beschrijving = beschrijving;
        this.foto = foto;
    }

    // --- Getters and Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNaam() { return naam; }
    public void setNaam(String naam) { this.naam = naam; }
    public double getPrijs() { return prijs; }
    public void setPrijs(double prijs) { this.prijs = prijs; }
    public String getBeschrijving() { return beschrijving; }
    public void setBeschrijving(String beschrijving) { this.beschrijving = beschrijving; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}