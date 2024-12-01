/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Main {
    
    private static ArrayList<Fornecedor> fornecedores = new ArrayList<>();
    private static Empresa empresa = new Empresa("Santa Cruz do Sul", "12345678900001");
    private static ArrayList<Mercadoria> carrinho = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Menu:");
            System.out.println("1 - Adicionar nova Mercadoria em Fornecedor");
            System.out.println("2 - Listar Itens do Fornecedor");
            System.out.println("3 - Comprar item de Fornecedor");
            System.out.println("4 - Listar Itens em estoque");
            System.out.println("5 - Adicionar Item ao carrinho");
            System.out.println("6 - Excluir Item do Carrinho");
            System.out.println("7 - Listar carrinho");
            System.out.println("8 - Finalizar Compra");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    adicionarMercadoriaFornecedor(scanner);
                    break;
                case 2:
                    listarItensFornecedor(scanner);
                    break;
                case 3:
                    break;
                case 4:
                    listarItensEstoque(scanner);
                    break;
                case 5:
                    adicionarItemCarrinho(scanner);
                    break;
                case 6:
                    excluirItemCarrinho(scanner);
                    break;
                case 7:
                    listarCarrinho(scanner);
                    break;
                case 8:
                    finalizarCompra();
                    break;
                case 0:
                   System.out.println("Salvando dados...");
    salvarTodosFornecedores();
    System.out.println("Saindo...");
    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void adicionarMercadoriaFornecedor(Scanner scanner) {
        System.out.print("Nome do Fornecedor: ");
        String nomeFornecedor = scanner.next();
        Fornecedor fornecedor = encontrarFornecedor(nomeFornecedor);

        if (fornecedor == null) {
            System.out.print("Fornecedor não encontrado. Deseja adicioná-lo? (s/n): ");
            char resposta = scanner.next().charAt(0);
            if (resposta == 's' || resposta == 'S') {
                System.out.print("Tempo de entrega: ");
                int tempoEntrega = scanner.nextInt();
                System.out.print("Desconto a cada X unidades: ");
                int descontoX = scanner.nextInt();
                System.out.print("Valor do desconto: ");
                double valorDesconto = scanner.nextDouble();
                fornecedor = new Fornecedor(nomeFornecedor, tempoEntrega, descontoX, valorDesconto);
                fornecedores.add(fornecedor);
            } else {
                return;
            }
        }

        System.out.print("Nome da mercadoria: ");
        String nomeMercadoria = scanner.next();
        System.out.print("Grupo: ");
        String grupo = scanner.next();
        System.out.print("Valor de compra: ");
        double valorCompra = scanner.nextDouble();
        System.out.print("Valor de venda: ");
        double valorVenda = scanner.nextDouble();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();

        Mercadoria mercadoria = new Mercadoria(nomeMercadoria, grupo, valorCompra, valorVenda, quantidade);
        fornecedor.adicionarMercadoria(mercadoria);
        System.out.println("Mercadoria adicionada com sucesso!");
    }

    private static Fornecedor encontrarFornecedor(String nome) {
        for (Fornecedor f : fornecedores) {
            if (f.getNome().equalsIgnoreCase(nome)) {
                return f;
            }
        }
        return null;
    }

    private static void listarItensFornecedor(Scanner scanner) {
        System.out.print("Nome do Fornecedor: ");
        String nomeFornecedor = scanner.next();
        Fornecedor fornecedor = encontrarFornecedor(nomeFornecedor);

        if (fornecedor != null) {
            ArrayList<Mercadoria> mercadorias = fornecedor.listarMercadorias();
            Collections.sort(mercadorias);
            double total = 0;
            for (Mercadoria m : mercadorias) {
                System.out.println("Nome: " + m.getNome() + ", Grupo: " + m.getGrupo() + 
                                   ", Valor de Venda: " + m.getValorVenda() + 
                                   ", Quantidade: " + m.getQuantidade());
                total += m.getValorVenda() * m.getQuantidade();
            }
            System.out.println("Total de Mercadorias: " + total);
        } else {
            System.out.println("Fornecedor não encontrado.");
        }
    }

    private static void listarItensEstoque(Scanner scanner) {
        ArrayList<Mercadoria> estoque = empresa.listarEstoque();
        System.out.println("Ordenar por: 1-Nome 2-Grupo 3-Valor");
        int criterio = scanner.nextInt();

        switch (criterio) {
            case 1:
                Collections.sort(estoque);
                break;
            case 2:
                Collections.sort(estoque, Mercadoria.ordenarPorGrupo()); 
                break;
            case 3:
                Collections.sort(estoque, Mercadoria.ordenarPorValor()); 
                break;
            default:
                System.out.println("Critério inválido.");
                return;
        }

        for (Mercadoria m : estoque) {
            System.out.println("Nome: " + m.getNome() + ", Valor de Venda: " + m.getValorVenda() + 
                               ", Quantidade: " + m.getQuantidade());
        }
    }

    private static void adicionarItemCarrinho(Scanner scanner) {
        System.out.print("Digite o nome do item: ");
        String nome = scanner.next();
        Mercadoria mercadoria = encontrarMercadoriaEmEstoque(nome);

        if (mercadoria != null && mercadoria.getQuantidade() > 0) {
            carrinho.add(mercadoria);
            mercadoria.setQuantidade(mercadoria.getQuantidade() - 1);
            System.out.println("Item adicionado ao carrinho!");
        } else {
            System.out.println("Item não está em estoque. Tentando comprar do fornecedor...");
            comprarDoFornecedor(nome);
        }
    }

    private static Mercadoria encontrarMercadoriaEmEstoque(String nome) {
        for (Mercadoria m : empresa.listarEstoque()) {
            if (m.getNome().equalsIgnoreCase(nome)) {
                return m;
            }
        }
        return null;
    }

    private static void comprarDoFornecedor(String nome) {
        System.out.print("Informe o nome do fornecedor: ");
        String nomeFornecedor = new Scanner(System.in).next();
        Fornecedor fornecedor = encontrarFornecedor(nomeFornecedor);

        if (fornecedor != null) {
            for (Mercadoria m : fornecedor.listarMercadorias()) {
                if (m.getNome().equalsIgnoreCase(nome) && m.getQuantidade() > 0) {
                    carrinho.add(m);
                    System.out.println("Item comprado do fornecedor e adicionado ao carrinho!");
                    return;
                }
            }
            System.out.println("Item não encontrado no fornecedor.");
        } else {
            System.out.println("Fornecedor não encontrado.");
        }
    }

    private static void excluirItemCarrinho(Scanner scanner) {
        System.out.print("Digite o nome do item para excluir: ");
        String nome = scanner.next();
    }

    private static void listarCarrinho(Scanner scanner) {
        System.out.println("Ordenar por: 1-Nome 2-Grupo 3-Valor");
        int criterio = scanner.nextInt();

        switch (criterio) {
            case 1:
                Collections.sort(carrinho);
                break;
            case 2:
                Collections.sort(carrinho, Mercadoria.ordenarPorGrupo()); 
                break;
            case 3:
                Collections.sort(carrinho, Mercadoria.ordenarPorValor());
            default:
                System.out.println("Critério inválido.");
                return;
        }

        for (Mercadoria m : carrinho) {
            System.out.println("Nome: " + m.getNome() + ", Valor: " + m.getValorVenda());
        }
    }

    private static void finalizarCompra() {
        if (carrinho.isEmpty()) {
        System.out.println("O carrinho está vazio! Adicione itens antes de finalizar a compra.");
        return;
    }

    for (Mercadoria item : carrinho) {
        Mercadoria estoqueItem = encontrarMercadoriaEmEstoque(item.getNome());
        if (estoqueItem != null) {
            estoqueItem.setQuantidade(estoqueItem.getQuantidade() - 1);
        } else {
            Fornecedor fornecedor = encontrarFornecedorQuePossui(item.getNome());
            if (fornecedor != null) {
                Mercadoria fornecedorItem = encontrarMercadoriaFornecedor(fornecedor, item.getNome());
                fornecedorItem.setQuantidade(fornecedorItem.getQuantidade() - 1);
            }
        }
    }

    carrinho.clear();
    System.out.println("Compra finalizada com sucesso! Carrinho esvaziado e estoque atualizado.");
}

    private static Fornecedor encontrarFornecedorQuePossui(String nomeMercadoria) {
    for (Fornecedor fornecedor : fornecedores) {
        for (Mercadoria mercadoria : fornecedor.listarMercadorias()) {
            if (mercadoria.getNome().equalsIgnoreCase(nomeMercadoria)) {
                return fornecedor;
            }
        }
    }
    return null;
}

private static Mercadoria encontrarMercadoriaFornecedor(Fornecedor fornecedor, String nomeMercadoria) {
    for (Mercadoria mercadoria : fornecedor.listarMercadorias()) {
        if (mercadoria.getNome().equalsIgnoreCase(nomeMercadoria)) {
            return mercadoria;
        }
    }
    return null;
}
    

private static void salvarTodosFornecedores() {
    for (Fornecedor fornecedor : fornecedores) {
        fornecedor.salvar();
    }
    System.out.println("Todos os fornecedores foram salvos com sucesso!");
}
}


