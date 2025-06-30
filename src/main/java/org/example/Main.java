package org.example;

import org.example.DAO.FuncionarioDAO;
import org.example.DAO.PessoaDAO;
import org.example.DAO.ProjetoDAO;
import org.example.Models.Funcionario;
import org.example.Models.Pessoa;
import org.example.Models.Projeto;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final PessoaDAO pessoaDAO = new PessoaDAO();
    private static final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private static final ProjetoDAO projetoDAO = new ProjetoDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    gerenciarPessoas();
                    break;
                case 2:
                    gerenciarFuncionarios();
                    break;
                case 3:
                    gerenciarProjetos();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
            pressioneEnterParaContinuar();
        } while (opcao != 0);
        scanner.close();
    }

    // --- MENUS DE GERENCIAMENTO ---

    private static void gerenciarPessoas() {
        int opcao;
        do {
            exibirSubMenu("Pessoa");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: // Cadastrar
                    System.out.print("Digite o nome da pessoa: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o email da pessoa: ");
                    String email = scanner.nextLine();
                    Pessoa novaPessoa = new Pessoa(0, nome, email);
                    pessoaDAO.adicionarPessoa(novaPessoa);
                    break;
                case 2: // Listar
                    System.out.println("\n--- Lista de Pessoas ---");
                    pessoaDAO.listarPessoas().forEach(System.out::println);
                    break;
                case 3: // Atualizar
                    System.out.print("Digite o ID da pessoa a ser atualizada: ");
                    int idAtualizar = lerOpcao();
                    Pessoa pessoaExistente = pessoaDAO.buscarPessoaPorId(idAtualizar);
                    if (pessoaExistente != null) {
                        System.out.print("Digite o novo nome: ");
                        pessoaExistente.setNome(scanner.nextLine());
                        System.out.print("Digite o novo email: ");
                        pessoaExistente.setEmail(scanner.nextLine());
                        pessoaDAO.atualizarPessoa(pessoaExistente);
                    } else {
                        System.err.println("Pessoa não encontrada.");
                    }
                    break;
                case 4: // Excluir
                    System.out.print("Digite o ID da pessoa a ser excluída: ");
                    int idExcluir = lerOpcao();
                    pessoaDAO.excluirPessoa(idExcluir);
                    break;
                case 0:
                    System.out.println("Retornando ao menu principal...");
                    break;
                default:
                    System.err.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void gerenciarFuncionarios() {
        int opcao;
        do {
            exibirSubMenu("Funcionário");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: // Cadastrar
                    System.out.print("Digite o ID da Pessoa para promover a Funcionário: ");
                    int idPessoa = lerOpcao();
                    System.out.print("Digite a matrícula (ex: F001): ");
                    String matricula = scanner.nextLine();
                    System.out.print("Digite o departamento: ");
                    String departamento = scanner.nextLine();
                    Funcionario novoFunc = new Funcionario();
                    novoFunc.setId(idPessoa); // REGRA 1 (verificação ocorre no DAO)
                    novoFunc.setMatricula(matricula);
                    novoFunc.setDepartamento(departamento);
                    funcionarioDAO.adicionarFuncionario(novoFunc);
                    break;
                case 2: // Listar
                    System.out.println("\n--- Lista de Funcionários ---");
                    funcionarioDAO.listarFuncionarios().forEach(System.out::println);
                    break;
                case 3: // Atualizar
                    System.out.print("Digite o ID do funcionário a ser atualizado: ");
                    int idAtualizar = lerOpcao();
                    Funcionario funcExistente = funcionarioDAO.buscarFuncionarioPorId(idAtualizar);
                    if (funcExistente != null) {
                        System.out.print("Digite o novo nome: ");
                        funcExistente.setNome(scanner.nextLine());
                        System.out.print("Digite o novo email: ");
                        funcExistente.setEmail(scanner.nextLine());
                        System.out.print("Digite a nova matrícula: ");
                        funcExistente.setMatricula(scanner.nextLine());
                        System.out.print("Digite o novo departamento: ");
                        funcExistente.setDepartamento(scanner.nextLine());
                        funcionarioDAO.atualizarFuncionario(funcExistente);
                    } else {
                        System.err.println("Funcionário não encontrado.");
                    }
                    break;
                case 4: // Excluir
                    System.out.print("Digite o ID do funcionário a ser excluído: ");
                    int idExcluir = lerOpcao();
                    funcionarioDAO.excluirFuncionario(idExcluir); // REGRA 3 (verificação ocorre no DAO)
                    break;
                case 0:
                    System.out.println("Retornando ao menu principal...");
                    break;
                default:
                    System.err.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void gerenciarProjetos() {
        int opcao;
        do {
            exibirSubMenu("Projeto");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: // Cadastrar
                    System.out.print("Digite o nome do projeto: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite a descrição do projeto: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Digite o ID do funcionário responsável: ");
                    int idFunc = lerOpcao();
                    Projeto novoProjeto = new Projeto(0, nome, descricao, idFunc); // REGRA 2 (verificação no DAO)
                    projetoDAO.adicionarProjeto(novoProjeto);
                    break;
                case 2: // Listar
                    System.out.println("\n--- Lista de Projetos ---");
                    projetoDAO.listarProjetos().forEach(System.out::println);
                    break;
                case 3: // Atualizar
                    System.out.print("Digite o ID do projeto a ser atualizado: ");
                    int idAtualizar = lerOpcao();
                    Projeto projExistente = projetoDAO.buscarProjetoPorId(idAtualizar);
                    if (projExistente != null) {
                        System.out.print("Digite o novo nome: ");
                        projExistente.setNome(scanner.nextLine());
                        System.out.print("Digite a nova descrição: ");
                        projExistente.setDescricao(scanner.nextLine());
                        System.out.print("Digite o novo ID do funcionário responsável: ");
                        projExistente.setIdFuncionario(lerOpcao());
                        projetoDAO.atualizarProjeto(projExistente);
                    } else {
                        System.err.println("Projeto não encontrado.");
                    }
                    break;
                case 4: // Excluir
                    System.out.print("Digite o ID do projeto a ser excluído: ");
                    int idExcluir = lerOpcao();
                    projetoDAO.excluirProjeto(idExcluir);
                    break;
                case 0:
                    System.out.println("Retornando ao menu principal...");
                    break;
                default:
                    System.err.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // --- MÉTODOS AUXILIARES ---

    private static void exibirMenuPrincipal() {
        System.out.println("\n--- SISTEMA DE GESTÃO DA EMPRESA ---");
        System.out.println("1 - Gerenciar Pessoas");
        System.out.println("2 - Gerenciar Funcionários");
        System.out.println("3 - Gerenciar Projetos");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirSubMenu(String entidade) {
        System.out.println("\n--- Menu " + entidade + " ---");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar");
        System.out.println("3 - Atualizar");
        System.out.println("4 - Excluir");
        System.out.println("0 - Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha pendente
            return opcao;
        } catch (InputMismatchException e) {
            System.err.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer do scanner
            return -1; // Retorna um valor inválido para repetir o loop
        }
    }

    private static void pressioneEnterParaContinuar() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }
}