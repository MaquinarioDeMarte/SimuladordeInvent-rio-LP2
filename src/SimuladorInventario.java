package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SimuladorInventario {

    public static void main(String[] args) {
        ArvoreAVL arvore = new ArvoreAVL();
        String caminhoCatalogo = "catalogo.csv";

        carregarCatalogo(arvore, caminhoCatalogo);
        
        arvore.listarEmOrdem();

    }

    public static void carregarCatalogo(ArvoreAVL arvore, String caminhoArquivo) {
        System.out.println("Iniciando carregamento do catálogo...");
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            br.readLine(); 

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";"); 
                
                if (dados.length == 4) {
                    try {
                        int codigo = Integer.parseInt(dados[0].trim());
                        String nome = dados[1].trim();
                        int estoque = Integer.parseInt(dados[2].trim());
                        double preco = Double.parseDouble(dados[3].trim());

                        if (estoque > 0) {
                            Eletrodomestico produto = new Eletrodomestico(codigo, nome, estoque, preco);
                            arvore.inserir(produto); 
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Erro de formato de número na linha: " + linha);
                    }
                }
            }
            System.out.println("Carregamento do catálogo concluído.");

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + caminhoArquivo + ": " + e.getMessage());
            System.err.println("Verifique se o arquivo 'catalogo.csv' existe no diretório correto.");
        }
    }
}