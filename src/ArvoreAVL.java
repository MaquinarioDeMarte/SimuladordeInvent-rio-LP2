package src;

public class ArvoreAVL {
    private NoAVL raiz;

    public ArvoreAVL() {
        this.raiz = null;
    }
    
    // Método auxiliar 1: Retorna a altura do nó
    private int obterAltura(NoAVL no) {
        return (no == null) ? 0 : no.altura;
    }

    // Método auxiliar 2: Atualiza a altura do nó
    private void atualizarAltura(NoAVL no) {
        if (no != null) {
            // A altura é 1 + o máximo da altura entre os filhos esquerdo e direito.
            no.altura = 1 + Math.max(obterAltura(no.esquerdo), obterAltura(no.direito));
        }
    }
    
    // Método auxiliar 3: Calcula o Fator de Balanceamento
    // Fator > 1 ou < -1 indica desequilíbrio.
    private int obterFatorBalanceamento(NoAVL no) {
        if (no == null) {
            return 0;
        }
        return obterAltura(no.esquerdo) - obterAltura(no.direito);
    }
    
    // Esqueleto para os métodos principais (a serem implementados)
    // public NoAVL inserir(Eletrodomestico produto) { /* ... */ } 
    // public Eletrodomestico buscar(int codigo) { /* ... */ }
    // public NoAVL remover(int codigo) { /* ... */ } 
}