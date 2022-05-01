package org.coletivoJava.fw.projetos.integracao.api.model.localizacaopostavel;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.modulos.CEP.LocalizacaoPostavel;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = LocalizacaoPostavel.class)
public enum CPLocalizacaoPostavel {
	_LOGRADOURO, _CEP;

	public static final String logradouro = "logradouro";
	public static final String cep = "cep";
}