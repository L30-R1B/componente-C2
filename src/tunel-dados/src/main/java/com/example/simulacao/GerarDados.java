package com.example.simulacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.example.operacao.InserirDados;

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
    
    public static void gerarDadosTabela(String nomeTabela, int quantidadeLinhasGeradas){
        Random rand = new Random();

        int campo1, campo2, campo3, campo4, campo5, campo6;
        float float1, float2, float3;
        String dataHora1, dataHora2;

        for(int i = 0; i < quantidadeLinhasGeradas; i ++){
            campo1 = rand.nextInt(0, 10);
            campo2 = rand.nextInt(100, 1000);
            campo3 = rand.nextInt(100, 1000);
            campo4 = rand.nextInt(100, 1000);
            campo5 = rand.nextInt(100000, 1000000);
            campo6 = rand.nextInt(100000, 1000000);

            float1 = rand.nextFloat(0.0f, 10.0f);
            float2 = rand.nextFloat(100.0f, 1000.0f);
            float3 = rand.nextFloat(0.0f, 100.0f);
            
            dataHora1 = gerarDataHoraAleatoria("2020-01-01 00:00:00", "2024-01-01 00:00:00");
            dataHora2 = gerarDataHoraAleatoria("2024-01-01 00:00:00", "2024-12-01 00:00:00");

            InserirDados.inserirLinha(nomeTabela, i, campo1, campo2, campo3, campo4, campo5, campo6, float1, float2, float3, dataHora1, dataHora2);
        }
    }
}
