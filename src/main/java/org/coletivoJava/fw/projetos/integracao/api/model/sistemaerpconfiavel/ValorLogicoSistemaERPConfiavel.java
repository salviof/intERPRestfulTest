package org.coletivoJava.fw.projetos.integracao.api.model.sistemaerpconfiavel;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import org.coletivoJava.fw.projetos.integracao.api.model.sistemaerpconfiavel.ValoresLogicosSistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@InfoReferenciaEntidade(tipoObjeto = SistemaERPConfiavel.class)
public @interface ValorLogicoSistemaERPConfiavel {

	ValoresLogicosSistemaERPConfiavel calculo();
}