package com.example.operacao;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.example.conexao.ConexaoSQLServer;
import com.example.processamento.EnvLoader;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ExportarParaJson {

    public static void salvarTabelaComoJson(String nomeTabela, String caminhoArquivo, String envFilePath) {
        try {
            EnvLoader.loadEnv(envFilePath);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo .env: " + e.getMessage());
            return;
        }

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

                // Adicionar dinamicamente os campos do .env no JSON
                for (Map.Entry<String, String> entry : EnvLoader.getFields().entrySet()) {
                    String campo = entry.getKey();
                    String tipo = entry.getValue();

                    try {
                        Object valor = rs.getObject(campo);
                        if (valor != null) {
                            switch (tipo.toUpperCase()) {
                                case "INT":
                                case "BIGINT":
                                    jsonObject.addProperty(campo, rs.getInt(campo));
                                    break;
                                case "FLOAT":
                                case "DOUBLE":
                                    jsonObject.addProperty(campo, rs.getDouble(campo));
                                    break;
                                case "DATETIME":
                                case "DATE":
                                case "VARCHAR":
                                case "TEXT":
                                    jsonObject.addProperty(campo, rs.getString(campo));
                                    break;
                                default:
                                    jsonObject.addProperty(campo, valor.toString());
                                    break;
                            }
                        } else {
                            jsonObject.addProperty(campo, "null"); // Ou pode usar null diretamente se preferir
                        }
                    } catch (SQLException e) {
                        System.err.println("Erro ao processar o campo '" + campo + "': " + e.getMessage());
                    }
                }

                jsonArray.add(jsonObject);
            }

            // Salvar o JSON no arquivo especificado
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
