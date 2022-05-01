package org.coletivoJava.fw.projetos.integracao.api.model.cidade;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.modulos.CEP.Cidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = Cidade.class)
public enum CPCidade {
	_ID, _NOME, _UNIDADEFEDERATIVA, _BAIRROS, _LOCALIDADE, _REGIOES, _ATIVO, _DATAALTERACAO, _DATACRIACAO;

	public static final String id = "id";
	public static final String nome = "nome";
	public static final String unidadefederativa = "unidadeFederativa";
	public static final String bairros = "bairros";
	public static final String localidade = "localidade";
	public static final String regioes = "regioes";
	public static final String ativo = "ativo";
	public static final String dataalteracao = "dataAlteracao";
	public static final String datacriacao = "dataCriacao";
}