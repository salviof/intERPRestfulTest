package org.coletivoJava.fw.projetos.integracao.api.model.regiao;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import org.coletivoJava.fw.projetos.integracao.api.model.regiao.ValoresLogicosRegiao;
import com.super_bits.modulosSB.Persistencia.registro.persistidos.modulos.CEP.Regiao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@InfoReferenciaEntidade(tipoObjeto = Regiao.class)
public @interface ValorLogicoRegiao {

	ValoresLogicosRegiao calculo();
}