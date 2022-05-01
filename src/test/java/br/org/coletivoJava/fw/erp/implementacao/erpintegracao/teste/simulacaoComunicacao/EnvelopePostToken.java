/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer.ServletOauth2Server;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.simuladorResposta.ItfSimulacaoRespostaServlet;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.WS.conexaoWebServiceClient.RespostaWebServiceSimples;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.ChamadaHttpSimples;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import static javax.ws.rs.client.Entity.json;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

/**
 *
 * @author sfurbino
 */
public class EnvelopePostToken extends EnvelopeRequisicaoServlet implements ItfSimulacaoRespostaServlet {

    private ChamadaHttpSimples chamada;

    public EnvelopePostToken(ChamadaHttpSimples pChamada) {

        super(true);
        chamada = pChamada;
        buildRequisicao();
    }

    private void buildRequisicao() {
        try {
            setRequestURL(new URL(chamada.getUrlRequisicao()));
            Mockito.when(resposta.getStatus()).thenReturn(200);
            setOrigimHeader("crm.casanovadigital.com.br");
            System.out.println(chamada.getCorpo());

            Mockito.when(requisicao.getReader()).thenReturn(new BufferedReader(new StringReader(chamada.getCorpo())));

            Mockito.when(requisicao.getContentType()).thenReturn("application/json");
            Mockito.when(requisicao.getCharacterEncoding()).thenReturn("UTF-8");

        } catch (MalformedURLException ex) {
            Logger.getLogger(EnvelopePostToken.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EnvelopePostToken.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public RespostaWebServiceSimples getRespostaWS() {

        try {
            ServletOauth2Server servlet = new ServletOauth2Server();
            servlet.doPost(getRequisicao(), getResposta());
            String resp = null;
            String erro = null;
            int codigoResposta = resposta.getStatus();

            if (codigoResposta >= 200 && codigoResposta < 300) {
                resp = getStringwriter().getBuffer().toString().trim();
            }
            RespostaWebServiceSimples respostaWs = new RespostaWebServiceSimples(getResposta().getStatus(), resp, erro);
            return respostaWs;

        } catch (ServletException | IOException ex) {
            RespostaWebServiceSimples respwsErro = new RespostaWebServiceSimples(500, ex.getMessage(), ex.getMessage());
            return respwsErro;

        }

    }

    @Override
    public String getRespostaPost(HttpServlet pServeletProcessador) {

        return getRespostaWS().getResposta();

    }

    @Override
    public String getRespostaGet(HttpServlet pServeletProcessador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
