package org.example.DAO;

import org.example.Utils.Conexao;
import org.example.Models.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    /**
     * Adiciona um novo funcionário.
     * REGRA 1: Verifica se o ID da pessoa já existe.
     * @param funcionario O objeto Funcionario a ser adicionado.
     */
    public void adicionarFuncionario(Funcionario funcionario) {
        // REGRA 1: Ao cadastrar um funcionário, verificar se o ID da pessoa existe.
        PessoaDAO pessoaDAO = new PessoaDAO();
        if (pessoaDAO.buscarPessoaPorId(funcionario.getId()) == null) {
            System.err.println("❌ Falha ao cadastrar: O ID de pessoa informado não existe.");
            return;
        }

        String sql = "INSERT INTO funcionario (id_pessoa, matricula, departamento) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, funcionario.getId());
            stmt.setString(2, funcionario.getMatricula());
            stmt.setString(3, funcionario.getDepartamento());
            stmt.executeUpdate();
            System.out.println("✅ Funcionário cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    /**
     * Lista todos os funcionários com seus dados de pessoa.
     * @return Uma lista de objetos Funcionario.
     */
    public List<Funcionario> listarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        // JOIN para buscar dados da tabela pessoa e funcionario
        String sql = "SELECT p.id, p.nome, p.email, f.matricula, f.departamento " +
                "FROM pessoa p JOIN funcionario f ON p.id = f.id_pessoa";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario func = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("matricula"),
                        rs.getString("departamento")
                );
                funcionarios.add(func);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar funcionários: " + e.getMessage());
        }
        return funcionarios;
    }

    /**
     * Busca um funcionário pelo seu ID (que é o mesmo ID da pessoa).
     * @param id O ID do funcionário.
     * @return O objeto Funcionario ou null se não for encontrado.
     */
    public Funcionario buscarFuncionarioPorId(int id) {
        String sql = "SELECT p.id, p.nome, p.email, f.matricula, f.departamento " +
                "FROM pessoa p JOIN funcionario f ON p.id = f.id_pessoa WHERE p.id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("matricula"),
                            rs.getString("departamento")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar funcionário: " + e.getMessage());
        }
        return null;
    }


    /**
     * Atualiza os dados de um funcionário.
     * @param funcionario O objeto com os dados atualizados.
     */
    public void atualizarFuncionario(Funcionario funcionario) {
        // Primeiro, atualiza os dados na tabela pessoa
        PessoaDAO pessoaDAO = new PessoaDAO();
        pessoaDAO.atualizarPessoa(funcionario); // Reutiliza o método de PessoaDAO

        // Segundo, atualiza os dados na tabela funcionario
        String sql = "UPDATE funcionario SET matricula = ?, departamento = ? WHERE id_pessoa = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getMatricula());
            stmt.setString(2, funcionario.getDepartamento());
            stmt.setInt(3, funcionario.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Dados específicos do funcionário atualizados com sucesso!");
            } else {
                System.err.println("❌ Falha ao atualizar: Funcionário não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar dados do funcionário: " + e.getMessage());
        }
    }

    /**
     * Exclui um funcionário.
     * REGRA 3: Proíbe a exclusão se o funcionário estiver vinculado a um projeto.
     * @param id O ID do funcionário a ser excluído.
     */
    public void excluirFuncionario(int id) {
        // REGRA 3: Verificar se o funcionário está vinculado a algum projeto.
        ProjetoDAO projetoDAO = new ProjetoDAO();
        if (projetoDAO.funcionarioTemProjetos(id)) {
            System.err.println("❌ Falha ao excluir: O funcionário está vinculado a um ou mais projetos.");
            return;
        }

        // Se não tiver projetos, exclui primeiro da tabela funcionario
        String sqlFunc = "DELETE FROM funcionario WHERE id_pessoa = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlFunc)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Registro de funcionário removido com sucesso!");
                // Opcional: decidir se quer remover o registro da tabela 'pessoa' também.
                // A boa prática é manter o registro de pessoa, removendo apenas o "papel" de funcionário.
                // new PessoaDAO().excluirPessoa(id); // Descomente se desejar excluir a pessoa também.
            } else {
                System.err.println("❌ Falha ao excluir: Funcionário não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir funcionário: " + e.getMessage());
        }
    }
}