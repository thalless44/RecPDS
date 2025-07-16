package model;

import java.sql.Date;

public class Venda {

    private int id;
    private int clienteId;  // ID do cliente
    private int produtoId;  // ID do produto
    private int quantidade;
    private double preco;
    private Date dataVenda;

    // Construtores
    public Venda(int id, int clienteId, int produtoId, int quantidade, double preco, Date dataVenda) {
        this.id = id;
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.preco = preco;
        this.dataVenda = dataVenda;
    }

    public Venda(int clienteId, int produtoId, int quantidade, double preco, Date dataVenda) {
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.preco = preco;
        this.dataVenda = dataVenda;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }
}
