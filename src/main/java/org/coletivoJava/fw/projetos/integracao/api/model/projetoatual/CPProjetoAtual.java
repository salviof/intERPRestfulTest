package org.coletivoJava.fw.projetos.integracao.api.model.projetoatual;

import com.super_bits.modulos.SBAcessosModel.fabricas.ProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoReferenciaEntidade;

@InfoReferenciaEntidade(tipoObjeto = ProjetoAtual.class)
public enum CPProjetoAtual {
	_ID, _NOMEPROJETO, _NOMECLIENTE;

	public static final String id = "id";
	public static final String nomeprojeto = "nomeProjeto";
	public static final String nomecliente = "nomeCliente";
}