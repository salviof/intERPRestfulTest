/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.webPaginas.controller.servlets.servletRecepcaoOauth.ServletRecepcaoOauth;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * @author sfurbino
 */
public class EnvelopeRequisicacaoTokenAcesso extends EnvelopeRequisicaoServlet {

    private final String codigo;
    private final String pathURI;
    private final URL urlcompleta;
    private final String hashServidor;

    public EnvelopeRequisicacaoTokenAcesso(String pCodigo, String pUrl, String pHashServidor
    ) throws MalformedURLException {
        super(true);
        codigo = pCodigo;

        urlcompleta = new URL(pUrl);
        pathURI = urlcompleta.getPath();
        hashServidor = pHashServidor;
        buildRequisicao();

    }

    private void buildRequisicao() {
        setRequestURL(urlcompleta);
        setOrigimHeader(codigo);
    }

    @Override
    public String getRespostaGet(HttpServlet pServeletProcessador) {
        try {
            ServletRecepcaoOauth servlet = (ServletRecepcaoOauth) pServeletProcessador;
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
