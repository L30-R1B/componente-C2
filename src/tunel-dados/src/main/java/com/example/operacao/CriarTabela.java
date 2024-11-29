package com.example.operacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.conexao.ConexaoSQLServer;

public class CriarTabela {
    public static void criarTabela(String nomeTabela) {
        Connection conn = ConexaoSQLServer.getConexao();
        Statement stmt = null;

        String sql = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='" + nomeTabela + "' AND xtype='U') " +
                     "BEGIN " +
                     "CREATE TABLE " + nomeTabela + " (" +
                     "ID INT PRIMARY KEY, " +
                     "Campo1 INT, " +
                     "Campo2 INT, " +
                     "Campo3 INT, " +
                     "Campo4 INT, " +
                     "Campo5 INT, " +
                     "Campo6 INT, " +
                     "Float1 FLOAT, " +
                     "Float2 FLOAT, " +
                     "Float3 FLOAT, " +
                     "DataHora1 DATETIME, " +
                     "DataHora2 DATETIME" +
                     "); " +
                     "END";

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Tabela '" + nomeTabela + "' criada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela '" + nomeTabela + "': " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
