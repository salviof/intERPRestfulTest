package org.coletivoJava.fw.projetos.integracao.implemetation.model.regiao;

import com.super_bits.modulosSB.SBCore.modulos.objetos.calculos.ValorLogicoCalculoGenerico;
import org.coletivoJava.fw.projetos.integracao.api.model.regiao.ValorLogicoRegiao;
import org.coletivoJava.fw.projetos.integracao.api.model.regiao.ValoresLogicosRegiao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;

@ValorLogicoRegiao(calculo = ValoresLogicosRegiao.CIDADESDISPONIVEIS)
public class ValorLogicoRegiaoCidadesDisponiveis
		extends
			ValorLogicoCalculoGenerico {

	ValorLogicoRegiaoCidadesDisponiveis(ItfCampoInstanciado pCampo) {
		super(pCampo);
	}
}