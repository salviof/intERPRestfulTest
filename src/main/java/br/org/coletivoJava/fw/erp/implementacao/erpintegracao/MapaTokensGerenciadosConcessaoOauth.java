/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenAcessoOauthServer;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenConcessaoOauthServer;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreDataHora;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringGerador;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.MapaTokensGerenciados;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sfurbino
 */
public class MapaTokensGerenciadosConcessaoOauth extends MapaTokensGerenciados {

    private static final Map<String, Map<String, TokenConcessaoOauthServer>> TOKENS_DE_CONSESSAO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL = new HashMap<>();
    private static final Map<String, Map<String, TokenAcessoOauthServer>> TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL = new HashMap<>();
    private static final Map<String, TokenConcessaoOauthServer> TOKEN_DE_CONCESSAO_BY_CODIGO = new HashMap<>();
    private static final Map<String, TokenAcessoOauthServer> TOKEN_DE_ACESSO_BY_CODIGO = new HashMap<>();
    private static final Map<String, TokenAcessoOauthServer> TOKEN_DE_ACESSO_BY_CODIGO_ATUALIZACAO = new HashMap<>();

    private static final ConfigModulo chavesDeAcessoErrp = SBCore.getConfigModulo(FabConfigModuloWebERPChaves.class);

    public static void loadTokensPersistidos(ItfSistemaERP pSistema) {

        String chavesPersistidasTExto = chavesDeAcessoErrp.getRepositorioDeArquivosExternos().getTexto(pSistema.getChavePublica());
        List<TokenAcessoOauthServer> tokensValidos = new ArrayList<>();
        if (!TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.containsKey(pSistema.getChavePublica())) {
            TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.put(pSistema.getChavePublica(), new HashMap<>());
        }

        if (!UtilSBCoreStringValidador.isNuloOuEmbranco(chavesPersistidasTExto)) {

            JsonReader jsonReader = Json.createReader(new StringReader(chavesPersistidasTExto));
            JsonArray array = jsonReader.readArray();
            jsonReader.close();

            for (JsonValue json : array) {
                TokenAcessoOauthServer token = new TokenAcessoOauthServer(json.asJsonObject());
                if (token.isTokenValido()) {
                    tokensValidos.add(token);
                }
            }
        }
        for (TokenAcessoOauthServer token : tokensValidos) {
            TOKEN_DE_ACESSO_BY_CODIGO.put(token.getToken(), token);
            TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.get(pSistema.getChavePublica()).put(token.getToken(), TOKEN_DE_ACESSO_BY_CODIGO.get(token.getToken()));
        }
    }

    public static TokenConcessaoOauthServer loadTokenConcessaoExistente(ItfSistemaERP pSistema, String pCodigo) throws ErroTentandoObterTokenAcesso {
        TokenConcessaoOauthServer tokenConcessao = TOKEN_DE_CONCESSAO_BY_CODIGO.get(pCodigo);
        if (tokenConcessao == null) {
            throw new ErroTentandoObterTokenAcesso("O Código " + pCodigo + " não foi encontrado");
        }
        if (!tokenConcessao.getChavePublicaAplicacaoConfiavel().equals(pSistema.getChavePublica())) {
            throw new ErroTentandoObterTokenAcesso("O código pertence a outro sistema");
        }
        if (new Date().getTime() >= tokenConcessao.getDataHoraExpira().getTime()) {
            throw new ErroTentandoObterTokenAcesso("O código de solicitação de token expirou");
        }
        return tokenConcessao;
    }

    public static TokenAcessoOauthServer loadTokenExistente(String pTokenAcesso) {
        return TOKEN_DE_ACESSO_BY_CODIGO.get(pTokenAcesso);
    }

    public static TokenAcessoOauthServer loadTokenExistente(ItfSistemaERP pSistema, ItfUsuario pUsuario) {
        if (TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.containsKey(pSistema.getChavePublica())) {
            loadTokensPersistidos(pSistema);
        }

        if (!TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.containsKey(pSistema.getChavePublica())) {
            return null;
        }
        TokenAcessoOauthServer token
                = TOKENS_DE_ACCESSO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.get(pSistema.getChavePublica()).get(pUsuario.getEmail());
        return token;

    }

