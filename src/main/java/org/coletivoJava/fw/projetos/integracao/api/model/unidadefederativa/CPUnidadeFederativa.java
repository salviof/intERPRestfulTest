package org.coletivoJava.fw.projetos.integracao.api.model.unidadefederativa;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.modulos.CEP.UnidadeFederativa;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = UnidadeFederativa.class)
public enum CPUnidadeFederativa {
	_ID, _NOME, _UF, _CIDADES;

	public static final String id = "id";
	public static final String nome = "nome";
	public static final String uf = "UF";
	public static final String cidades = "cidades";
}