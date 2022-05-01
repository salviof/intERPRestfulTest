package org.coletivoJava.fw.projetos.integracao.api.model.negado_grupos;

import com.super_bits.modulos.SBAcessosModel.model.Negado_Grupos;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = Negado_Grupos.class)
public enum CPNegado_Grupos {
	_ID, _GRUPO, _ACESSO;

	public static final String id = "id";
	public static final String grupo = "grupo";
	public static final String acesso = "acesso";
}