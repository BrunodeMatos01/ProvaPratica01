package org.example.DAO;

import org.example.Utils.Conexao; // IMPORT DA SUA CLASSE DE CONEXÃO
import org.example.Models.Pessoa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    /**
     * Adiciona uma nova pessoa ao banco de dados.
     * @param pessoa O objeto Pessoa a ser adicionado.
     */
    public void adicionarPessoa(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, email) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEmail());
            stmt.executeUpdate();
            System.out.println("✅ Pessoa cadastrada com sucesso!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao cadastrar pessoa: " + e.getMessage());
        }
    }

    /**
     * Lista todas as pessoas cadastradas.
     * @return Uma lista de objetos Pessoa.
     */
    public List<Pessoa> listarPessoas() {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM pessoa";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pessoa pessoa = new Pessoa(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email")
                );
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar pessoas: " + e.getMessage());
        }
        return pessoas;
    }

    /**
     * Busca uma pessoa pelo seu ID.
     * @param id O ID da pessoa a ser buscada.
     * @return O objeto Pessoa encontrado ou null se não existir.
     */
    public Pessoa buscarPessoaPorId(int id) {
        String sql = "SELECT * FROM pessoa WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Pessoa(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar pessoa por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Atualiza os dados de uma pessoa existente.
     * @param pessoa O objeto Pessoa com os dados atualizados.
     */
    public void atualizarPessoa(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome = ?, email = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEmail());
            stmt.setInt(3, pessoa.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Pessoa atualizada com sucesso!");
            } else {
                System.err.println("❌ Falha ao atualizar: Pessoa não encontrada.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar pessoa: " + e.getMessage());
        }
    }

    /**
     * Exclui uma pessoa do banco de dados.
     * ATENÇÃO: A lógica de negócio para impedir exclusão de pessoa que é funcionário
     * deve ser verificada antes de chamar este método (na classe FuncionarioDAO).
     * @param id O ID da pessoa a ser excluída.
     */
    public void excluirPessoa(int id) {
        // Regra de negócio: Um funcionário não pode ser excluído se estiver em um projeto.
        // A exclusão da pessoa associada a um funcionário deve ser tratada no FuncionarioDAO.
        String sql = "DELETE FROM pessoa WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Pessoa excluída com sucesso!");
            } else {
                System.err.println("❌ Falha ao excluir: Pessoa não encontrada.");
            }
        } catch (SQLException e) {
            // Trata erro de chave estrangeira se a pessoa for um funcionário
            if (e.getErrorCode() == 1451) { // Código de erro do MySQL para Foreign Key constraint
                System.err.println("❌ Erro ao excluir: Esta pessoa está registrada como um funcionário. Remova o registro de funcionário primeiro.");
            } else {
                System.err.println("❌ Erro ao excluir pessoa: " + e.getMessage());
            }
        }
    }
}