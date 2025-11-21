package src;
public class Eletrodomestico {
    private int codigo; 
    private String nome; 
    private int quantidadeEstoque; 
    private double precoUnitario; 

    // Construtor
    public Eletrodomestico(int codigo, String nome, int quantidadeEstoque, double precoUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.quantidadeEstoque = quantidadeEstoque;
        this.precoUnitario = precoUnitario;
    }

    // Getters
    public int getCodigo() {
        return codigo;
    }
    
    public String getNome() { 
        return nome;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    
    public double getPrecoUnitario() {
        return precoUnitario;
    }

    // Setter para o Estoque 
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}