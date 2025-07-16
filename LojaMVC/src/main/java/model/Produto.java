package model;

public class Produto {

    private int id;
    private String nome;
    private int quantidadeEstoque;
    private double valor;

    public Produto(String nome, int quantidadeEstoque, double valor) {
        this.nome = nome;
        this.quantidadeEstoque = quantidadeEstoque;
        this.valor = valor;

    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
