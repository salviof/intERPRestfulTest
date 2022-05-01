/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.FabConfigModuloWebERPChaves;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPAtual;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.FabTipoRequisicaoOauthServer;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.ServletOauth2Server;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.ConfigGeral.arquivosConfiguracao.ConfigModulo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public class EnvelopeSolicitacaoCodigoObterToken extends EnvelopeRequisicaoServlet {

    private final SistemaERPAtual sistemaServidor;
    private final SistemaERPConfiavel sistemaConfiavel;
    private final String usuario;

    public EnvelopeSolicitacaoCodigoObterToken(HttpServletRequest pRequisicao, HttpServletResponse pResposta,
            SistemaERPAtual pSistemaServidor, SistemaERPConfiavel pSistemaRequisicao, String pEmailUsuarioACesso
    ) {
        super(true);
        sistemaServidor = pSistemaServidor;
        sistemaConfiavel = pSistemaRequisicao;
        buildRequisicao();
        //  chavPrivadaSolicitante = pChavePrivada;
        usuario = pEmailUsuarioACesso;
    }

    private void buildRequisicao() {
        //String emailcripto = UtilSBCoreCriptoRSA.getTextoCriptografado(usuario, chavPrivadaSolicitante);
        //adicionarHeader("emailCripto", emailcripto);
        adicionarHeader("CHAVE_PUBLICA", sistemaConfiavel.getChavePublica());
        setOrigimHeader(sistemaConfiavel.getDominio());

        ConfigModulo moduloServidorOauth = SBCore.getConfigModulo(FabConfigModuloWebERPChaves.class);

        try {
            String urlRequisicao = "https://" + sistemaServidor.getDominio() + "/" + ServletOauth2Server.SLUGPUBLICACAOSERVLET
                    + "/" + FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_AUTORIZACAO.toString()
                    + "/" + FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_AUTORIZACAO.toString()
                    + "/" + sistemaServidor.getHashChavePublica()
                    + "/" + URLEncoder.encode(sistemaConfiavel.getHashChavePublica())
                    + "/" + URLEncoder.encode("https://crm.coletivojava.com.br/solicitacaoAuth2Recept/code/USUARIO/", "UTF8")
                    + "/USUARIO";

            setRequestURL(new URL(urlRequisicao));
        } catch (UnsupportedEncodingException | MalformedURLException ex) {
            Logger.getLogger(EnvelopeSolicitacaoCodigoObterToken.class.getName()).log(Level.SEVERE, null, ex);
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha gerando simulação obter", ex);
        }

    }

    @Override
    public String getRespostaGet(HttpServlet pServeletProcessador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getRespostaPost(HttpServlet pServeletProcessador) {
        try {
            ServletOauth2Server servlet = (ServletOauth2Server) pServeletProcessador;
            servlet.doPost(getRequisicao(), getResposta());
            String resposta = getStringwriter().getBuffer().toString().trim();
            return resposta;
        } catch (ServletException | IOException ex) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha chamando servlet" + ex.getMessage(), ex);
            return null;
        }
    }

}
