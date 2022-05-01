package org.coletivoJava.fw.projetos.integracao.api.model.usuariosb;

import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = UsuarioSB.class)
public enum CPUsuarioSB {
	_ID, _NOME, _EMAIL, _APELIDO, _SENHA, _COMPLEMENTO, _LOCALIZACAO, _TELEFONE, _TIPOUSUARIO, _DATACADASTRO, _ATIVO, _GRUPO, _GRUPOSADICIONAIS, _DATAHORAALTERACAO, _DATAHORAINSERSAO, _USUARIOINSERCAO, _USUARIOALTERACAO;

	public static final String id = "id";
	public static final String nome = "nome";
	public static final String email = "email";
	public static final String apelido = "apelido";
	public static final String senha = "senha";
	public static final String complemento = "complemento";
	public static final String localizacao = "localizacao";
	public static final String telefone = "telefone";
	public static final String tipousuario = "tipoUsuario";
	public static final String datacadastro = "dataCadastro";
	public static final String ativo = "ativo";
	public static final String grupo = "grupo";
	public static final String gruposadicionais = "gruposAdicionais";
	public static final String datahoraalteracao = "dataHoraAlteracao";
	public static final String datahorainsersao = "dataHoraInsersao";
	public static final String usuarioinsercao = "usuarioInsercao";
	public static final String usuarioalteracao = "usuarioAlteracao";
}