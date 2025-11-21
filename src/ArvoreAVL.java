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

    public Eletrodomestico buscar(int codigo) {
        return buscarRecursivo(raiz, codigo);
    }

    private Eletrodomestico buscarRecursivo(NoAVL no, int codigo) {
        if (no == null) {
            return null; 
        }

        if (codigo == no.dado.getCodigo()) {
            return no.dado; 
        }

        if (codigo < no.dado.getCodigo()) {
            return buscarRecursivo(no.esquerdo, codigo);
        } else {
            return buscarRecursivo(no.direito, codigo);
        }
    }

    public void listarEmOrdem() {
        System.out.println("\n--- CATÁLOGO DE PRODUTOS DISPONÍVEIS ---");
        listarEmOrdemRecursivo(raiz);
        System.out.println("----------------------------------------\n");
    }

    private void listarEmOrdemRecursivo(NoAVL no) {
        if (no != null) {
            listarEmOrdemRecursivo(no.esquerdo);

            if (no.dado.getQuantidadeEstoque() > 0) { 
                System.out.println(
                    "CÓD: " + no.dado.getCodigo() + 
                    " | NOME: " + no.dado.getNome() + 
                    " | ESTOQUE: " + no.dado.getQuantidadeEstoque() + 
                    " | PREÇO: R$ " + String.format("%.2f", no.dado.getPrecoUnitario())
                );
            }

            listarEmOrdemRecursivo(no.direito);
        }
    }

    public void inserir(Eletrodomestico produto) {
        this.raiz = inserirRecursivo(this.raiz, produto);
    }

    private NoAVL inserirRecursivo(NoAVL no, Eletrodomestico produto) {
        if (no == null) {
            System.out.println("Inserindo produto " + produto.getCodigo());
            return new NoAVL(produto);
        }

        if (produto.getCodigo() < no.dado.getCodigo()) {
            no.esquerdo = inserirRecursivo(no.esquerdo, produto);
        } else if (produto.getCodigo() > no.dado.getCodigo()) {
            no.direito = inserirRecursivo(no.direito, produto);
        } else {
            return no;
        }

        atualizarAltura(no);

        int saldo = obterFatorBalanceamento(no);

        
        if (saldo > 1 && produto.getCodigo() < no.esquerdo.dado.getCodigo()) {
            return rotacaoDireita(no); 
        }

        if (saldo < -1 && produto.getCodigo() > no.direito.dado.getCodigo()) {
            return rotacaoEsquerda(no);
        }

        if (saldo > 1 && produto.getCodigo() > no.esquerdo.dado.getCodigo()) {
            no.esquerdo = rotacaoEsquerda(no.esquerdo);
            return rotacaoDireita(no);
        }

        if (saldo < -1 && produto.getCodigo() < no.direito.dado.getCodigo()) {
            no.direito = rotacaoDireita(no.direito);
            return rotacaoEsquerda(no);
        }

        return no; 
    }

    // Provisório
    private NoAVL rotacaoDireita(NoAVL y) {
        return y;
    }

    private NoAVL rotacaoEsquerda(NoAVL x) {
        return x; 
    }

    // Esqueleto para os métodos principais (a serem implementados)
    // public NoAVL inserir(Eletrodomestico produto) { /* ... */ } 
    // public Eletrodomestico buscar(int codigo) { /* ... */ }
    // public NoAVL remover(int codigo) { /* ... */ } 
}