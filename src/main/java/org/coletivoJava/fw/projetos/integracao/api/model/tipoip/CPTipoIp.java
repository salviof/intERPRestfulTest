package org.coletivoJava.fw.projetos.integracao.api.model.tipoip;

import com.super_bits.modulos.SBAcessosModel.model.Ips.TipoIp;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = TipoIp.class)
public enum CPTipoIp {
	_ID, _NOME, _DESCRICAO;

	public static final String id = "id";
	public static final String nome = "nome";
	public static final String descricao = "descricao";
}