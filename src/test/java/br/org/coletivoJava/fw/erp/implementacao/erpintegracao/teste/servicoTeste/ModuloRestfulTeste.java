/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.servicoTeste;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.acoes.FabAcaoRestfullTestes;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.acoes.InfoAcaoestfulTestes;
import com.super_bits.modulos.SBAcessosModel.controller.resposta.RespostaComGestaoEMRegraDeNegocioPadrao;
import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.Persistencia.dao.ControllerAbstratoSBPersistencia;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfRespostaAcaoDoSistema;
import com.super_bits.modulosSB.SBCore.modulos.TratamentoDeErros.ErroRegraDeNegocio;

/**
 *
 * @author sfurbino
 */
public class ModuloRestfulTeste extends ControllerAbstratoSBPersistencia {

    @InfoAcaoestfulTestes(acao = FabAcaoRestfullTestes.USUARIO_RESTFUL_CTR_SALVAR_MERGE)
    public static ItfRespostaAcaoDoSistema usuarioMerge(UsuarioSB pUsuario) {
        return new RespostaComGestaoEMRegraDeNegocioPadrao(getNovaRespostaAutorizaChecaNulo(pUsuario), pUsuario) {
            @Override
            public void regraDeNegocio() throws ErroRegraDeNegocio {
                setRetorno(atualizarEntidade(pUsuario));
            }

        }.getResposta();
    }

}
