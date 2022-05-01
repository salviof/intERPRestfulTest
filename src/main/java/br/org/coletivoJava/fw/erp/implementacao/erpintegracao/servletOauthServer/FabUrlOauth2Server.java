/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.servletOauthServer;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.TIPO_PARTE_URL;
import com.super_bits.modulosSB.webPaginas.controller.servletes.urls.ItfFabUrlServletSBFW;
import com.super_bits.modulosSB.webPaginas.controller.servletes.urls.parametrosURL.InfoParametroURL;

/**
 *
 * @author sfurbino
 */
public enum FabUrlOauth2Server implements ItfFabUrlServletSBFW {
    @InfoParametroURL(nome = "Tipo Acesso", tipoParametro = TIPO_PARTE_URL.OBJETO_COM_CONSTRUCTOR,
            fabricaObjetosRelacionada = FabTipoRequisicaoOauthServer.class, tipoEntidade = TipoRequisicaoOauth.class)
    TIPO_REQUISICAO,
    @InfoParametroURL(nome = "Hash chave pública servico", tipoParametro
            = TIPO_PARTE_URL.TEXTO)
    CHAVE_PUBLICA_ID_RECURSOS,
    @InfoParametroURL(nome = "Hash chave pública cliente", tipoParametro
            = TIPO_PARTE_URL.TEXTO)
    CHAVE_PUBLICA_ID_CLIENTE,
    @InfoParametroURL(nome = "Endereço Resposta Cliente", tipoParametro = TIPO_PARTE_URL.TEXTO)
    REDIRECT_URI,
    @InfoParametroURL(nome = "Escopo", tipoParametro = TIPO_PARTE_URL.TEXTO)
    ESCOPO;

}
