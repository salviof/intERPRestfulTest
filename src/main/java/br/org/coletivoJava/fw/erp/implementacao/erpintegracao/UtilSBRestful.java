/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPAtual;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenAcessoOauthServer;
import static br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP.ServletRestfullERP.SLUGPUBLICACAOSERVLET;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreReflexao;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreReflexaoObjeto;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringsMaiuculoMinusculo;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.ItfMensagem;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author sfurbino
 */
public class UtilSBRestful {

    public static String getCorpoRequisicao(HttpServletRequest pRequest) {
        String corpo;
        try {

            corpo = pRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            return corpo;
        } catch (IOException ex) {
            return null;
        }

    }

    public static String getCodigoEntidade(HttpServletRequest pRequest) {
        String caminhoChamada = pRequest.getRequestURI();

        String[] partes = caminhoChamada.split("/");
        if (partes.length >= 4) {
            String slugAcao = partes[3];
            return slugAcao;
        }
        return null;
    }

    public static String getAtributoEntidade(HttpServletRequest pRequest) {

        return null;
    }

    public static String getNomeSlugAcao(HttpServletRequest pRequest) {
        String caminhoChamada = pRequest.getRequestURI();

        String[] partes = caminhoChamada.split("/");
        if (partes.length >= 3) {
            String slugAcao = partes[2];
            return slugAcao;
        }
        return null;
    }

    //(String pMetodoRestful, String pHashIdentificadorCliente,
    //String pNomeUnicoAcao, ItfUsuario pUsuario, String pCodigo, String pAtributo, JsonObject pParametros)
    public static SolicitacaoControllerERP getSolicitacaoAcaoController(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            ItfAcaoDoSistema pAcaoSistema, ItfBeanSimples pBeanSimples) {
        ItfIntegracaoERP erpIntegracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        String acao = null;
        if (pAcaoSistema != null) {
            acao = pAcaoSistema.getNomeUnico();
        }

        return getSolicitacaoAcaoController(pCliente, pServico, acao, pBeanSimples);
    }

    public static SolicitacaoControllerERP getSolicitacaoAcaoController(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao, ItfBeanSimples pBeanSimples) {
        ItfIntegracaoERP erpIntegracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

        if (pNomeUnicoAcao == null) {
            throw new UnsupportedOperationException("A ação só pode ser nula com o tipo de solicitação options");
        }
        if (!pNomeUnicoAcao.contains("_CTR_")) {
            throw new UnsupportedOperationException("A ação " + pNomeUnicoAcao + " não parece ser do tipo controller");
        }
        String codigoBeanSimples = null;
        if (pBeanSimples != null) {
            codigoBeanSimples = String.valueOf(pBeanSimples.getId());
        }
        SolicitacaoControllerERP novaSolicitacao = new SolicitacaoControllerERP(
                FabTipoSolicitacaoRestfull.CONTROLLER.getMetodo(),
                pServico.getHashChavePublica(),
                pCliente.getHashChavePublica(),
                pNomeUnicoAcao, SBCore.getUsuarioLogado(), codigoBeanSimples,
                null,
                erpIntegracao.gerarConversaoObjetoToJson(pServico, pBeanSimples));

        return novaSolicitacao;
    }

    public static SolicitacaoControllerERP getSolicitacaoOption(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao) {
        if (!UtilSBCoreStringValidador.isNuloOuEmbranco(pNomeUnicoAcao)) {
            if (!pNomeUnicoAcao.contains("_MB_")) {
                throw new UnsupportedOperationException("A ação " + pNomeUnicoAcao + " não parece ser do tipo Gestão ManagedBean");
            }
        }
        ItfIntegracaoERP erpIntegracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        SolicitacaoControllerERP novaSolicitacao = new SolicitacaoControllerERP(
                FabTipoSolicitacaoRestfull.OPCOES.getMetodo(),
                pServico.getHashChavePublica(),
                pCliente.getHashChavePublica(),
                pNomeUnicoAcao, SBCore.getUsuarioLogado(), null,
                null,
                null);
        return novaSolicitacao;
    }

    public static SolicitacaoControllerERP getSolicitacaoInserirPost(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao) {
        throw new UnsupportedOperationException("A solicitação post não foi implementada");
    }

    public static SolicitacaoControllerERP getSolicitacaoAtualizarPut(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao) {
        throw new UnsupportedOperationException("A solicitação put não foi implementada");
    }

    public static SolicitacaoControllerERP getSolicitacaoExcluirDelete(ItfSistemaERP pCliente, ItfSistemaERP pServico,
            String pNomeUnicoAcao) {
        throw new UnsupportedOperationException("A solicitação put não foi implementada");
    }