    public static TokenConcessaoOauthServer gerarNovoTokenCocessaoDeAcesso(ItfSistemaERP pSistema, ItfUsuario pUsuario) {
        String tokenConcessao = UtilSBCoreStringGerador.getStringRandomicaTokenAleatorio();
        Date dataExpiracao = UtilSBCoreDataHora.incrementaSegundos(new Date(), 300);

        TokenConcessaoOauthServer tokenDinamico = new TokenConcessaoOauthServer(tokenConcessao, dataExpiracao, pSistema.getChavePublica(), pUsuario.getEmail());
        if (!TOKENS_DE_CONSESSAO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.containsKey(pSistema.getChavePublica())) {
            TOKENS_DE_CONSESSAO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.put(pSistema.getChavePublica(), new HashMap<>());
        }
        TOKENS_DE_CONSESSAO_BY_CHAVE_PUBLICA_SISTEMA_CONFIAVEL.get(pSistema.getChavePublica()).put(pUsuario.getEmail(), tokenDinamico);
        TOKEN_DE_CONCESSAO_BY_CODIGO.put(tokenConcessao, tokenDinamico);
        return tokenDinamico;
    }

    public static TokenAcessoOauthServer gerarNovoTokenDeAcesso(String pcodigoTokenConsessao, String pClient_id, String pIdentificadorUsuario) {
        TokenConcessaoOauthServer tokenConcessao = TOKEN_DE_CONCESSAO_BY_CODIGO.get(pcodigoTokenConsessao);
        if (tokenConcessao == null) {
            return null;
        }
        String codigoTokenDeAcesso = UtilSBCoreStringGerador.getStringRandomicaTokenAleatorio(30);
        String codigoRefresh = UtilSBCoreStringGerador.getStringRandomicaTokenAleatorio(30);
        Date dataExpiracao = UtilSBCoreDataHora.incrementaSegundos(new Date(), 3000);
        TokenAcessoOauthServer novoToken = new TokenAcessoOauthServer(codigoTokenDeAcesso, codigoRefresh, dataExpiracao, pClient_id, pIdentificadorUsuario);
        if (!persistirToken(pClient_id, novoToken)) {
            throw new UnsupportedOperationException("falha persistindo token");
        }
        return novoToken;
    }

    public synchronized static boolean persistirToken(String pHashClientidentificador, TokenAcessoOauthServer pToken) {
        try {
            String chavesPersistidasTExto = chavesDeAcessoErrp.getRepositorioDeArquivosExternos().getTexto(pHashClientidentificador);
            List<TokenAcessoOauthServer> tokensValidos = new ArrayList<>();
            tokensValidos.add(pToken);
            if (!UtilSBCoreStringValidador.isNuloOuEmbranco(chavesPersistidasTExto)) {
                JsonReader jsonReader = Json.createReader(new StringReader(chavesPersistidasTExto));
                JsonArray array = jsonReader.readArray();
                jsonReader.close();

                for (JsonValue json : array) {
                    TokenAcessoOauthServer token = new TokenAcessoOauthServer(json.asJsonObject());
                    if (token.isTokenValido()) {
                        tokensValidos.add(token);
                    }
                }
            }
            JsonArrayBuilder jsonArraybuilder = Json.createArrayBuilder();
            for (TokenAcessoOauthServer tokenValido : tokensValidos) {
                jsonArraybuilder.add(tokenValido.getObjetoJsonArmazenamento());
            }
            chavesDeAcessoErrp.getRepositorioDeArquivosExternos().putConteudoRecursoExterno(pHashClientidentificador, jsonArraybuilder.build().toString());
            TOKEN_DE_ACESSO_BY_CODIGO.put(pToken.getToken(), pToken);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public static TokenAcessoOauthServer renovarNovoTokenDeAcesso(String pcodigoAtualizacaoToken) {
        TokenAcessoOauthServer tokenEncontrado = TOKEN_DE_ACESSO_BY_CODIGO_ATUALIZACAO.get(pcodigoAtualizacaoToken);
        if (tokenEncontrado == null) {
            return null;
        }
        Date dataHoraLimiteRenovacaoToken = UtilSBCoreDataHora.incrementaDias(tokenEncontrado.getDataHoraExpira(), 5);
        if (dataHoraLimiteRenovacaoToken.getTime() <= new Date().getTime()) {
            return null;
        }
        String sistemaConfiavelChavePublica = tokenEncontrado.getClient_id();
        Date dataExpiracao = UtilSBCoreDataHora.incrementaSegundos(new Date(), 3000);
        String codigoTokenDeAcesso = UtilSBCoreStringGerador.getStringRandomicaTokenAleatorio(30);
        String codigoTokenRefresh = UtilSBCoreStringGerador.getStringRandomicaTokenAleatorio(30);
        TokenAcessoOauthServer novoToken = new TokenAcessoOauthServer(codigoTokenDeAcesso, codigoTokenRefresh, dataExpiracao, sistemaConfiavelChavePublica,
                tokenEncontrado.getClient_id());
        persistirToken(sistemaConfiavelChavePublica, novoToken);
        return novoToken;
    }

}
