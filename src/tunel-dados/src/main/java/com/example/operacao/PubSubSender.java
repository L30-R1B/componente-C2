package com.example.operacao;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

public class PubSubSender {

    public static void enviarArquivoParaPubSub(String topicId, String caminhoArquivoJson, String caminhoCredenciais) {
        String projectId = obterProjectIdDoJson(caminhoCredenciais);

        if (projectId == null) {
            System.err.println("Não foi possível obter o Project ID do arquivo de credenciais.");
            return;
        }

        System.out.println("Usando o Project ID: " + projectId);

        String topicName = String.format("projects/%s/topics/%s", projectId, topicId);

        Publisher publisher = null;
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(caminhoCredenciais))
                    .createScoped("https://www.googleapis.com/auth/cloud-platform");

            publisher = Publisher.newBuilder(topicName)
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            String conteudoJson = new String(Files.readAllBytes(Paths.get(caminhoArquivoJson)));

            PubsubMessage mensagem = PubsubMessage.newBuilder()
                    .setData(ByteString.copyFromUtf8(conteudoJson))
                    .build();

            publisher.publish(mensagem).get();
            System.out.println("Arquivo enviado com sucesso para o Pub/Sub!");
        } catch (Exception e) {
            System.err.println("Erro ao enviar arquivo para o Pub/Sub: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (publisher != null) {
                try {
                    publisher.shutdown();
                } catch (Exception e) {
                    System.err.println("Erro ao encerrar o Publisher: " + e.getMessage());
                }
            }
        }
    }

    private static String obterProjectIdDoJson(String caminhoCredenciais) {
        try {
            String conteudoJson = new String(Files.readAllBytes(Paths.get(caminhoCredenciais)));
            com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser.parseString(conteudoJson).getAsJsonObject();
            return jsonObject.get("project_id").getAsString();
        } catch (Exception e) {
            System.err.println("Erro ao ler o Project ID do arquivo de credenciais: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
