/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfModuloAcaoSistema;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.modulo.ItfFabricaModulo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoDaFabrica;
import com.super_bits.modulosSB.SBCore.modulos.view.menu.ItfFabricaMenu;

/**
 *
 * @author sfurbino
 */
public enum FabModuloTestesIntegracao implements ItfFabricaModulo {
    @InfoObjetoDaFabrica(classeObjeto = ModuloTestesIntegracao.class, id = 1, nomeObjeto = "Modulo Testes integração")
    MODULO_TESTES;

    @Override
    public ItfModuloAcaoSistema getRegistro() {
        ModuloTestesIntegracao modulo = (ModuloTestesIntegracao) ItfFabricaModulo.super.getRegistro();
        modulo.setUmModuloNativo(true);
        return modulo;
    }

    @Override
    public ItfFabricaMenu getMenuPadrao() {
        return null;
    }

}
