/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.FabTipoRequisicaoOauthServer;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.ServletOauth2Server;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public class EnvelopeServeletSolicitarCodigoAcessoAoToken extends EnvelopeRequisicaoServlet {

    private final SistemaERPConfiavel sistemaServidor;
    private final SistemaERPConfiavel sistemaCliente;
    private final String emailSolicitante;

    public EnvelopeServeletSolicitarCodigoAcessoAoToken(
            SistemaERPConfiavel pSistemaServidor, SistemaERPConfiavel pSistemaRequisicao, String pEmailSolicitante
    ) {
        super(true);
        sistemaServidor = pSistemaServidor;
        sistemaCliente = pSistemaRequisicao;
        emailSolicitante = pEmailSolicitante;
        buildRequisicao();
    }

    private void buildRequisicao() {
        adicionarHeader("CHAVE_PUBLICA", sistemaCliente.getChavePublica());
        setOrigimHeader(sistemaCliente.getDominio());

        try {
            String patchRequisicao = "/" + ServletOauth2Server.SLUGPUBLICACAOSERVLET
                    + "/" + FabTipoRequisicaoOauthServer.OBTER_CODIGO_DE_CONCESSAO_DE_ACESSO.toString()
                    + "/" + sistemaServidor.getHashChavePublica()
                    + "/" + sistemaCliente.getHashChavePublica()
                    + "/" + URLEncoder.encode(sistemaCliente.getUrlRecepcaoCodigo(), "UTF8")
                    + "/" + emailSolicitante;

            URL urlCompleto = new URL("https://" + sistemaServidor.getDominio() + patchRequisicao);
            setRequestURL(urlCompleto);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EnvelopeServeletSolicitarCodigoAcessoAoToken.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(EnvelopeServeletSolicitarCodigoAcessoAoToken.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getRespostaGet(HttpServlet pServeletProcessador) {
        try {
            ServletOauth2Server servlet = (ServletOauth2Server) pServeletProcessador;
            servlet.doGet(getRequisicao(), getResposta());
            String resposta = getStringwriter().getBuffer().toString().trim();
            return resposta;
        } catch (ServletException | IOException ex) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Falha chamando servlet" + ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public String getRespostaPost(HttpServlet pServeletProcessador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
