/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.acoes;

import com.super_bits.modulos.SBAcessosModel.controller.FabModulosSistemaSB;
import com.super_bits.modulos.SBAcessosModel.controller.InfoModulosSistemaSB;
import com.super_bits.modulos.SBAcessosModel.fabricas.ItfFabricaDeAcoesPersistencia;
import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.SBCore.modulos.Controller.anotacoes.InfoTipoAcaoController;
import com.super_bits.modulosSB.SBCore.modulos.Controller.anotacoes.InfoTipoAcaoFormulario;
import com.super_bits.modulosSB.SBCore.modulos.Controller.anotacoes.InfoTipoAcaoGestaoEntidade;

/**
 *
 * @author sfurbino
 */
@InfoModulosSistemaSB(modulo = FabModulosSistemaSB.COMUNICACAO)
public enum FabAcaoRestfullTestes implements ItfFabricaDeAcoesPersistencia {

    @InfoTipoAcaoGestaoEntidade(entidade = UsuarioSB.class)
    USUARIO_RESTFUL_MB_GESTAO,
    @InfoTipoAcaoFormulario()
    USUARIO_RESTFUL_FRM_LISTAR,
    @InfoTipoAcaoFormulario()
    USUARIO_RESTFUL_FRM_EDITAR,
    @InfoTipoAcaoController()
    USUARIO_RESTFUL_CTR_SALVAR_NOVO,
    @InfoTipoAcaoController()
    USUARIO_RESTFUL_CTR_SALVAR_MERGE;

}
