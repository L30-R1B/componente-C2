package com.example.operacao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.conexao.ConexaoSQLServer;
import com.example.processamento.EnvLoader;

public class CriarTabela {
    public static void criarTabela(String nomeTabela, String envFilePath) {
        try {
            EnvLoader.loadEnv(envFilePath);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo .env: " + e.getMessage());
            return;
        }

        Connection conn = ConexaoSQLServer.getConexao();
        Statement stmt = null;

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='")
                  .append(nomeTabela)
                  .append("' AND xtype='U') ")
                  .append("BEGIN CREATE TABLE ")
                  .append(nomeTabela)
                  .append(" (");

        // Adicionar campos dinâmicos
        EnvLoader.getFields().forEach((campo, tipo) -> {
            sqlBuilder.append(campo).append(" ").append(tipo).append(", ");
        });

        // Remover última vírgula e espaço
        sqlBuilder.setLength(sqlBuilder.length() - 2);
        sqlBuilder.append("); END");

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlBuilder.toString());
            System.out.println("Tabela '" + nomeTabela + "' criada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela '" + nomeTabela + "': " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
