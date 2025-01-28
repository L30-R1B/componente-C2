package com.example.operacao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.example.conexao.ConexaoSQLServer;
import com.example.processamento.EnvLoader;

public class InserirDados {

    public static void inserirLinha(String nomeTabela, String envFilePath, Map<String, Object> valores) {
        try {
            EnvLoader.loadEnv(envFilePath);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo .env: " + e.getMessage());
            return;
        }

        Connection conn = ConexaoSQLServer.getConexao();
        PreparedStatement pstmt = null;

        // Construir dinamicamente a query
        StringBuilder campos = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        EnvLoader.getFields().forEach((campo, tipo) -> {
            campos.append(campo).append(", ");
            placeholders.append("?, ");
        });

        // Remover última vírgula e espaço
        campos.setLength(campos.length() - 2);
        placeholders.setLength(placeholders.length() - 2);

        String sql = "INSERT INTO " + nomeTabela + " (" + campos + ") VALUES (" + placeholders + ")";

        try {
            pstmt = conn.prepareStatement(sql);

            int index = 1;
            for (String campo : EnvLoader.getFields().keySet()) {
                pstmt.setObject(index++, valores.get(campo));
            }

            pstmt.executeUpdate();
            System.out.println("Linha adicionada com sucesso na tabela '" + nomeTabela + "'!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir a linha na tabela '" + nomeTabela + "': " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
