# 💼 Sistema de Gerenciamento de Funcionários e Projetos

Este projeto Java implementa um sistema simples de gerenciamento de pessoas, funcionários e projetos em uma empresa. Utiliza o **paradigma de Programação Orientada a Objetos (POO)** e realiza persistência de dados com **JDBC** e um banco de dados relacional (**MySQL**). É ideal para fins acadêmicos ou como base para sistemas maiores.

---

## 🧩 Funcionalidades

- 👤 Cadastro, edição, listagem e exclusão de **Pessoas**
- 💼 Registro e controle de **Funcionários** (herdam de Pessoa)
- 📁 Gestão de **Projetos**
- 🔗 Associação entre Funcionários e Projetos
- 📦 Operações completas de **CRUD**
- 🗃️ Separação em camadas com uso de DAOs para acesso a dados

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Descrição |
|-----------|-----------|
| ☕ **Java** | Linguagem principal do projeto |
| 🔌 **JDBC** | API de conectividade com banco de dados |
| 🐬 **MySQL** | Banco de dados relacional utilizado |
| 🧠 **POO** | Programação orientada a objetos |
| 🧰 **IntelliJ IDEA** | IDE recomendada para desenvolvimento |
| 🗃️ **DAO Pattern** | Organização de acesso a dados |

---

## 🖥️ Requisitos

- ✅ Java JDK 17 ou superior
- ✅ MySQL Server (ou outro SGBD relacional com suporte a JDBC)
- ✅ IntelliJ IDEA, Eclipse ou VS Code com extensão Java
- ✅ Driver JDBC compatível (como `mysql-connector-java`)

---

## 📁 Estrutura do Projeto

├── Main.java # Ponto de entrada da aplicação
├── Conexao.java # Classe responsável pela conexão JDBC
├── Pessoa.java # Superclasse abstrata para pessoas
├── Funcionario.java # Subclasse de Pessoa com dados específicos
├── Projeto.java # Classe de domínio para Projetos
├── PessoaDAO.java # Operações CRUD com Pessoa
├── FuncionarioDAO.java # Operações CRUD com Funcionario
├── ProjetoDAO.java # Operações CRUD com Projeto


---

## 🏁 Como Executar

### 1. 🔽 Clone o repositório:
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
CONFIGURE SEU BANCO DE DADOS
private static final String URL = "jdbc:mysql://localhost:3306/empresa";
private static final String USUARIO = "root";
private static final String SENHA = "";
