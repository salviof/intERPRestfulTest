/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.coletivoJava.fw.projetos.integracao.restfulERP.testes.conversorErpResfull;

import com.super_bits.modulosSB.SBCore.ConfigGeral.CarameloCode;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;

import com.super_bits.modulosSB.SBCore.modulos.erp.conversao.ItfConversorERRestfullToJson;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coletivojava.fw.utilCoreBase.UtilCRCReflexaoAPIERPRestFull;
import testesFW.geradorDeCodigo.GeradorClasseGenerico;

/**
 *
 * @author salvio
 */
public class GeradorConversorErpRestFullToJson extends GeradorClasseGenerico {

    public GeradorConversorErpRestFullToJson(ItfSistemaERP pSistem, Class pClasse) {
        super(UtilCRCReflexaoAPIERPRestFull.getPacoteClassesConversao(pSistem, pClasse), UtilCRCReflexaoAPIERPRestFull.getNomeClasseToJson(pSistem, pClasse));
        getCodigoJava().addImport(ItfConversorERRestfullToJson.class);
        getCodigoJava().addImport("br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.conversor.ConversorERPResfullToJsonAbs;");
        getCodigoJava().addInterface(ItfConversorERRestfullToJson.class);
        try {
            getCodigoJava().extendSuperType(CarameloCode.getClasseLoaderAplicacao().loadClass("br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.conversor.ConversorERPResfullToJsonAbs"));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GeradorConversorErpRestFullToJson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
