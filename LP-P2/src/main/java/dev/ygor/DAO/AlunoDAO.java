package dev.ygor.DAO;

import dev.ygor.model.Aluno;

import javax.xml.transform.Result;
import java.sql.*;

public class AlunoDAO {
    private final Connection connection;

    public AlunoDAO(Connection connection) {
        this.connection = connection;
    }

//    create
    public void cadastrarAluno (Aluno aluno){
        String sql = "INSERT INTO aluno (nome_aluno, cpf_aluno, data_nascimento_aluno, peso_aluno, altura_aluno, imc, interpretacao_imc_aluno) VALUES (?,?,?,?,?,?,?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getDataDeNascimento());
            stmt.setDouble(4, aluno.getPeso());
            stmt.setDouble(5, aluno.getAltura());
            stmt.setNull(6, Types.DOUBLE);
            stmt.setNull(7, Types.VARCHAR);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                aluno.setIdAluno((rs.getInt(1)));
                System.out.println("Id do aluno " + aluno.getNome() + ": " + aluno.getIdAluno());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar aluno no banco de dados.");
        }
    }

//    metodo ler aluno por nome
    public Aluno buscarAlunoPorNome(String nome){
        String sql = "SELECT * FROM aluno WHERE nome_aluno = ?";
        Aluno aluno = null;

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                aluno = new Aluno(rs.getString("nome_aluno"),
                        rs.getString("cpf_aluno"),
                        rs.getDate("data_nascimento_aluno").toString(),
                        rs.getDouble("peso_aluno"),
                        rs.getDouble("altura_aluno")
                    );
                aluno.setImc(rs.getDouble("imc"));
                aluno.setSituacaoIMC(rs.getString("interpretacao_imc_aluno"));
            }
        } catch (SQLException e){
                throw new RuntimeException("Não foi possível encontrar o aluno.");
        }
        return aluno;
    }


//    update IMC e Situacao imc
    public void inserirImcESituacao (Aluno aluno){
        String sql = "UPDATE aluno SET imc = ?, interpretacao_imc_aluno = ? WHERE nome_aluno = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setDouble(1, aluno.getImc());
            stmt.setString(2, aluno.getSituacaoIMC());
            stmt.setString(3, aluno.getNome());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir imc e situaçao do aluno " + aluno.getNome() + " no banco de dados.");
        }
    }

    //    update
    public void update(Aluno aluno){
        String sql = "UPDATE aluno SET nome_aluno = ?, cpf_aluno = ?, data_nascimento_aluno = ?, peso_aluno = ?, altura_aluno = ?, imc = ?, interpretacao_imc_aluno = ? WHERE nome_aluno = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getDataDeNascimento());
            stmt.setDouble(4, aluno.getPeso());
            stmt.setDouble(5, aluno.getAltura());
            stmt.setDouble(6, aluno.getImc());
            stmt.setString(7, aluno.getSituacaoIMC());
            stmt.setString(8, aluno.getNome());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir imc e situaçao do aluno " + aluno.getNome() + " no banco de dados.");
        }
    }

//    metodo excluir aluno
    public void excluirAlunoPorCPF (String cpf) {
        String sql = "DELETE FROM aluno where cpf_aluno = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, cpf);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível excluir esse aluno.");
        }

    }
}
