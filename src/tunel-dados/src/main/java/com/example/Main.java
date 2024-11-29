package com.example;

import com.example.conexao.ConexaoSQLServer;
import com.example.operacao.CriarTabela;
import com.example.operacao.ExportarParaJson;
import com.example.operacao.PubSubSender;
import com.example.simulacao.GerarDados;

import io.grpc.LoadBalancerRegistry;
import io.grpc.internal.PickFirstLoadBalancerProvider;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.exit(1);
        }

        LoadBalancerRegistry.getDefaultRegistry().register(new PickFirstLoadBalancerProvider());

        final String OPERACAO = args[0];

        try {
            ConexaoSQLServer.iniciarConexao();
            switch (OPERACAO){
                case "CRIAR_TABELA":
                    if(args.length < 2){
                        System.err.println("Argumentos : CRIAR_TABELA <(String)nome-da-tabela>");
                        break;
                    }
                    CriarTabela.criarTabela(args[1]);
                break;
                case "SALVAR_TABELA_COMO_JSON":
                    if(args.length < 3){
                        System.err.println("Argumentos : SALVAR_TABELA_COMO_JSON <(String)nome-da-tabela> <(String)caminho/arquivo.json>");
                        break;
                    }
                    ExportarParaJson.salvarTabelaComoJson(args[1], args[2]);
                break;
                case "PREENCHER_TABELA":
                    if(args.length < 3){
                        System.err.println("Argumentos : PREENCHER_TABELA <(String)nome-da-tabela> <(int)quantidade-dados-gerados>");
                        break;
                    }
                try {
                    int numero = Integer.parseInt(args[2]);
                    GerarDados.gerarDadosTabela(args[1], numero);
                } catch (NumberFormatException e) {
                    System.err.println("Argumento inválido ! ! !");
                }
                break;
                case "ENVIAR_DADOS_PARA_PUB-SUB":
                    if (args.length < 3) {
                        System.err.println("Argumentos : ENVIAR_DADOS_PARA_PUB-SUB <TOPIC_ID> <CAMINHO_ARQUIVO_JSON> <CAMINHO_ARQUIVO_CREDENCIAIS>");
                        break;
                    }

                    PubSubSender.enviarArquivoParaPubSub(args[1], args[2], args[3]);
                break;
                default:
                    System.err.println("Operação inválida ! ! !");
                break;
            }


        } catch (Exception e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConexaoSQLServer.encerrarConexao();
        }
    }
}