    public static SolicitacaoControllerERP getSolicitacaoByRequest(HttpServletRequest pRequest) throws ErroTentandoObterTokenAcesso {
        TokenAcessoOauthServer token = null;
        ItfUsuario usuario = null;
        String acaoDoSistemaEnum = null;
        String codigoEntidade = getCodigoEntidade(pRequest);
        String atributoEntidade = null;
        JsonObject json = null;
        try {
            token = getTokenAcesso(pRequest);
            usuario = getUsuario(token);
        } catch (Throwable t) {
            throw new ErroTentandoObterTokenAcesso("Token de acesso não identificado, certificque a presença do  header Authorization com o valor: Bearer xxxxxmeutokenAlfanumericoregistradoxxxx");
        }
        if (usuario == null) {
            throw new ErroTentandoObterTokenAcesso("Token de acesso não identificado, certificque a presença do  header Authorization com o valor: Bearer xxxxxmeutokenAlfanumericoregistradoxxxx");
        }
        if (!token.isTokenValido()) {
            throw new ErroTentandoObterTokenAcesso("Token de acesso expirou");
        }
        acaoDoSistemaEnum = getNomeSlugAcao(pRequest);
        token.getObjetoJsonResposta();
        String corpoRequisicaoTesxto = getCorpoRequisicao(pRequest);
        if (!UtilSBCoreStringValidador.isNuloOuEmbranco(corpoRequisicaoTesxto)) {
            json = UtilSBCoreJson.getJsonObjectByTexto(corpoRequisicaoTesxto);
        }

        ItfIntegracaoERP servicoRestful = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        ItfSistemaERPAtual servidor = servicoRestful.getSistemaAtual();

        SolicitacaoControllerERP solicitacao = new SolicitacaoControllerERP(
                pRequest.getMethod(),
                servidor.getHashChavePublica(),
                token.getClient_id(), acaoDoSistemaEnum, usuario, codigoEntidade, atributoEntidade, json);

        return solicitacao;

    }

    private static TokenAcessoOauthServer getTokenAcesso(HttpServletRequest pSolicitacao) throws ErroTentandoObterTokenAcesso {

        String autorizacaoWrap = pSolicitacao.getHeader("Authorization");
        String autorizacao = autorizacaoWrap.replace("Bearer ", "");
        TokenAcessoOauthServer dadosToken = MapaTokensGerenciadosConcessaoOauth.loadTokenExistente(autorizacao);
        return dadosToken;
    }

    private static ItfUsuario getUsuario(TokenAcessoOauthServer pToken) throws ErroTentandoObterTokenAcesso {

        String scopo = pToken.getScope();
        ItfUsuario usuario = SBCore.getServicoPermissao().getUsuarioByEmail(scopo);
        return usuario;

    }

    public static String buildTextoJsonResposta(ItfRespostaAcaoDoSistema resposta) {
        ItfIntegracaoERP erpIntegracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        JsonArrayBuilder mensagens = Json.createArrayBuilder();
        for (ItfMensagem msg : resposta.getMensagens()) {
            JsonObjectBuilder msgJsonBuilder = Json.createObjectBuilder();
            msgJsonBuilder.add("tipoMensagem", msg.getTipoDeMensagem().name());
            msgJsonBuilder.add("tipoAgente", msg.getTipoDeMensagem().name());
            msgJsonBuilder.add("mensagem", msg.getMenssagem());
            mensagens.add(msgJsonBuilder.build());
        }

        JsonObjectBuilder jsonRespconstrutor = Json.createObjectBuilder();
        jsonRespconstrutor.add("resultado", resposta.getResultado().name());
        jsonRespconstrutor.add("sucesso", resposta.isSucesso());
        jsonRespconstrutor.add("mensagem", mensagens);

        if (resposta.getRetorno() instanceof JsonObject) {
            jsonRespconstrutor.add("retorno", (JsonObject) resposta.getRetorno());
        } else if (resposta.getRetorno() instanceof ItfBeanSimples) {
            JsonObject retorno = erpIntegracao.gerarConversaoObjetoToJson((ItfBeanSimples) resposta.getRetorno());
            jsonRespconstrutor.add("retorno", retorno);
        } else {
            jsonRespconstrutor.add("retorno", "");
        }

        if (UtilSBCoreStringValidador.isNuloOuEmbranco(resposta.getUrlDestino())) {
            jsonRespconstrutor.add("urlDestino", JsonValue.NULL);
        } else {
            jsonRespconstrutor.add(SLUGPUBLICACAOSERVLET, BigDecimal.ONE);
        }

        String respostaTexto = UtilSBCoreJson.getTextoByJsonObjeect(jsonRespconstrutor.build());
        return respostaTexto;

    }

}
