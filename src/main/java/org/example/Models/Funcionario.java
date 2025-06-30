package org.example.Models;

public class Funcionario extends Pessoa {
    private String matricula;
    private String departamento;

    // Construtor
    public Funcionario(int id, String nome, String email, String matricula, String departamento) {
        super(id, nome, email); // Chama o construtor da classe pai (Pessoa)
        this.matricula = matricula;
        this.departamento = departamento;
    }

    public Funcionario() {
        super();
    }

    // Getters e Setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", matricula='" + matricula + '\'' +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}