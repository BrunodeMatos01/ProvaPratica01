CREATE DATABASE IF NOT EXISTS empresa;

CREATE TABLE pessoa (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE funcionario (
  id_pessoa INT NOT NULL,
  matricula VARCHAR(10) NOT NULL UNIQUE,
  departamento VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_pessoa),
  CONSTRAINT fk_funcionario_pessoa
    FOREIGN KEY (id_pessoa)
    REFERENCES pessoa (id)
    ON DELETE CASCADE
);

CREATE TABLE projeto (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  descricao TEXT NULL,
  id_funcionario INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_projeto_funcionario
    FOREIGN KEY (id_funcionario)
    REFERENCES funcionario (id_pessoa)
    ON DELETE RESTRICT
);


