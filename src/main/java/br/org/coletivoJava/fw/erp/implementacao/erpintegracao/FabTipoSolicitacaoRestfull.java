/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

/**
 *
 * @author sfurbino
 */
public enum FabTipoSolicitacaoRestfull {

    CONTROLLER,
    OPCOES,
    LISTA_DA_ENTIDADE,
    LISTA_DE_ENTIDADE,
    ESTRUTURA_FORMULARIO,
    BEAN_FORMULARIO;

    public String getMetodo() {
        switch (this) {
            case CONTROLLER:
                return "PUT";
            case OPCOES:
                return "OPTIONS";
            case LISTA_DA_ENTIDADE:
                return "GET";
            case LISTA_DE_ENTIDADE:
                return "GET";
            case ESTRUTURA_FORMULARIO:
                return "GET";
            case BEAN_FORMULARIO:
                return "GET";
            default:
                throw new AssertionError(this.name());

        }
    }

}
