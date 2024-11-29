package com.example.autenticacao;

import java.io.File;

import io.github.cdimascio.dotenv.Dotenv;

public class ObterCredencial {

    public static String getCredencial(String credencial, String caminhoEnv, String nomeEnv) {
        Dotenv dotenv = null;

        if (caminhoEnv == null || caminhoEnv.trim().isEmpty()) {
            System.out.println("Erro: caminho do arquivo não pode ser nulo ou vazio.");
            return null;
        }

        File directory = new File(caminhoEnv);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Erro: caminho fornecido não é um diretório válido ou não existe.");
            return null;
        }

        try {
            if (nomeEnv == null) {
                dotenv = Dotenv.configure()
                               .directory(caminhoEnv)
                               .load();
            } else {
                File file = new File(caminhoEnv, nomeEnv);
                if (!file.exists()) {
                    System.out.println("Erro: arquivo não encontrado no caminho " + caminhoEnv + " com nome " + nomeEnv);
                    return null;
                }
                if (!file.canRead()) {
                    System.out.println("Erro: não tem permissões para ler o arquivo " + nomeEnv);
                    return null;
                }

                dotenv = Dotenv.configure()
                               .directory(caminhoEnv)
                               .filename(nomeEnv)
                               .load();
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo .env: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        String valorCredencial = dotenv.get(credencial);
        if (valorCredencial == null) {
            System.out.println("Erro: credencial '" + credencial + "' não encontrada no arquivo .env.");
        }

        return valorCredencial;
    }
}
