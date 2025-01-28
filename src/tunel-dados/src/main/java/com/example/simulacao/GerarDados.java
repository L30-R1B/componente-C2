package com.example.simulacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.example.operacao.InserirDados;
import com.example.processamento.EnvLoader;

public class GerarDados {

    public static String gerarDataHoraAleatoria(String inicio, String fim) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime dataInicio = LocalDateTime.parse(inicio, formatter);
        LocalDateTime dataFim = LocalDateTime.parse(fim, formatter);

        long inicioEpoch = dataInicio.atZone(java.time.ZoneId.systemDefault()).toEpochSecond();
        long fimEpoch = dataFim.atZone(java.time.ZoneId.systemDefault()).toEpochSecond();
        long randomEpoch = ThreadLocalRandom.current().nextLong(inicioEpoch, fimEpoch);

        LocalDateTime dataAleatoria = LocalDateTime.ofEpochSecond(randomEpoch, 0, java.time.ZoneOffset.UTC);

        return dataAleatoria.format(formatter);
    }

    public static Object gerarValorAleatorio(String tipo, Random rand) {
        switch (tipo.toUpperCase()) {
            case "INT":
            case "BIGINT":
                return rand.nextInt(1, 10000); // Gera valores inteiros aleatórios
            case "FLOAT":
            case "DOUBLE":
                return rand.nextDouble(1.0, 1000.0); // Gera valores decimais aleatórios
            case "DATETIME":
            case "DATE":
                return gerarDataHoraAleatoria("2020-01-01 00:00:00", "2024-12-31 23:59:59");
            case "VARCHAR":
            case "TEXT":
                return "Texto_" + rand.nextInt(1, 100); // Gera strings aleatórias simples
            default:
                return null; // Para tipos não reconhecidos
        }
    }

    public static void gerarDadosTabela(String nomeTabela, int quantidadeLinhasGeradas, String envFilePath) {
        // Carregar configuração do arquivo .env
        try {
            EnvLoader.loadEnv(envFilePath);
        } catch (Exception e) {
            System.err.println("Erro ao carregar o arquivo .env: " + e.getMessage());
            return;
        }

        Random rand = new Random();

        for (int i = 0; i < quantidadeLinhasGeradas; i++) {
            // Criar mapa de valores dinâmicos para os campos
            Map<String, Object> valores = new HashMap<>();
            for (Map.Entry<String, String> campo : EnvLoader.getFields().entrySet()) {
                valores.put(campo.getKey(), gerarValorAleatorio(campo.getValue(), rand));
            }

            // Inserir linha na tabela
            InserirDados.inserirLinha(nomeTabela, envFilePath, valores);
        }
    }
}
