package org.coletivoJava.fw.projetos.integracao.api.model.permissaosb;

import com.super_bits.modulos.SBAcessosModel.model.PermissaoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = PermissaoSB.class)
public enum CPPermissaoSB {
	_ID, _NOMEACESSO, _IDACAODOSISTEMA, _TIPOAUTENTICACAO, _LISTATODOSUSUARIOS, _LISTATODOSGRUPOSUSUARIOS, _USUARIOSPERMITIDOS, _USUARIOSNEGADOS, _GRUPOSPERMITIDOS, _GRUPOSNEGADOS, _IDACAOGESTAO;

	public static final String id = "id";
	public static final String nomeacesso = "nomeAcesso";
	public static final String idacaodosistema = "idacaoDoSistema";
	public static final String tipoautenticacao = "tipoAutenticacao";
	public static final String listatodosusuarios = "listaTodosUsuarios";
	public static final String listatodosgruposusuarios = "listaTodosGruposUsuarios";
	public static final String usuariospermitidos = "usuariosPermitidos";
	public static final String usuariosnegados = "usuariosNegados";
	public static final String grupospermitidos = "gruposPermitidos";
	public static final String gruposnegados = "gruposNegados";
	public static final String idacaogestao = "idAcaoGestao";
}