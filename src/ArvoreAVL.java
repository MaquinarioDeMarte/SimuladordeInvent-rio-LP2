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

    // Rotação Simples à Direita
    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerdo;
        NoAVL T2 = x.direito;

        // Realiza a rotação
        x.direito = y;
        y.esquerdo = T2;

        // Atualiza as alturas
        atualizarAltura(y);
        atualizarAltura(x);

        // Retorna a nova raiz da subárvore
        return x;
    }

    // Rotação Simples à Esquerda
    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direito;
        NoAVL T2 = y.esquerdo;

        // Realiza a rotação
        y.esquerdo = x;
        x.direito = T2;

        // Atualiza as alturas
        atualizarAltura(x);
        atualizarAltura(y);

        // Retorna a nova raiz da subárvore
        return y;
    }

    // Chamada da remoção
    public void remover(int codigo) {
        this.raiz = removerRecursivo(this.raiz, codigo);
    }

    // Método que realiza a remoção e o rebalanceamento
    private NoAVL removerRecursivo(NoAVL no, int codigo) {
        // 1. Executar a remoção normal de uma BST
        if (no == null) {
            return no;
        }

        if (codigo < no.dado.getCodigo()) {
            no.esquerdo = removerRecursivo(no.esquerdo, codigo);
        } else if (codigo > no.dado.getCodigo()) {
            no.direito = removerRecursivo(no.direito, codigo);
        } else {
            // Nó encontrado. Verificar casos de filhos:

            // Caso 1 e 2: Nó com apenas um filho ou nenhum
            if ((no.esquerdo == null) || (no.direito == null)) {
                NoAVL temp = null;
                if (temp == no.esquerdo) {
                    temp = no.direito;
                } else {
                    temp = no.esquerdo;
                }

                // Caso sem filhos
                if (temp == null) {
                    temp = no;
                    no = null;
                } else { // Caso com um filho
                    no = temp; // Copia o conteúdo do filho não nulo
                }
            } else {
                // Caso 3: Nó com dois filhos
                // Pega o sucessor em ordem
                NoAVL temp = obterMenorNo(no.direito);

                // Copia os dados do sucessor para este nó
                no.dado = temp.dado;

                // Remove o sucessor
                no.direito = removerRecursivo(no.direito, temp.dado.getCodigo());
            }
        }

        // Se a árvore tinha apenas um nó, retorna null
        if (no == null) {
            return null;
        }

        // 2. Atualizar a altura do nó atual
        atualizarAltura(no);

        // 3. Obter o fator de balanceamento para verificar se desbalanceou
        int saldo = obterFatorBalanceamento(no);

        // 4. Se houver desbalanceamento, realizar as rotações

        // Caso Esquerda-Esquerda
        if (saldo > 1 && obterFatorBalanceamento(no.esquerdo) >= 0) {
            return rotacaoDireita(no);
        }

        // Caso Esquerda-Direita
        if (saldo > 1 && obterFatorBalanceamento(no.esquerdo) < 0) {
            no.esquerdo = rotacaoEsquerda(no.esquerdo);
            return rotacaoDireita(no);
        }

        // Caso Direita-Direita
        if (saldo < -1 && obterFatorBalanceamento(no.direito) <= 0) {
            return rotacaoEsquerda(no);
        }

        // Caso Direita-Esquerda
        if (saldo < -1 && obterFatorBalanceamento(no.direito) > 0) {
            no.direito = rotacaoDireita(no.direito);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    // Método auxiliar para encontrar o nó com menor valor
    private NoAVL obterMenorNo(NoAVL no) {
        NoAVL atual = no;
        while (atual.esquerdo != null) {
            atual = atual.esquerdo;
        }
        return atual;
    }

    // Esqueleto para os métodos principais (a serem implementados)
    // public NoAVL inserir(Eletrodomestico produto) { /* ... */ } 
    // public Eletrodomestico buscar(int codigo) { /* ... */ }
}