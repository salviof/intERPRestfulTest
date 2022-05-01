package br.org.coletivoJava.integracoes.restInterprestfull.implementacao;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;

import br.org.coletivoJava.fw.api.erp.erpintegracao.servico.ItfIntegracaoERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.FabTipoRequisicaoOauthServer;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.ServletOauth2Server;
import br.org.coletivoJava.integracoes.restInterprestfull.api.InfoIntegracaoRestInterprestfullRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreCriptoRSA;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreDataHora;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.FabTipoConexaoRest;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.oauth.InfoTokenOauth2;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenDeAcessoExterno;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.ItfTokenGestaoOauth;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.ChamadaHttpSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.gestaoToken.GestaoTokenOath2Base;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * https://datatracker.ietf.org/doc/html/rfc6749
 *
 * @author sfurbino
 */
@InfoIntegracaoRestInterprestfullRestfull(tipo = FabIntApiRestIntegracaoERPRestfull.OAUTH_VALIDAR_CREDENCIAL)
public class GestaoTokenRestInterprestfull extends GestaoTokenOath2Base implements ItfTokenGestaoOauth {

    private final String urlServidorApiRest;
    private final ERPIntegracaoSistemasApi erpIntegracao = ERPIntegracaoSistemasApi.RESTFUL;
    private final ItfIntegracaoERP integracaoERP = erpIntegracao.getImplementacaoDoContexto();
    private final String urlObterCodigoSolicitacao;
    private final String urlObterToken;
    private final String chavePublicaCliente;
    private final String chavePublicaServidor;
    private final String urlRetornoSucessoObterToken;
    private final String urlRetornoReceberCodigoSolicitao;

