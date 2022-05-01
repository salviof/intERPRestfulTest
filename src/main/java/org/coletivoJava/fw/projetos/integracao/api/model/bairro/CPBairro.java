package org.coletivoJava.fw.projetos.integracao.api.model.bairro;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.modulos.CEP.Bairro;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = Bairro.class)
public enum CPBairro {
	_ID, _NOME, _CIDADE, _COORDENADAS;

	public static final String id = "id";
	public static final String nome = "nome";
	public static final String cidade = "cidade";
	public static final String coordenadas = "coordenadas";
}