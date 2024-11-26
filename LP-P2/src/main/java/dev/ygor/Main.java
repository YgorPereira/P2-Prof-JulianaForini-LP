package dev.ygor;

import dev.ygor.DAO.AlunoDAO;
import dev.ygor.factory.ConnectionFactory;
import dev.ygor.model.Aluno;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try(Connection connection = connectionFactory.getConnections()) {
            AlunoDAO alunoDAO = new AlunoDAO(connection);

            Aluno Luana = new Aluno("Luana Souza", "98765432100", "2006-11-17", 55.0, 1.55);
            Aluno Ygor = new Aluno("Ygor Pereira", "12345678900", "1995-08-15", 68.0, 1.65);

            alunoDAO.cadastrarAluno(Luana);
            alunoDAO.cadastrarAluno(Ygor);

            Ygor.calculaIMC();
            Ygor.interpretaIMC();
            alunoDAO.inserirImcESituacao(Ygor);

            Luana.calculaIMC();
            Luana.interpretaIMC();
            alunoDAO.inserirImcESituacao(Luana);

            Ygor.gravarNumArquivo();
            Luana.gravarNumArquivo();

            alunoDAO.buscarAlunoPorNome(Ygor.getNome());

            alunoDAO.excluirAlunoPorCPF("98765432100");

            Ygor.setPeso(60);

            alunoDAO.update(Ygor);
        } catch (Exception e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
        }
    }
}