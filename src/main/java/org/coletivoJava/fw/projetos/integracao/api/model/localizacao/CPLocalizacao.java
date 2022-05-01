package org.coletivoJava.fw.projetos.integracao.api.model.localizacao;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.modulos.CEP.Localizacao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = Localizacao.class)
public enum CPLocalizacao {
	_ID, _NOME, _BAIRRO, _UNIDADESFEDERATIVAS, _LONGITUDE, _LATITUDE, _COMPLEMENTO, _TIPOLOCALIZACAO;

	public static final String id = "id";
	public static final String nome = "nome";
	public static final String bairro = "bairro";
	public static final String unidadesfederativas = "unidadesFederativas";
	public static final String longitude = "longitude";
	public static final String latitude = "latitude";
	public static final String complemento = "complemento";
	public static final String tipolocalizacao = "tipoLocalizacao";
}