    public GestaoTokenRestInterprestfull(
            final FabTipoAgenteClienteApi pTipoAgente, final ItfUsuario pUsuario, String pIdentificadorServico) {
        super(FabIntApiRestIntegracaoERPRestfull.class, pTipoAgente, pUsuario, pIdentificadorServico);
        ItfSistemaERP sistemaServidor = integracaoERP.getSistemaByHashChavePublica(pIdentificadorServico);
        ItfSistemaERP sistemaCliente = integracaoERP.getSistemaAtual();
        if (SBCore.isEmModoDesenvolvimento()) {
            if (sistemaCliente.getHashChavePublica().equals(pIdentificadorServico)) {
                //detectado ambiente de teste
                List<SistemaERPConfiavel> sistemasRegistrados = UtilSBPersistencia.getListaTodos(SistemaERPConfiavel.class);
                if (sistemasRegistrados.size() != 2) {
                    throw new UnsupportedOperationException("para homologar a couminicação ERP usando um sistema unicico como cliente e servidor, devem existir ");
                }
                Optional<SistemaERPConfiavel> sistemaClientePesquisa = sistemasRegistrados.stream().filter(sis -> !sis.getHashChavePublica().equals(pIdentificadorServico)).findFirst();
                sistemaCliente = sistemaClientePesquisa.get();
            }
        }
        chavePublicaServidor = sistemaServidor.getChavePublica();
        String porta = "";
        String protocolo = "https";
        if (!SBCore.isEmModoProducao()) {
            try {
                URL urlTEste = new URL(sistemaServidor.getUrlPublicaEndPoint());
                int port = urlTEste.getPort();
                if (port > 0) {
                    porta = ":" + Integer.valueOf(port);
                }
                protocolo = urlTEste.getProtocol().toLowerCase();

            } catch (MalformedURLException ex) {
                Logger.getLogger(GestaoTokenRestInterprestfull.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        urlServidorApiRest = protocolo + "://" + sistemaServidor.getDominio() + porta;
        chavePublicaCliente = sistemaCliente.getChavePublica();
        urlObterCodigoSolicitacao = urlServidorApiRest + "/" + ServletOauth2Server.SLUGPUBLICACAOSERVLET
                + "/" + FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_CONCESSAO_DE_ACESSO.toString()
                + "/" + sistemaServidor.getChavePublica().hashCode()
                + "/" + chavePublicaCliente.hashCode()
                + "/" + URLEncoder.encode(sistemaCliente.getUrlRecepcaoCodigo())
                + "/" + pUsuario.getEmail();

        urlObterToken = urlServidorApiRest + "/" + ServletOauth2Server.SLUGPUBLICACAOSERVLET
                + "/" + FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_AUTORIZACAO.toString()
                + "/" + sistemaServidor.getHashChavePublica()
                + "/" + sistemaCliente.getHashChavePublica()
                + "/" + URLEncoder.encode(sistemaCliente.getUrlRecepcaoCodigo())
                + "/" + pUsuario.getEmail();
        urlRetornoReceberCodigoSolicitao = sistemaCliente.getUrlRecepcaoCodigo();
        urlRetornoSucessoObterToken = "https://" + sistemaCliente.getDominio();
    }

    @Override
    protected ChamadaHttpSimples gerarChamadaTokenObterChaveAcesso() {

        if (codigoSolicitacao != null) {
            try {

                String codigoCriptogrado = UtilSBCoreCriptoRSA.getTextoCriptografadoUsandoChavePublica(codigoSolicitacao, chavePublicaServidor);
                ChamadaHttpSimples chamada = new ChamadaHttpSimples();
                chamada.setTipoConexao(FabTipoConexaoRest.POST);
                chamada.setEnderecoHost(urlServidorApiRest);
                chamada.setPath(urlObterToken.replace(urlServidorApiRest, ""));
                chamada.setCabecalhos(new HashMap<>());
                JsonObjectBuilder jsonEnvioCodigoAcesso = Json.createObjectBuilder();
                jsonEnvioCodigoAcesso.add("grant_type", FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_AUTORIZACAO.toString());
                jsonEnvioCodigoAcesso.add("client_id", chavePublicaCliente.hashCode());
                jsonEnvioCodigoAcesso.add("code", codigoCriptogrado);
                jsonEnvioCodigoAcesso.add("redirect_uri", urlRetornoSucessoObterToken);
                JsonObject jsonPostSolicitacao = jsonEnvioCodigoAcesso.build();
                chamada.setCorpo(jsonPostSolicitacao.toString());
                chamada.setPossuiCorpoComConteudo(true);
                return chamada;
            } catch (Throwable ex) {
                Logger.getLogger(GestaoTokenRestInterprestfull.class.getName()).log(Level.SEVERE, "Falha gerando chamada para obter token", ex);
                return null;
            }

        }
        return null;
    }

    @Override
    public boolean validarToken() {
        if (getTokenCompleto() == null) {
            return false;
        }
        return getTokenCompleto().isTokenValido();
    }

    @Override
    public ItfTokenDeAcessoExterno extrairToken(org.json.simple.JSONObject pJson) {
        String tk = (String) pJson.get("access_token");
        InfoTokenOauth2 tokenGerado = new InfoTokenOauth2(tk);
        tokenGerado.setTokenRefresh((String) pJson.get("refresh_token"));
        String expiraStr = String.valueOf(pJson.get("dataHoraExpirarToken"));
        tokenGerado.setDataHoraExpirarToken(new Date(Long.valueOf(expiraStr)));
        tokenGerado.setScope((String) pJson.get("scope"));
        return tokenGerado;
    }

    @Override
    public boolean armazenarRespostaToken(String pToken) {

        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject respostaJson;
        try {
            respostaJson = (org.json.simple.JSONObject) parser.parse(pToken);
            Date dataHora = UtilSBCoreDataHora.incrementaSegundos(new Date(), Integer.parseInt(respostaJson.get("expires_in").toString()));
            respostaJson.put("dataHoraExpirarToken", String.valueOf(dataHora.getTime()));

            return super.armazenarRespostaToken(respostaJson.toJSONString()); //chamada super do metodo (implementação classe pai)
        } catch (ParseException ex) {

            return false;
        }

    }

    @Override
    public String getUrlObterCodigoSolicitacao() {
        return urlObterCodigoSolicitacao;
    }

    @Override
    public String getUrlRetornoReceberCodigoSolicitacao() {
        return urlRetornoSucessoObterToken;
    }

    @Override
    public String getUrlServidorApiRest() {
        return urlServidorApiRest;
    }

    @Override
    protected String gerarUrlAutenticaoObterCodigoSolicitacaoToken() {
        return urlObterCodigoSolicitacao;
    }

    @Override
    protected String gerarUrlRetornoSucessoGeracaoTokenDeAcesso() {

        return urlRetornoSucessoObterToken;
    }

}
