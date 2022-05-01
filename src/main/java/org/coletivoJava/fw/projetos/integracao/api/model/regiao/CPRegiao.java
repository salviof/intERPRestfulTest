package org.coletivoJava.fw.projetos.integracao.api.model.regiao;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.modulos.CEP.Regiao;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = Regiao.class)
public enum CPRegiao {
	_ID, _NOMEREGIAO, _CIDADES, _UNIDADEFEDERATIVA, _CIDADESDISPONIVEIS, _BAIRROS, _SIGLA, _QUANTIDADECIDADES, _CRIADOEM, _ALTERADOEM, _ATIVO;

	public static final String id = "id";
	public static final String nomeregiao = "nomeRegiao";
	public static final String cidades = "cidades";
	public static final String unidadefederativa = "unidadeFederativa";
	public static final String cidadesdisponiveis = "cidadesDisponiveis";
	public static final String bairros = "bairros";
	public static final String sigla = "sigla";
	public static final String quantidadecidades = "quantidadeCidades";
	public static final String criadoem = "criadoEm";
	public static final String alteradoem = "alteradoEm";
	public static final String ativo = "ativo";
}