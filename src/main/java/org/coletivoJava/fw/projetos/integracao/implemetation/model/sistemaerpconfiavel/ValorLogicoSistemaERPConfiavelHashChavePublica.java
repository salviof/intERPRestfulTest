package org.coletivoJava.fw.projetos.integracao.implemetation.model.sistemaerpconfiavel;

import com.super_bits.modulosSB.SBCore.modulos.objetos.calculos.ValorLogicoCalculoGenerico;
import org.coletivoJava.fw.projetos.integracao.api.model.sistemaerpconfiavel.ValorLogicoSistemaERPConfiavel;
import org.coletivoJava.fw.projetos.integracao.api.model.sistemaerpconfiavel.ValoresLogicosSistemaERPConfiavel;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;

@ValorLogicoSistemaERPConfiavel(calculo = ValoresLogicosSistemaERPConfiavel.HASHCHAVEPUBLICA)
public class ValorLogicoSistemaERPConfiavelHashChavePublica
		extends
			ValorLogicoCalculoGenerico {

	ValorLogicoSistemaERPConfiavelHashChavePublica(ItfCampoInstanciado pCampo) {
		super(pCampo);
	}
}