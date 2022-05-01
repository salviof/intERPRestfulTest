package org.coletivoJava.fw.projetos.integracao.implemetation.model.sistemaerpconfiavel;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.UtilSBRestful;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import com.super_bits.modulosSB.SBCore.modulos.objetos.calculos.ValorLogicoCalculoGenerico;
import org.coletivoJava.fw.projetos.integracao.api.model.sistemaerpconfiavel.ValorLogicoSistemaERPConfiavel;
import org.coletivoJava.fw.projetos.integracao.api.model.sistemaerpconfiavel.ValoresLogicosSistemaERPConfiavel;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import org.coletivojava.fw.utilCoreBase.UtilSBCoreReflexaoAPIERPRestFull;

@ValorLogicoSistemaERPConfiavel(calculo = ValoresLogicosSistemaERPConfiavel.NOME)
public class ValorLogicoSistemaERPConfiavelNome
        extends
        ValorLogicoCalculoGenerico {

    public ValorLogicoSistemaERPConfiavelNome(ItfCampoInstanciado pCampo) {
        super(pCampo);
    }

    @Override
    public Object getValor(Object... pEntidade) {

        String nome = UtilSBCoreReflexaoAPIERPRestFull.getSlugAplicacao(getSistema());
        getSistema().setNome(nome);
        return getSistema().getNome();
    }

    public SistemaERPConfiavel getSistema() {
        return (SistemaERPConfiavel) getCampoInst().getObjetoDoAtributo();
    }

}
