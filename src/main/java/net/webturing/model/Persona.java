package net.webturing.model;

import java.util.Objects;

public class Persona {
    private Long id;

    private String nome;
    private String cognome;
    private String indirizzo;
    private String telefono;
    private int eta;

    public Persona(String nome, String cognome, String indirizzo, String telefono, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = eta;
    }
    //caricamento/lettura da db
    public Persona(long id, String nome, String cognome, String indirizzo, String telefono, int eta) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = eta;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getEta() {
        return eta;
    }
    public void setId(long id) { this.id = id; }
    public long getId() { return id; }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona person = (Persona) o;
        return eta == person.eta && Objects.equals(nome, person.nome) && Objects.equals(cognome, person.cognome) && Objects.equals(indirizzo, person.indirizzo) && Objects.equals(telefono, person.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cognome, indirizzo, telefono, eta);
    }

    @Override
    public String toString() {
        return "Person{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", eta=" + eta +
                '}';
    }

    // Parsing da riga di file
    public static Persona fromString(String riga) {
        String[] parts = riga.split(";");
        if (parts.length != 5) return null;
        try {
            String nome = parts[0];
            String cognome = parts[1];
            String indirizzo = parts[2];
            String telefono = parts[3];
            int eta = Integer.parseInt(parts[4]);
            return new Persona(nome, cognome, indirizzo, telefono, eta);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Formattazione per salvataggio
    public String toFileString() {
        return String.format("%s;%s;%s;%s;%d", nome, cognome, indirizzo, telefono, eta);
    }
}
