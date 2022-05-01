/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model;

import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPAtual;

/**
 *
 * @author sfurbino
 */
public class SistemaERPAtual extends SistemaERPConfiavel implements ItfSistemaERPAtual {

    private String chavePrivada;

    @Override
    public String getChavePrivada() {
        return chavePrivada;
    }

    public void setChavePrivada(String chavePrivada) {
        this.chavePrivada = chavePrivada;
    }

}
