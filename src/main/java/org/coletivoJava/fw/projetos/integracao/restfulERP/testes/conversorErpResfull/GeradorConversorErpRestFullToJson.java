/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.coletivoJava.fw.projetos.integracao.restfulERP.testes.conversorErpResfull;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.conversor.ConversorERPResfullToJsonAbs;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;

import com.super_bits.modulosSB.SBCore.modulos.erp.conversao.ItfConversorERRestfullToJson;
import org.coletivojava.fw.utilCoreBase.UtilSBCoreReflexaoAPIERPRestFull;
import testesFW.geradorDeCodigo.GeradorClasseGenerico;

/**
 *
 * @author salvio
 */
public class GeradorConversorErpRestFullToJson extends GeradorClasseGenerico {

    public GeradorConversorErpRestFullToJson(ItfSistemaERP pSistem, Class pClasse) {
        super(UtilSBCoreReflexaoAPIERPRestFull.getPacoteClassesConversao(pSistem, pClasse), UtilSBCoreReflexaoAPIERPRestFull.getNomeClasseToJson(pSistem, pClasse));
        getCodigoJava().addImport(ItfConversorERRestfullToJson.class);
        getCodigoJava().addImport(ConversorERPResfullToJsonAbs.class);
        getCodigoJava().addInterface(ItfConversorERRestfullToJson.class);
        getCodigoJava().extendSuperType(ConversorERPResfullToJsonAbs.class);
    }

}
