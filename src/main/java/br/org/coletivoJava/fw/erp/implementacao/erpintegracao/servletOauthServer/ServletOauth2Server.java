/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPAtual;

import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.ErroTentandoObterTokenAcesso;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.MapaTokensGerenciadosConcessaoOauth;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenAcessoOauthServer;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token.TokenConcessaoOauthServer;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreCriptoRSA;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreDataHora;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.json.ErroProcessandoJson;
import com.super_bits.modulosSB.SBCore.modulos.Controller.qualificadoresCDI.sessao.QlSessaoFacesContext;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfSessao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import com.super_bits.modulosSB.webPaginas.controller.servletes.urls.UrlInterpretada;
import com.super_bits.modulosSB.webPaginas.controller.servletes.util.UtilFabUrlServlet;
import jakarta.json.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.Date;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coletivojava.fw.api.tratamentoErros.FabErro;
import org.json.JSONException;

/**
 *
 *
 * https://datatracker.ietf.org/doc/html/rfc6749
 *
 * @author sfurbino
 */
public class ServletOauth2Server extends HttpServlet implements Serializable {

    public static final String SLUGPUBLICACAOSERVLET = "OAUTH2_SERVICE";
    private static final ItfIntegracaoERP integracaoEntreSistemas = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();

    @Inject
    @QlSessaoFacesContext
    private ItfSessao sessaoAtual;

