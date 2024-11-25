package dev.ygor;

import dev.ygor.DAO.AlunoDAO;
import dev.ygor.factory.ConnectionFactory;
import dev.ygor.model.Aluno;

import java.sql.Connection;


public class Main {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try (Connection connection = connectionFactory.getConnections()) {
            // Instanciar AlunoDAO com a conexão criada
            AlunoDAO alunoDAO = new AlunoDAO(connection);

            // Criar um novo aluno
            Aluno novoAluno = new Aluno("Maria Oliveira", "98765432100", "1995-08-15", 68.0, 1.65);
            novoAluno.calculaIMC();
            novoAluno.interpretaIMC();

            // Cadastrar o aluno no banco de dados
            alunoDAO.cadastrarAluno(novoAluno);
            alunoDAO.inserirImcESituacao(novoAluno);
            System.out.println("Aluno cadastrado com sucesso!");

            // Buscar aluno pelo nome
            Aluno alunoEncontrado = alunoDAO.buscarAlunoPorNome("Maria Oliveira");
            if (alunoEncontrado != null) {
                System.out.println("Aluno encontrado:");
                System.out.println("Nome: " + alunoEncontrado.getNome());
                System.out.println("IMC: " + alunoEncontrado.getImc());
                System.out.println("Situação IMC: " + alunoEncontrado.getSituacaoIMC());
            } else {
                System.out.println("Aluno não encontrado.");
            }

//            exlui o aluno da tabela
//            alunoDAO.excluirAlunoPorCPF(novoAluno.getCpf());
        } catch (Exception e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
        }
    }
}