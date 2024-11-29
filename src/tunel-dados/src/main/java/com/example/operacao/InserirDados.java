package com.example.operacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.conexao.ConexaoSQLServer;

public class InserirDados {

    private static boolean idExiste(String nomeTabela, int id) {
        Connection conn = ConexaoSQLServer.getConexao();
        String sql = "SELECT COUNT(*) FROM " + nomeTabela + " WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void inserirLinha(String nomeTabela, int id, int campo1, int campo2, int campo3, int campo4, int campo5, int campo6,
                                    double float1, double float2, double float3, String dataHora1, String dataHora2) {
        
        if (idExiste(nomeTabela, id)) {
            System.err.println("Erro: ID já existe na tabela. Ignorando a inserção.");
            return;
        }    
        
        Connection conn = ConexaoSQLServer.getConexao();
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO " + nomeTabela + " (ID, Campo1, Campo2, Campo3, Campo4, Campo5, Campo6, " +
                     "Float1, Float2, Float3, DataHora1, DataHora2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setInt(2, campo1);
            pstmt.setInt(3, campo2);
            pstmt.setInt(4, campo3);
            pstmt.setInt(5, campo4);
            pstmt.setInt(6, campo5);
            pstmt.setInt(7, campo6);
            pstmt.setDouble(8, float1);
            pstmt.setDouble(9, float2);
            pstmt.setDouble(10, float3);
            pstmt.setString(11, dataHora1);
            pstmt.setString(12, dataHora2);

            pstmt.executeUpdate();
            System.out.println("Linha adicionada com sucesso na tabela '" + nomeTabela + "'!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir a linha na tabela '" + nomeTabela + "': " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