    @Override
    public void doGet(HttpServletRequest requisicao, HttpServletResponse resp) throws ServletException, IOException {

        UrlInterpretada parametrosDeUrl;
        try {

            parametrosDeUrl = UtilFabUrlServlet.getUrlInterpretada(FabUrlOauth2Server.class, requisicao);

        } catch (Throwable t) {
            resp.getWriter().append("PARAMETROS DE ACESSO INCORRETOS, VERIFIQUE A DOCUMENTAÇÃO DA CLASSE " + FabUrlOauth2Server.class.getSimpleName());
            return;
        }

        //FacesContext contexto = FacesContext.getCurrentInstance();
        String hashChave = parametrosDeUrl.getValorComoString(FabUrlOauth2Server.CHAVE_PUBLICA_ID_CLIENTE);
        ItfSistemaERP sistemaCliente = integracaoEntreSistemas.getSistemaByHashChavePublica(hashChave);
        if (sistemaCliente == null) {
            resp.getWriter().append("ACESSO NEGADO, A CHAVE PÚBLICA DO CLIENTE NÃO FOI REGISTRADA");
            return;
        }
        // PODE VALIDAR TAMBÉM A CHAVE PÚBLICA DO SERVIDOR, POR MOTIVOS DE COMPATIBILIDADE DE TESTES FOI REMOVIDA A VALIDAÇÃO
        //Verifica se a origem é vinda do dominio do sistema solicitante
        String dominioDoSistemaClienteResgistrado = sistemaCliente.getDominio();
        String dominioDaRequisicao = requisicao.getHeader("referer");

        if (SBCore.isEmModoProducao() && (dominioDaRequisicao == null || !dominioDaRequisicao.contains(dominioDoSistemaClienteResgistrado))) {
            if (dominioDoSistemaClienteResgistrado.contains("localhost")) {

            } else {
                resp.getWriter().append("ACESSO NEGADO, A ORIGEM DA REQUISIÇÃO DIVERGE DA ORIEM AUTORIZADA [" + dominioDaRequisicao + "|" + dominioDoSistemaClienteResgistrado + "]");
                return;
            }
        }

        String emailDoEscopo = parametrosDeUrl.getValorComoString(FabUrlOauth2Server.ESCOPO);

        ItfUsuario pUsuario = SBCore.getServicoPermissao().getUsuarioByEmail(emailDoEscopo);

        if (emailDoEscopo == null) {
            resp.getWriter().append("ACESSO NEGADO IMPOSSÍVEL RECONHECER O USUÁRIO VERIFIQUE SUA CHAVE PRIVADA");
            return;
        }

        if (pUsuario == null) {
            resp.getWriter().append("ACESSO NEGADO O USUÁRIO " + emailDoEscopo + " NÃO FOI ENCONTRADO NO SISTEMA");
            return;
        }
        TipoRequisicaoOauth tipoRequisicao = (TipoRequisicaoOauth) parametrosDeUrl.getValorComoBeanSimples(FabUrlOauth2Server.TIPO_REQUISICAO);

        try {
            sessaoAtual.getUsuario();
            System.out.println(sessaoAtual.getUsuario().getEmail());
        } catch (Throwable t) {

        }
        if (SBCore.isEmModoDesenvolvimento()) {
            sessaoAtual = SBCore.getServicoSessao().getSessaoAtual();
        }
        if (!pUsuario.equals(sessaoAtual.getUsuario())) {

            if (sessaoAtual.isIdentificado()) {

                sessaoAtual.encerrarSessao();
            }
            if (SBCore.isEmModoDesenvolvimento()) {
                resp.getWriter().append("EFETUE LOGIN DE FORMA PROGRAMÁTICA,POR PADRÃO O SERVIÇO CDI QUE GERE OS BEANS DE SESSÃO NÃO É ATIVADO NO MODO TESTES");
            } else {
                RequestDispatcher despachadorDeRespostaParaRequisicao = requisicao
                        .getRequestDispatcher("/resources/oauth/login.xhtml?hashChavePublicaAplicacaoSolicitante=" + sistemaCliente.getHashChavePublica() + "&scopo=" + pUsuario.getEmail());

                despachadorDeRespostaParaRequisicao.forward(requisicao, resp);

                return;
            }
        }
        switch (tipoRequisicao.getEnumVinculado()) {
            case OBTER_CODIGO_DE_CONCESSAO_DE_ACESSO:

                TokenConcessaoOauthServer tokenConcessaodeAcesso = MapaTokensGerenciadosConcessaoOauth.gerarNovoTokenCocessaoDeAcesso(sistemaCliente, pUsuario);

                if (tokenConcessaodeAcesso == null) {
                    throw new ServletException("Falha gerando código de concessao do token de acesso");
                } else {
                    tokenConcessaodeAcesso = MapaTokensGerenciadosConcessaoOauth.gerarNovoTokenCocessaoDeAcesso(sistemaCliente, pUsuario);
                    String url = URLDecoder.decode(parametrosDeUrl.getValorComoString(FabUrlOauth2Server.REDIRECT_URI));
                    ItfIntegracaoERP erp = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto();
                    ItfSistemaERPAtual servidor = erp.getSistemaAtual();
                    url = url + "?code=" + tokenConcessaodeAcesso.getToken() + "&tipoAplicacao=" + servidor.getHashChavePublica();
                    resp.getWriter().append("<script> windows.location='" + url + "'</script>");
                    resp.sendRedirect(url);
                }

                break;
            case OBTER_CODIGO_DE_AUTORIZACAO:
                throw new ServletException("Utilize o método post ");

            case VERIFICACAO_STATUS_ACESSO:
                TokenAcessoOauthServer token = MapaTokensGerenciadosConcessaoOauth.loadTokenExistente(sistemaCliente, pUsuario);
                if (token.isTokenValido()) {
                    resp.getWriter().append("OK");
                } else {
                    resp.getWriter().append("SEM TOKEN");
                }
                break;
            default:
                throw new AssertionError(tipoRequisicao.getEnumVinculado().name());

        }

    }

