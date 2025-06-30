package org.example.DAO;

import org.example.Utils.Conexao; // IMPORT DA SUA CLASSE DE CONEXÃO
import org.example.Models.Projeto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    /**
     * Adiciona um novo projeto.
     * REGRA 2: Verifica se o funcionário responsável existe.
     * @param projeto O objeto Projeto a ser adicionado.
     */
    public void adicionarProjeto(Projeto projeto) {
        // REGRA 2: Um projeto não pode ser criado sem vínculo com um funcionário existente.
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        if (funcionarioDAO.buscarFuncionarioPorId(projeto.getIdFuncionario()) == null) {
            System.err.println("❌ Falha ao criar projeto: O funcionário responsável não foi encontrado.");
            return;
        }

        String sql = "INSERT INTO projeto (nome, descricao, id_funcionario) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setInt(3, projeto.getIdFuncionario());
            stmt.executeUpdate();
            System.out.println("✅ Projeto cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao cadastrar projeto: " + e.getMessage());
        }
    }

    /**
     * Lista todos os projetos.
     * @return Uma lista de objetos Projeto.
     */
    public List<Projeto> listarProjetos() {
        List<Projeto> projetos = new ArrayList<>();
        String sql = "SELECT * FROM projeto";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Projeto projeto = new Projeto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("id_funcionario")
                );
                projetos.add(projeto);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar projetos: " + e.getMessage());
        }
        return projetos;
    }

    /**
     * Busca um projeto pelo seu ID.
     * @param id O ID do projeto a ser buscado.
     * @return O objeto Projeto encontrado ou null se não existir.
     */
    public Projeto buscarProjetoPorId(int id) {
        String sql = "SELECT * FROM projeto WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Projeto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getInt("id_funcionario")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar projeto por ID: " + e.getMessage());
        }
        return null;
    }


    /**
     * Atualiza os dados de um projeto.
     * @param projeto O objeto Projeto com os dados atualizados.
     */
    public void atualizarProjeto(Projeto projeto) {
        // Verifica se o novo funcionário responsável existe antes de atualizar
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        if (funcionarioDAO.buscarFuncionarioPorId(projeto.getIdFuncionario()) == null) {
            System.err.println("❌ Falha ao atualizar projeto: O novo funcionário responsável não foi encontrado.");
            return;
        }

        String sql = "UPDATE projeto SET nome = ?, descricao = ?, id_funcionario = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setInt(3, projeto.getIdFuncionario());
            stmt.setInt(4, projeto.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Projeto atualizado com sucesso!");
            } else {
                System.err.println("❌ Falha ao atualizar: Projeto não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar projeto: " + e.getMessage());
        }
    }

    /**
     * Exclui um projeto do banco de dados.
     * @param id O ID do projeto a ser excluído.
     */
    public void excluirProjeto(int id) {
        String sql = "DELETE FROM projeto WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Projeto excluído com sucesso!");
            } else {
                System.err.println("❌ Falha ao excluir: Projeto não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir projeto: " + e.getMessage());
        }
    }

    /**
     * Verifica se um funcionário tem projetos associados.
     * Usado para a REGRA 3 na exclusão de funcionários.
     * @param idFuncionario O ID do funcionário.
     * @return true se o funcionário tiver pelo menos um projeto, false caso contrário.
     */
    public boolean funcionarioTemProjetos(int idFuncionario) {
        String sql = "SELECT COUNT(*) FROM projeto WHERE id_funcionario = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFuncionario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao verificar projetos do funcionário: " + e.getMessage());
        }
        return false;
    }
}