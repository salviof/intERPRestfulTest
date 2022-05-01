/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.acoes;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author sfurbino
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface InfoAcaoestfulTestes {

    public boolean padraoBloqueado() default true;

    public FabAcaoRestfullTestes acao();
}