    @Override
    public void doPost(HttpServletRequest requisicao, HttpServletResponse resp) throws ServletException, IOException {
        UrlInterpretada parametrosDeUrl;
        try {

            parametrosDeUrl = UtilFabUrlServlet.getUrlInterpretada(FabUrlOauth2Server.class, requisicao);

        } catch (Throwable t) {
            resp.getWriter().append("PARAMETROS DE ACESSO INCORRETOS, VERIFIQUE A DOCUMENTAÇÃO DA CLASSE " + FabUrlOauth2Server.class.getSimpleName());
            return;
        }
        String hashChave = parametrosDeUrl.getValorComoString(FabUrlOauth2Server.CHAVE_PUBLICA_ID_CLIENTE);
        ItfSistemaERP sistemaCliente = integracaoEntreSistemas.getSistemaByHashChavePublica(hashChave);
        if (sistemaCliente == null) {
            resp.getWriter().append("ACESSO NEGADO, A CHAVE PÚBLICA DO CLIENTE NÃO FOI REGISTRADA");
            return;
        }
        // PODE VALIDAR TAMBÉM A CHAVE PÚBLICA DO SERVIDOR, POR MOTIVOS DE COMPATIBILIDADE DE TESTES FOI REMOVIDA A VALIDAÇÃO

        //Verifica se a origem é vinda do dominio do sistema solicitante
        String dominioDoSistema = sistemaCliente.getDominio();
        String dominioDaRequisicao = requisicao.getHeader("origin");

        //todo origem explícita, analizar nescessidade já que o post não vem do browser
        if (false) {
            if (dominioDaRequisicao == null || !dominioDaRequisicao.contains(dominioDoSistema)) {
                resp.getWriter().append("ACESSO NEGADO, A ORIGEM DA REQUISIÇÃO DIVERGE DA ORIEM AUTORIZADA [" + dominioDaRequisicao + "|" + dominioDoSistema + "]");
                return;
            }
        }
        String emailDoEscopo = parametrosDeUrl.getValorComoString(FabUrlOauth2Server.ESCOPO);

        ItfUsuario pUsuario = SBCore.getServicoPermissao().getUsuarioByEmail(emailDoEscopo);

        if (emailDoEscopo == null) {
            resp.getWriter().append("ACESSO NEGADO IMPOSSÍVEL RECONHECER O USUÁRIO VERIFIQUE SUA CHAVE PRIVADA");
            return;
        }

        if (pUsuario == null) {
            resp.getWriter().append("ACESSO NEGADO O USUÁRIO " + emailDoEscopo + " NÃO FOI ENCONTRADO NO SISTEMA");
            return;
        }
        //String codigoCriptografado = JsonReader jsonReader = Json.createReader(new StringReader(jsonSTR));
        //UtilSBCoreCriptoRSA.getTextoDescriptografado(, sistemaCliente.getChavePublica());

        StringBuffer jb = new StringBuffer();
        String line = null;
        String codigoCriptografado = null;
        try {
            BufferedReader reader = requisicao.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            /*report an error*/ }

        try {
            JsonObject jsonObject = UtilSBCoreJson.getJsonObjectByTexto(jb.toString());
            codigoCriptografado = jsonObject.getString("code");
        } catch (JSONException e) {
            // crash and burn
            throw new ServletException("Erro lendo Conteúdo Json do post");
        }
        if (codigoCriptografado == null) {
            throw new ServletException("Error parsing JSON request string");
        }
        ItfSistemaERPAtual sistemaAtual = ERPIntegracaoSistemasApi.RESTFUL.getImplementacaoDoContexto().getSistemaAtual();
        String codigoDescriptografado = UtilSBCoreCriptoRSA.getTextoDescriptografadoUsandoChavePrivada(codigoCriptografado, sistemaAtual.getChavePrivada());
        try {
            TokenConcessaoOauthServer tokenConcessao = MapaTokensGerenciadosConcessaoOauth.loadTokenConcessaoExistente(sistemaCliente, codigoDescriptografado);
            if (tokenConcessao.isTokenValido()) {
                TokenAcessoOauthServer novoToken = MapaTokensGerenciadosConcessaoOauth
                        .gerarNovoTokenDeAcesso(tokenConcessao.getToken(), sistemaCliente.getHashChavePublica(),
                                emailDoEscopo);
                Integer segudos = UtilSBCoreDataHora.segundosEntre(new Date(), novoToken.getDataHoraExpira());
                JsonObject tokenJson = UtilSBCoreJson
                        .getJsonObjectBySequenciaChaveValor("access_token", novoToken.getToken(),
                                "token_type", "Bearer",
                                "scope", tokenConcessao.getIdentificadorUsuario(),
                                "expires_in", segudos.toString(),
                                "refresh_token", novoToken.getRefresh_token()
                        );

                PrintWriter writer = resp.getWriter();
                String textoJson = UtilSBCoreJson.getTextoByJsonObjeect(tokenJson.asJsonObject());
                writer.append(textoJson);
            }
        } catch (ErroTentandoObterTokenAcesso ex) {
            throw new ServletException("O token não foi encontrado");
        } catch (ErroProcessandoJson ex) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro criando json de resposta com token de acesso", ex);
        }

    }

}
