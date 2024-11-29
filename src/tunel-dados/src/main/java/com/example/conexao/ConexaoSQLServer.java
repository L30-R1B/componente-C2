package com.example.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.autenticacao.ObterCredencial;

public class ConexaoSQLServer {

    private static Connection connection = null;

    public static boolean iniciarConexao(){
        String url = ObterCredencial.getCredencial("URL", ".", "credenciais.env");
        String usuario = ObterCredencial.getCredencial("USUARIO", ".", "credenciais.env");
        String senha = ObterCredencial.getCredencial("SENHA", ".", "credenciais.env");
    
        if(url == null || usuario == null || senha == null){
            return false;
        }

        try {

            connection = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão estabelecida com sucesso!");
            
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean encerrarConexao() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão encerrada com sucesso.");
                return true;
            } catch (SQLException e) {
                System.err.println("Erro ao encerrar a conexão: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Connection getConexao() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(ObterCredencial.getCredencial("URL", ".", "credenciais.env")
                                                        , ObterCredencial.getCredencial("USUARIO", ".", "credenciais.env")
                                                        , ObterCredencial.getCredencial("SENHA", ".", "credenciais.env"));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
