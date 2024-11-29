package com.example.simulacao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.conexao.ConexaoSQLServer;

public class ImprimirTabela {

    public static void imprimirTabela(String nomeTabela) {
        Connection conn = ConexaoSQLServer.getConexao();
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM " + nomeTabela;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            System.out.println("Conteúdo da tabela '" + nomeTabela + "':");
            System.out.println("--------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("ID");
                int campo1 = rs.getInt("Campo1");
                int campo2 = rs.getInt("Campo2");
                int campo3 = rs.getInt("Campo3");
                int campo4 = rs.getInt("Campo4");
                int campo5 = rs.getInt("Campo5");
                int campo6 = rs.getInt("Campo6");
                float float1 = rs.getFloat("Float1");
                float float2 = rs.getFloat("Float2");
                float float3 = rs.getFloat("Float3");
                String dataHora1 = rs.getString("DataHora1");
                String dataHora2 = rs.getString("DataHora2");

                System.out.println(
                    "ID: " + id +
                    ", Campo1: " + campo1 +
                    ", Campo2: " + campo2 +
                    ", Campo3: " + campo3 +
                    ", Campo4: " + campo4 +
                    ", Campo5: " + campo5 +
                    ", Campo6: " + campo6 +
                    ", Float1: " + float1 +
                    ", Float2: " + float2 +
                    ", Float3: " + float3 +
                    ", DataHora1: " + dataHora1 +
                    ", DataHora2: " + dataHora2
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao imprimir a tabela '" + nomeTabela + "': " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
