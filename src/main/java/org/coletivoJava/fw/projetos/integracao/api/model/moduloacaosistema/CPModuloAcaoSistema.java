package org.coletivoJava.fw.projetos.integracao.api.model.moduloacaosistema;

import com.super_bits.modulos.SBAcessosModel.model.ModuloAcaoSistema;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = ModuloAcaoSistema.class)
public enum CPModuloAcaoSistema {
	_ID, _NOME, _DESCRICAO, _DATAHORACRIACAO, _UMMODULONATIVO, _ICONEDACLASSE, _TIPOMODULO, _SELECAOACOES;

	public static final String id = "id";
	public static final String nome = "nome";
	public static final String descricao = "descricao";
	public static final String datahoracriacao = "dataHoraCriacao";
	public static final String ummodulonativo = "umModuloNativo";
	public static final String iconedaclasse = "iconeDaClasse";
	public static final String tipomodulo = "tipoModulo";
	public static final String selecaoacoes = "selecaoAcoes";
}