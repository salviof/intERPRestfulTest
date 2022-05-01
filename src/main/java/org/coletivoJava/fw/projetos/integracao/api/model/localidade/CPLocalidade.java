package org.coletivoJava.fw.projetos.integracao.api.model.localidade;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.modulos.CEP.Localidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = Localidade.class)
public enum CPLocalidade {
	_GRANDEBH, _ID, _DESCRICAO, _NOME, _ATIVO, _DATAALTERACAO, _CIDADES;

	public static final String grandebh = "grandeBH";
	public static final String id = "id";
	public static final String descricao = "descricao";
	public static final String nome = "nome";
	public static final String ativo = "ativo";
	public static final String dataalteracao = "dataAlteracao";
	public static final String cidades = "cidades";
}