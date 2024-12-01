/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import java.util.ArrayList;
import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class Mercadoria implements Comparable<Mercadoria> {
   
private String nome;
    private String grupo;
    private double valorCompra;
    private double valorVenda;
    private int quantidade;

    public Mercadoria(String nome, String grupo, double valorCompra, double valorVenda, int quantidade) {
        this.nome = nome;
        this.grupo = grupo;
        this.valorCompra = valorCompra;
        this.valorVenda = valorVenda;
        this.quantidade = quantidade;
    }
    
    public String getNome() {
        return nome;
    }

    public String getGrupo() {
        return grupo;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int compareTo(Mercadoria outra) {
        return this.nome.compareTo(outra.nome); 
    }

    public static Comparator<Mercadoria> ordenarPorGrupo() {
        return Comparator.comparing(Mercadoria::getGrupo);
    }

    public static Comparator<Mercadoria> ordenarPorValor() {
        return Comparator.comparingDouble(Mercadoria::getValorVenda);
    }
}






