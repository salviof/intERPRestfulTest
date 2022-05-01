package org.coletivoJava.fw.projetos.integracao.api.model.permitido_grupos;

import com.super_bits.modulos.SBAcessosModel.model.Permitido_Grupos;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = Permitido_Grupos.class)
public enum CPPermitido_Grupos {
	_ID, _NOME, _GRUPO, _ACESSO;

	public static final String id = "id";
	public static final String nome = "nome";
	public static final String grupo = "grupo";
	public static final String acesso = "acesso";
}