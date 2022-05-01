/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.ErroTentandoObterTokenAcesso;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.UtilSBRestful;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaAcoesSistema;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.DTO_SBGENERICO;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.UtilSBController;
import com.super_bits.modulosSB.SBCore.modulos.Controller.comunicacao.RespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.qualificadoresCDI.sessao.QlSessaoFacesContext;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfSessao;
import com.super_bits.modulosSB.SBCore.modulos.servicosCore.ItfServicoControllerExecucao;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sfurbino
 */
public class ServletRestfullERP extends HttpServlet implements Serializable {

    public static final String SLUGPUBLICACAOSERVLET = "acoesRestful";
    private static ItfIntegracaoERP erpIntegraca = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

    @Inject
    @QlSessaoFacesContext
    private ItfSessao sessaoAtual;

    @Inject
    private ItfServicoControllerExecucao entregaJson;

    private void processarSolicitacao(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SolicitacaoControllerERP solicitacao;
        try {
            solicitacao = UtilSBRestful.getSolicitacaoByRequest(req);

            if (solicitacao != null && solicitacao.getUsuarioSolicitante() != null) {
                sessaoAtual.setUsuario(solicitacao.getUsuarioSolicitante());
            }
            if (!solicitacao.getMetodo().equals("OPTIONS")) {
                if (solicitacao.getAcaoStrNomeUnico() == null) {
                    RespostaAcaoDoSistema resposta = new RespostaAcaoDoSistema();
                    resposta.addErro("A ação do sistema não foi enviada");
                    resp.setStatus(503);
                    String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
                    resp.getWriter().append(respostaStr);
                    return;
                }

                ItfAcaoDoSistema acao = MapaAcoesSistema.getAcaoDoSistemaByNomeUnico(solicitacao.getAcaoStrNomeUnico());
                if (acao == null) {
                    RespostaAcaoDoSistema resposta = new RespostaAcaoDoSistema();
                    resposta.addErro("A ação " + solicitacao.getAcaoStrNomeUnico() + " não foi encontrata");
                    resp.setStatus(503);
                    String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
                    resp.getWriter().append(respostaStr);
                    return;
                }

            }
        } catch (ErroTentandoObterTokenAcesso ex) {
            RespostaAcaoDoSistema resposta = new RespostaAcaoDoSistema();
            resposta.addErro("Autenticação negada ");
            resp.setStatus(401);
            String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
            resp.getWriter().append(respostaStr);
            return;
        }

        RequestDispatcher despachadorDeRespostaParaRequisicao = req.getRequestDispatcher("/resources/restful/respostaController.xhtml");
        req.setAttribute("solicitacao", solicitacao);
        despachadorDeRespostaParaRequisicao.forward(req, resp);

    }

    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processarSolicitacao(req, resp);

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processarSolicitacao(req, resp);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processarSolicitacao(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processarSolicitacao(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processarSolicitacao(req, resp);
    }

}
