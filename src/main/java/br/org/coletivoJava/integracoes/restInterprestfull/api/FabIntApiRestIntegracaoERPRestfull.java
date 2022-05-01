/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.restInterprestfull.api;

import br.org.coletivoJava.fw.api.erp.codigoPostal.br.ERPCodigoPostalBR;
import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.FabConfigModuloWebERPChaves;
import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.ItfFabricaIntegracaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.InfoConsumoRestService;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.FabTipoAutenticacaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.servicoRegistrado.InfoConfigRestClientIntegracao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestao;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.transmissao_recepcao_rest_client.ItfAcaoApiRest;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.importacao.FabTipoArquivoImportacao;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

/**
 *
 * @author sfurbino
 */
@InfoConfigRestClientIntegracao(enderecosDocumentacao = "https://coletivojava.com.br",
        tipoAutenticacao = FabTipoAutenticacaoRest.OAUTHV2,
        nomeIntegracao = "intERPRestfull",
        configuracao = FabConfigModuloWebERPChaves.class
)
public enum FabIntApiRestIntegracaoERPRestfull implements ItfFabricaIntegracaoRest {

    @InfoConsumoRestService(getPachServico = "/oauth2Service/status/{0}",
            tipoConexao = FabTipoConexaoRest.GET,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"chavePublica"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    OAUTH_VALIDAR_CREDENCIAL,
    @InfoConsumoRestService(getPachServico = "/acoesRestful/acao/executar/{0}/{1}",
            tipoConexao = FabTipoConexaoRest.PUT,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"nomeUnicoAcaoGestao", "codigoEntidade"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_CONTROLLER_ENTIDADE_EXISTENTE,
    @InfoConsumoRestService(getPachServico = "/controllerERP/acao/executar/{0}/",
            tipoConexao = FabTipoConexaoRest.POST,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"nomeUnicoAcaoGestao", "codigoEntidade"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_CONTROLLER,
    @InfoConsumoRestService(getPachServico = "/acoesRestful/acaogestao/",
            tipoConexao = FabTipoConexaoRest.POST,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_CRIAR_NOVA_ENTIDADE,
    @InfoConsumoRestService(getPachServico = "/acoesRestful/acaogestao/{0}",
            tipoConexao = FabTipoConexaoRest.PUT,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"idEntidade"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_ATUALIZAR_ENTIDADE,
    @InfoConsumoRestService(getPachServico = "/acoesRestful/acaogestao/{0}",
            tipoConexao = FabTipoConexaoRest.DELET,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            parametrosGet = {"identidade"},
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_EXECUTAR_DELETE,
    @InfoConsumoRestService(getPachServico = "/acoesRestful/acaogestao/",
            tipoConexao = FabTipoConexaoRest.OPTIONS,
            tipoInformacaoRecebida = FabTipoArquivoImportacao.JSON,
            urlDocumentacao = "https://coletivojava.com.br",
            adicionarAutenticacaoBearer = true)
    ACOES_GET_OPCOES,
    ACOES_GET_ESTRUTURA_CAMPOS_FORMULARIO,
    ACOES_GET_LISTA_ENTIDADES,
    ACOES_GET_REGISTRO_ENTIDADE;

    public ItfTokenGestaoOauth getGestaoToken(SolicitacaoControllerERP pSolicitacao) {
        return (ItfTokenGestaoOauth) getGestaoToken(pSolicitacao.getErpServico());
    }

    public ItfAcaoApiRest getAcao(SolicitacaoControllerERP pSolicicatacao) {
        ItfIntegracaoERP resp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
        //pSolicicatacao.getErpServico()
        ItfSistemaERP sistemaSErvidor = resp.getSistemaByHashChavePublica(pSolicicatacao.getErpServico());
        if (sistemaSErvidor == null) {
            throw new UnsupportedOperationException("Hash chave pública do serviço não foi definida");
        }
        return ItfFabricaIntegracaoRest.super.getAcao(SBCore.getUsuarioLogado(), pSolicicatacao);
    }

    public ItfTokenGestaoOauth getGestaoToken(ItfSistemaERP pSistemaServico) {
        return (ItfTokenGestaoOauth) ItfFabricaIntegracaoRest.super.getGestaoToken(SBCore.getUsuarioLogado(), pSistemaServico.getHashChavePublica());
    }

    @Override
    public ItfTokenGestaoOauth getGestaoToken(ItfUsuario pUsuario) {
        throw new UnsupportedOperationException("Informe o sistema chamando get GestaoDeToken(Sistema)");
    }

}
