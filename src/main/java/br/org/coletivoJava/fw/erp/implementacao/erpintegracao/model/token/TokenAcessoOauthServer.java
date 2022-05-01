/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.TokenDeAcessoExternoDinamico;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.Json;
import java.util.Date;

/**
 *
 * @author sfurbino
 */
public class TokenAcessoOauthServer extends TokenDeAcessoExternoDinamico {

    private JsonObject objetoJsonArmazenamento;
    private JsonObject objetoJsonResposta;
    private final String refresh_token;
    private final String scope;
    private final String client_id;

    public TokenAcessoOauthServer(String pCodigoToken, String pRefresh_token, Date pDataHoraExipira, String hashChavePuvlica_client_id, String pIdentigicadorAgente) {
        super(pCodigoToken, pDataHoraExipira);
        refresh_token = pRefresh_token;
        scope = pIdentigicadorAgente;
        client_id = hashChavePuvlica_client_id;
    }

    private static Date getDataHoraExpiraToken(JsonObject pJson) {
        String pTimeStamp = pJson.getString("dataHoraExpirarToken");
        long longTimeStamp = Long.parseLong(pTimeStamp);
        return new Date(longTimeStamp);
    }

    public TokenAcessoOauthServer(JsonObject pObjetoJsonArmazenamento) {

        super(pObjetoJsonArmazenamento.getString("access_token"), getDataHoraExpiraToken(pObjetoJsonArmazenamento));
        client_id = pObjetoJsonArmazenamento.getString("client_id");

        scope = pObjetoJsonArmazenamento.getString("scope");
        refresh_token = pObjetoJsonArmazenamento.getString("refresh_token");
    }

    public JsonObject getComoJsonResposta() {
        if (objetoJsonResposta == null) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        }
        return objetoJsonResposta;
    }

    public JsonObject getObjetoJsonArmazenamento() {
        if (objetoJsonArmazenamento == null) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("client_id", client_id);
            objectBuilder.add("refresh_token", refresh_token);
            objectBuilder.add("access_token", getToken());
            objectBuilder.add("dataHoraExpirarToken", Long.toString(getDataHoraExpira().getTime()));
            objectBuilder.add("scope", SBCore.getUsuarioLogado().getEmail());
            objetoJsonArmazenamento = objectBuilder.build();
        }

        return objetoJsonArmazenamento;

    }

    public JsonObject getObjetoJsonResposta() {
        return objetoJsonResposta;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public String getClient_id() {
        return client_id;
    }

}
