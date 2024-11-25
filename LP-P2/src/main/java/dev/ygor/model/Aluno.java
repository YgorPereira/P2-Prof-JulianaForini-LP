package dev.ygor.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Aluno {

//    Atributos:
    int idAluno;
    String nome;
    String cpf;
    String dataDeNascimento;
    double peso;
    double altura;
    double imc;
    String situacaoIMC;

//    Constructor

    public Aluno(String nome, String cpf, String dataDeNascimento, double peso, double altura) {
        if(peso <= 0 || altura <= 0)
            throw new IllegalArgumentException("Peso e altura precisam ser maiores que zero");

        this.nome = nome;
        this.cpf = cpf;
        this.dataDeNascimento = dataDeNascimento;
        this.peso = peso;
        this.altura = altura;
    }

//    getters and setters

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(String dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public String getSituacaoIMC() {
        return situacaoIMC;
    }

    public void setSituacaoIMC(String situacaoIMC) {
        this.situacaoIMC = situacaoIMC;
    }

//    gravar num arquivo
    public void gravarNumArquivo(){
        File dir = new File("C:\\Users\\ygorr\\Desktop\\Fatec\\LP-P2\\FilesExamples");
        if(!dir.exists())
            dir.mkdirs();

        File arq = new File("C:\\Users\\ygorr\\Desktop\\Fatec\\LP-P2\\FilesExamples", "arquivos" + getNome().replace(" ", "") + ".txt");

        try {
            if (arq.createNewFile()){
                System.out.println("Arquivo de aluno criado com sucesso...");
            }
            try (FileWriter writer = new FileWriter(arq, true);) {
                LocalDate dataDeCalculo = LocalDate.now();

                String imcFormatado = String.format("%.2f", imc);

                writer.write("Aluno: " + nome + "\n");
                writer.write("CPF: " + cpf + "\n");
                writer.write("IMC: " + imcFormatado + " (Calculado em " + dataDeCalculo + ")\n");
                writer.write("Interpretação do IMC: " + situacaoIMC + "\n");
                writer.write("-----------------------------\n");
            }

        } catch (IOException e) {
            throw new RuntimeException("Falha ao grava arquivo");
        }
    }

//    metodo calculaIMC
    public double calculaIMC() {
        double imcCalculado = peso / (altura * altura);
        this.setImc(Math.round(imcCalculado * 100.0) / 100.0); // Arredonda para 2 casas decimais
        return this.getImc();
    }


    //    metodo calculaIMC
    public void interpretaIMC(){
        double imc = calculaIMC();

//        Lógica de inerpretação do IMC
        if(imc < 18.5)
            this.setSituacaoIMC("abaixo do peso");
        else if (imc >= 18.5 && imc <= 24.9)
            this.setSituacaoIMC("peso normal");
        else if (imc >= 25 && imc <= 29.9)
            this.setSituacaoIMC("sobrepeso");
        else if (imc >= 30 && imc <= 34.9)
            this.setSituacaoIMC("obesidade grau I");
        else if (imc >= 35 && imc <= 39.9)
            this.setSituacaoIMC("obesidade grau II");
        else
            this.setSituacaoIMC("obesidade grau III");
    }
}