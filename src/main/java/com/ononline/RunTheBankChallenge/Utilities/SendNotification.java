package com.ononline.RunTheBankChallenge.Utilities;

import com.ononline.RunTheBankChallenge.Data.Entities.Transacao;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * Classe utilitária para enviar notificações relacionadas a transações.
 */
public class SendNotification {
    
    private static final String API_ENDPOINT = "https://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3";
    
    /**
     * Envia uma notificação de débito para o endpoint de API especificado.
     * @param transacao A transação associada ao débito.
     */
    public static void ofDebit(Transacao transacao){
        // Configura os cabeçalhos da requisição
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Configura a entidade da requisição com o objeto Transacao e os cabeçalhos
        HttpEntity<Transacao> requestEntity = new HttpEntity<>(transacao, headers);
        
        // Cria uma instância do RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        
        // Envia a requisição POST e recupera a resposta
        restTemplate.postForEntity(API_ENDPOINT, requestEntity, String.class);
    }
    
    /**
     * Envia uma notificação de crédito para o endpoint de API especificado.
     * @param transacao A transação associada ao crédito.
     */
    public static void ofCredit(Transacao transacao){
        
        // Cria uma instância do RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        
        HttpEntity<Transacao> transacaoHttpEntity = new HttpEntity<>(transacao);
        
        // Envia a requisição POST
        try {
            restTemplate.exchange(API_ENDPOINT, HttpMethod.POST, transacaoHttpEntity, String.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
