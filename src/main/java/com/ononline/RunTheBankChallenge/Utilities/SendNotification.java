package com.ononline.RunTheBankChallenge.Utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ononline.RunTheBankChallenge.Data.Entities.Transacao;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Classe utilitária para enviar notificações relacionadas a transações.
 */
public class SendNotification {
    
    private static final String API_ENDPOINT = "https://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3";
    
    
    // Mapper usado para gerar String Json de POJOs
    private static final ObjectMapper mapper = new ObjectMapper();
    
    /**
     * Envia uma notificação de débito para o endpoint de API especificado.
     * @param transacao A transação associada ao débito.
     */
    public static void ofDebit(Transacao transacao){
        
        // Envia a requisição POST
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_ENDPOINT))
                    .method("POST", HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(transacao)))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Envia uma notificação de crédito para o endpoint de API especificado.
     * @param transacao A transação associada ao crédito.
     */
    public static void ofCredit(Transacao transacao){
        
        // Envia a requisição POST
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_ENDPOINT))
                    .method("POST", HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(transacao)))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
