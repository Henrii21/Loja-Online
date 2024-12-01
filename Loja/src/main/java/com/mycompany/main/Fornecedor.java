/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import java.util.ArrayList;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private ArrayList<Mercadoria> mercadorias;
    private int tempoEntrega;
    private int descontoX;
    private double valorDesconto;

    private static final String PASTA_FORNECEDORES = "fornecedores/";

    public Fornecedor(String nome, int tempoEntrega, int descontoX, double valorDesconto) {
        this.nome = nome;
        this.mercadorias = new ArrayList<>();
        this.tempoEntrega = tempoEntrega;
        this.descontoX = descontoX;
        this.valorDesconto = valorDesconto;

        
        File pasta = new File(PASTA_FORNECEDORES);
        if (!pasta.exists()) {
            pasta.mkdir();
        }
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Mercadoria> listarMercadorias() {
        return mercadorias;
    }

    public void adicionarMercadoria(Mercadoria mercadoria) {
        mercadorias.add(mercadoria);
        salvar();
    }

    public double calcularTotal() {
        double total = 0;
        for (Mercadoria m : mercadorias) {
            total += m.getValorVenda() * m.getQuantidade();
        }
        return total;
    }

    public void salvar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(PASTA_FORNECEDORES + nome.toLowerCase() + ".dat"))) {
            oos.writeObject(this);
            System.out.println("Fornecedor '" + nome + "' salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar fornecedor '" + nome + "': " + e.getMessage());
        }
    }

    public static Fornecedor carregar(String nomeFornecedor) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(PASTA_FORNECEDORES + nomeFornecedor.toLowerCase() + ".dat"))) {
            return (Fornecedor) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar fornecedor '" + nomeFornecedor + "': " + e.getMessage());
            
            return null;
        }
    }
}