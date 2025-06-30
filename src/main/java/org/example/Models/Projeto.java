package org.example.Models;

public class Projeto {
    private int id;
    private String nome;
    private String descricao;
    private int idFuncionario; // Chave estrangeira para o funcionário responsável

    // Construtor
    public Projeto(int id, String nome, String descricao, int idFuncionario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.idFuncionario = idFuncionario;
    }

    public Projeto() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }

    @Override
    public String toString() {
        return "Projeto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", idFuncionario=" + idFuncionario +
                '}';
    }
}