package org.coletivoJava.fw.projetos.integracao.api.model.ips;

import com.super_bits.modulos.SBAcessosModel.model.Ips.Ips;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = Ips.class)
public enum CPIps {
	_ID, _INICIALFAIXA, _FINALFAIXA, _DNS, _TIPO, _ATIVO, _DATAALTERACAO, _USUARIOALTERACAO, _USUARIOINSERCAO;

	public static final String id = "id";
	public static final String inicialfaixa = "inicialFaixa";
	public static final String finalfaixa = "finalFaixa";
	public static final String dns = "dns";
	public static final String tipo = "tipo";
	public static final String ativo = "ativo";
	public static final String dataalteracao = "dataAlteracao";
	public static final String usuarioalteracao = "usuarioAlteracao";
	public static final String usuarioinsercao = "usuarioInsercao";
}