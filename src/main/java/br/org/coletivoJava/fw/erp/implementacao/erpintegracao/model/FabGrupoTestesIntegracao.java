/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model;

import com.super_bits.modulos.SBAcessosModel.fabricas.FabAcaoProjetoSB;
import com.super_bits.modulos.SBAcessosModel.model.GrupoUsuarioSB;
import com.super_bits.modulos.SBAcessosModel.model.ModuloAcaoSistema;
import com.super_bits.modulosSB.Persistencia.fabrica.ItfFabricaComPersistencia;

/**
 *
 * @author sfurbino
 */
public enum FabGrupoTestesIntegracao implements ItfFabricaComPersistencia {
    GRUPO_TESTE;

    @Override
    public Object getRegistro() {
        GrupoUsuarioSB grupo = new GrupoUsuarioSB();
        grupo.setId(1);
        grupo.setNome("Testes integracao");
        grupo.setPaginaInicial(FabAcaoProjetoSB.PROJETO_FRM_VISAO_GERAL);
        grupo.setModuloPrincipal((ModuloAcaoSistema) FabModuloTestesIntegracao.MODULO_TESTES.getRegistro());
        return grupo;
    }

}
