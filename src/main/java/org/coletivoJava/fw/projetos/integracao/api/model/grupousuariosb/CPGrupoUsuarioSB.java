package org.coletivoJava.fw.projetos.integracao.api.model.grupousuariosb;

import com.super_bits.modulos.SBAcessosModel.model.GrupoUsuarioSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = GrupoUsuarioSB.class)
public enum CPGrupoUsuarioSB {
	_ID, _NOME, _DESCRICAO, _TIPOGRUPOUSUARIO, _MODULOS, _PERMISSOESCONCEDIDAS, _ACESSOS, _PERMISSAOPORACAO, _MODULOPRINCIPAL, _TIPOGRUPONATIVO, _PAGINAINICIAL, _USUARIOS, _DATAHORAALTERACAO, _DATAHORAINSERSAO, _USUARIOINSERCAO, _USUARIOALTERACAO, _ATIVO;

	public static final String id = "id";
	public static final String nome = "nome";
	public static final String descricao = "descricao";
	public static final String tipogrupousuario = "tipoGrupoUsuario";
	public static final String modulos = "modulos";
	public static final String permissoesconcedidas = "permissoesConcedidas";
	public static final String acessos = "acessos";
	public static final String permissaoporacao = "permissaoPorAcao";
	public static final String moduloprincipal = "moduloPrincipal";
	public static final String tipogruponativo = "tipoGrupoNativo";
	public static final String paginainicial = "paginaInicial";
	public static final String usuarios = "usuarios";
	public static final String datahoraalteracao = "dataHoraAlteracao";
	public static final String datahorainsersao = "dataHoraInsersao";
	public static final String usuarioinsercao = "usuarioInsercao";
	public static final String usuarioalteracao = "usuarioAlteracao";
	public static final String ativo = "ativo";
}