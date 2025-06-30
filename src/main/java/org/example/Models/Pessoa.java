package org.example.Models;

public class Pessoa {
    private int id;
    private String nome;
    private String email;

    // Construtor vazio é útil para criar objetos a partir do banco
    public Pessoa() {}

    public Pessoa(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Pessoa{" + "id=" + id + ", nome='" + nome + '\'' + ", email='" + email + '\'' + '}';
    }
}