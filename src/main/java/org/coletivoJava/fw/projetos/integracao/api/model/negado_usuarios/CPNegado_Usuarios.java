package org.coletivoJava.fw.projetos.integracao.api.model.negado_usuarios;

import com.super_bits.modulos.SBAcessosModel.model.Negado_Usuarios;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = Negado_Usuarios.class)
public enum CPNegado_Usuarios {
	_ID, _USUARIO, _ACESSO;

	public static final String id = "id";
	public static final String usuario = "usuario";
	public static final String acesso = "acesso";
}