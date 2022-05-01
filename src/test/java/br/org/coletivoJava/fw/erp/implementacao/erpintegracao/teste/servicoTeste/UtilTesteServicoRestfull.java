/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.servicoTeste;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.ErroTentandoObterTokenAcesso;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.UtilSBRestful;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletRestfulERP.ServletRestfullERP;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.MapaAcoesSistema;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.acoes.ItfAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.comunicacao.RespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import spark.Spark;

/**
 *
 * @author sfurbino
 */
public class UtilTesteServicoRestfull {

    private static ItfIntegracaoERP erpIntegraca = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

    public static void iniciarServico() {
        Spark.port(8066);
        Spark.get("/hello", (req, res) -> "Hello World");

        Spark.options("/" + ServletRestfullERP.SLUGPUBLICACAOSERVLET + "/*", (req, res) -> {

            SolicitacaoControllerERP solicitacao;
            try {
                solicitacao = UtilSBRestful.getSolicitacaoByRequest(req.raw());
            } catch (ErroTentandoObterTokenAcesso ex) {
                RespostaAcaoDoSistema resposta = new RespostaAcaoDoSistema();
                resposta.addErro("Autenticação negada");
                res.status(401);
                String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
                return respostaStr;
            }
            ItfRespostaAcaoDoSistema resposta = erpIntegraca.gerarRespostaAcaoDoSistemaServico(solicitacao);
            res.status(200);
            String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
            return respostaStr;

        });

        Spark.post("/" + ServletRestfullERP.SLUGPUBLICACAOSERVLET + "/*", (req, res)
                -> {
            SolicitacaoControllerERP solicitacao;
            try {
                solicitacao = UtilSBRestful.getSolicitacaoByRequest(req.raw());
            } catch (ErroTentandoObterTokenAcesso ex) {
                RespostaAcaoDoSistema resposta = new RespostaAcaoDoSistema();
                resposta.addErro("Autenticação negada");
                res.status(401);
                String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);
                return respostaStr;
            }

            ItfRespostaAcaoDoSistema resposta = erpIntegraca.gerarRespostaAcaoDoSistemaServico(solicitacao);
            String respostaStr = UtilSBRestful.buildTextoJsonResposta(resposta);

            if (resposta.isSucesso()) {
                res.status(200);
            } else {
                if (!UtilSBCoreStringValidador.isNuloOuEmbranco(solicitacao.getAcaoStrNomeUnico())) {
                    ItfAcaoDoSistema acao = MapaAcoesSistema.getAcaoDoSistemaByNomeUnico(solicitacao.getAcaoStrNomeUnico());
                    if (!SBCore.getServicoPermissao().isAcaoPermitidaUsuario(solicitacao.getUsuarioSolicitante(), acao)) {
                        res.status(403);

                    }
                } else {
                    res.status(500);
                }
            }

            return respostaStr;
        }
        );

    }
;

}
