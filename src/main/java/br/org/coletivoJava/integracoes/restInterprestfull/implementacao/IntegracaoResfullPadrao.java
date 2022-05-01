/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.restInterprestfull.implementacao;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.UtilSBRestful;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.FabMensagens;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sfurbino
 */
public class IntegracaoResfullPadrao extends
        AcaoApiIntegracaoComOauthAbstrato {

    private SolicitacaoControllerERP solicitacao;
    private ItfSistemaERP dadosServico;

    public IntegracaoResfullPadrao(String pTipoApicacao, ItfFabricaIntegracaoRest pIntegracaoEndpoint, FabTipoAgenteClienteApi pTipoAgente, ItfUsuario pUsuario, Object... pParametros) {
        super(pTipoApicacao, pIntegracaoEndpoint,
                pTipoAgente, pUsuario, pParametros);
    }

    @Override
    public String gerarUrlRequisicao() {
        StringBuilder urlReqBuild = new StringBuilder();
        String urlBaseLegado = super.gerarUrlRequisicao();

        String urlBase = getDadoServico().getUrlPublicaEndPoint();

        urlReqBuild.append(urlBase);
        if (!urlBase.endsWith("/")) {
            urlReqBuild.append("/");
        }
        if (!UtilSBCoreStringValidador.isNuloOuEmbranco(getSoliciatacao().getAcaoStrNomeUnico())) {
            urlReqBuild.append(solicitacao.getAcaoStrNomeUnico());
            urlReqBuild.append("/");
        }
        if (!UtilSBCoreStringValidador.isNuloOuEmbranco(solicitacao.getCodigoEntidade())) {
            urlReqBuild.append(solicitacao.getCodigoEntidade());
            urlReqBuild.append("/");
        }
        if (!UtilSBCoreStringValidador.isNuloOuEmbranco(solicitacao.getAtributoEntidade())) {
            urlReqBuild.append(solicitacao.getAtributoEntidade());
            urlReqBuild.append("/");
        }

        return urlReqBuild.toString();
    }

    public ItfSistemaERP getDadoServico() {
        if (dadosServico == null) {
            ItfIntegracaoERP integracao = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
            dadosServico = integracao.getSistemaByHashChavePublica(getIdTipoAplicacao());

        }
        return dadosServico;
    }

    public SolicitacaoControllerERP getSoliciatacao() {

        if (solicitacao == null) {
            if (!(parametros[0] instanceof SolicitacaoControllerERP)) {
                throw new UnsupportedOperationException("O único parâmetro aceitável é  " + SolicitacaoControllerERP.class.getSimpleName() + " utlize a classe " + UtilSBRestful.class.getSimpleName() + " para instanciar um parâmetro");
            }

            solicitacao = (SolicitacaoControllerERP) parametros[0];
        }
        return solicitacao;
    }

    @Override
    public String gerarCorpoRequisicao() {
        return getSoliciatacao().getCorpoParametros();
    }

    @Override
    protected RespostaWebServiceSimples buildResposta(RespostaWebServiceSimples pRespostaWSSemTratamento) {
        if (!UtilSBCoreStringValidador.isNuloOuEmbranco(pRespostaWSSemTratamento.getRetorno())) {
            pRespostaWSSemTratamento.getMensagens();
            JsonObject respostaJson = UtilSBCoreJson.getJsonObjectByTexto(pRespostaWSSemTratamento.getRetorno().toString());
            if (respostaJson.containsKey("resultado")) {

                String resultado = respostaJson.getString("resultado");
                JsonArray mensagensJson = respostaJson.getJsonArray("mensagem");
                mensagensJson.stream().forEach(msg -> {
                    JsonObject mensagemJson = msg.asJsonObject();
                    String tipo = mensagemJson.getString("tipoMensagem");
                    if (tipo.equals(FabMensagens.ERRO.toString())
                            || tipo.equals(FabMensagens.ERRO_FATAL.toString())) {
                        pRespostaWSSemTratamento.addErro(msg.asJsonObject().getString("mensagem"));
                    } else {
                        pRespostaWSSemTratamento.addAviso(msg.asJsonObject().getString("mensagem"));

                    }

                });

            }
            System.out.println(pRespostaWSSemTratamento.getResultado());

        }
        return pRespostaWSSemTratamento;

    }

}
