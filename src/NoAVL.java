package src;

public class NoAVL {
    public Eletrodomestico dado; 
    public NoAVL esquerdo; 
    public NoAVL direito; 
    public int altura; 

    // Construtor
    public NoAVL(Eletrodomestico dado) {
        this.dado = dado;
        this.esquerdo = null;
        this.direito = null;
        this.altura = 1; 
    }
}