/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.token;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.token.TokenDeAcessoExternoDinamico;
import java.util.Date;

/**
 *
 * @author sfurbino
 */
public class TokenConcessaoOauthServer extends TokenDeAcessoExternoDinamico {

    private final String chavePublicaAplicativoConfiavel;
    private final String identificadorUsuario;

    public TokenConcessaoOauthServer(String pToken, Date pDataHoraExipira, String pChavePublicaAplicativoConfiavel, String pIdentificadorUsuario) {
        super(pToken, pDataHoraExipira);
        chavePublicaAplicativoConfiavel = pChavePublicaAplicativoConfiavel;
        identificadorUsuario = pIdentificadorUsuario;
    }

    public String getChavePublicaAplicacaoConfiavel() {
        return chavePublicaAplicativoConfiavel;
    }

    public String getIdentificadorUsuario() {
        return identificadorUsuario;
    }

}
