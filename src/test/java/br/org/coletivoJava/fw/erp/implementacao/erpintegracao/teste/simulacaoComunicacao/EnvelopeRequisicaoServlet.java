/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.ApiIntegracaoRestfulimplTest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author sfurbino
 */
public abstract class EnvelopeRequisicaoServlet {

    @Mock
    public HttpServletRequest requisicao;

    @Mock
    public HttpServletResponse resposta;

    private StringWriter sw;

    public EnvelopeRequisicaoServlet(boolean usarmkito) {
        MockitoAnnotations.initMocks(this);
        initStringWriter();
    }

    private void initStringWriter() {
        sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try {
            Mockito.when(resposta.getWriter()).thenReturn(pw);
        } catch (IOException ex) {
            Logger.getLogger(ApiIntegracaoRestfulimplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected HttpServletResponse getResposta() {
        return resposta;
    }

    protected HttpServletRequest getRequisicao() {
        return requisicao;
    }

    public void setRequestURL(URL pUrl) {
        String patch = pUrl.getPath();
        String query = pUrl.getQuery();
        StringBuilder uri = new StringBuilder();
        uri.append(patch);

        if (query != null) {
            if (!patch.endsWith("/")) {
                uri.append("/");
            }
            uri.append(query);
        }
        setRequestURI(uri.toString(), query);

    }

    public void setRequestURI(String pRequestURI, String query) {
        if (pRequestURI != null) {
            Mockito.when(requisicao.getRequestURI()).thenReturn(pRequestURI);

            Map<String, String> mapaDeParametros = getMapaParametros(query);
            for (Map.Entry<String, String> chaveValor : mapaDeParametros.entrySet()) {
                adicionarParametro(chaveValor.getKey(), chaveValor.getValue());
            }
        }
    }

    public void adicionarHeader(String pNomeHeader, String pResposta) {
        Mockito.when(requisicao.getHeader(pNomeHeader)).thenReturn(pResposta);
    }

    public void adicionarParametro(String pNomeHeader, String pResposta) {
        Mockito.when(requisicao.getParameter(pNomeHeader)).thenReturn(pResposta);
    }

    public void setOrigimHeader(String pOrigem) {
        Mockito.when(requisicao.getHeader("origin")).thenReturn(pOrigem);
    }

    public void setBodyRequisicao(String dataCorpo) {
        try {
            Mockito.when(requisicao.getReader()).thenReturn(new BufferedReader(new StringReader(dataCorpo)));
        } catch (IOException ex) {
            Logger.getLogger(EnvelopeRequisicaoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void adicionarPostData() {

    }

    protected StringWriter getStringwriter() {
        return sw;
    }

    public abstract String getRespostaGet(HttpServlet pServeletProcessador);

    public abstract String getRespostaPost(HttpServlet pServeletProcessador);

    public static Map<String, String> getMapaParametros(URL url) {

        String query = url.getQuery();
        return getMapaParametros(query);

    }

    public static Map<String, String> getMapaParametros(String pURIquery) {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        if (pURIquery == null) {
            return query_pairs;
        }
        String parametros = pURIquery;
        if (pURIquery.contains("?")) {
            parametros = parametros.split("\\?")[1];
        }
        String[] pairs = parametros.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            try {
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            } catch (UnsupportedEncodingException ex) {

                Logger.getLogger(EnvelopeRequisicaoServlet.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            }
        }
        return query_pairs;
    }

}
