/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Empresa {
    private ArrayList<Mercadoria> estoque;
    private String localizacao;
    private String cnpj;

    public Empresa(String localizacao, String cnpj) {
        this.estoque = new ArrayList<>();
        this.localizacao = localizacao;
        this.cnpj = cnpj;
    }

    public void adicionarMercadoria(Mercadoria mercadoria) {
        estoque.add(mercadoria);
    }

    public ArrayList<Mercadoria> listarEstoque() {
        return estoque;
    }
    
    public String getLocalizacao() {
        return localizacao;
    }

    public String getCnpj() {
        return cnpj;
    }
}

