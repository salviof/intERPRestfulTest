/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import com.super_bits.modulosSB.webPaginas.controller.servletes.tratamentoErro.ErroSBGenericoWeb;

/**
 *
 * @author sfurbino
 */
public class ErroTentandoObterTokenAcesso extends ErroSBGenericoWeb {

    public ErroTentandoObterTokenAcesso(String pMsg) {
        super(pMsg);
    }

}
