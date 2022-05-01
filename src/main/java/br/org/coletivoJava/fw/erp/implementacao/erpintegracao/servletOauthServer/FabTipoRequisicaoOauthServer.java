/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer;

import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoDaFabrica;

/**
 *
 * @author sfurbino
 */
public enum FabTipoRequisicaoOauthServer implements ItfFabrica {

    @InfoObjetoDaFabrica(nomeObjeto = "OBTER_CODIGO_DE_CONCESSAO_DE_ACESSO", id = 1, classeObjeto = TipoRequisicaoOauth.class)
    OBTER_CODIGO_DE_CONCESSAO_DE_ACESSO,
    @InfoObjetoDaFabrica(nomeObjeto = "OBTER_CODIGO_DE_AUTORIZACAO", id = 2, classeObjeto = TipoRequisicaoOauth.class)
    OBTER_CODIGO_DE_AUTORIZACAO,
    @InfoObjetoDaFabrica(nomeObjeto = "VERIFICACAO_STATUS_ACESSO", id = 3, classeObjeto = TipoRequisicaoOauth.class)
    VERIFICACAO_STATUS_ACESSO;

}
