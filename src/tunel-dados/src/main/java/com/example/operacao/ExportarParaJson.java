package com.example.operacao;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.conexao.ConexaoSQLServer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ExportarParaJson {

    public static void salvarTabelaComoJson(String nomeTabela, String caminhoArquivo) {
        Connection conn = ConexaoSQLServer.getConexao();
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM " + nomeTabela;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            JsonArray jsonArray = new JsonArray();

            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("ID", rs.getInt("ID"));
                jsonObject.addProperty("Campo1", rs.getInt("Campo1"));
                jsonObject.addProperty("Campo2", rs.getInt("Campo2"));
                jsonObject.addProperty("Campo3", rs.getInt("Campo3"));
                jsonObject.addProperty("Campo4", rs.getInt("Campo4"));
                jsonObject.addProperty("Campo5", rs.getInt("Campo5"));
                jsonObject.addProperty("Campo6", rs.getInt("Campo6"));
                jsonObject.addProperty("Float1", rs.getFloat("Float1"));
                jsonObject.addProperty("Float2", rs.getFloat("Float2"));
                jsonObject.addProperty("Float3", rs.getFloat("Float3"));
                jsonObject.addProperty("DataHora1", rs.getString("DataHora1"));
                jsonObject.addProperty("DataHora2", rs.getString("DataHora2"));

                jsonArray.add(jsonObject);
            }
            try (FileWriter file = new FileWriter(caminhoArquivo)) {
                Gson gson = new Gson();
                file.write(gson.toJson(jsonArray));
                System.out.println("Dados exportados para " + caminhoArquivo);
            } catch (IOException e) {
                System.err.println("Erro ao salvar o arquivo JSON: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("Erro ao exportar a tabela '" + nomeTabela + "': " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
