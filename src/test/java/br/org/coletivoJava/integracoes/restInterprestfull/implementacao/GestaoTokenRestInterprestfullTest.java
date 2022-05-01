/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.restInterprestfull.implementacao;

import com.super_bits.modulosSB.SBCore.modulos.erp.SolicitacaoControllerERP;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.ConfigPercistenciaItegracaoSistemas;
import br.org.coletivoJava.integracoes.intRestful.api.ConfiguradorCoreApiERPIntegracoes;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.junit.Test;

/**
 *
 * @author sfurbino
 */
public class GestaoTokenRestInterprestfullTest {

    public GestaoTokenRestInterprestfullTest() {
    }

    @Test
    public void testExtrairToken() {
        SBCore.configurar(new ConfiguradorCoreApiERPIntegracoes(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPercistenciaItegracaoSistemas());
        SistemaERPConfiavel sistema = new SistemaERPConfiavel();
        sistema.setDominio("fatura.coletivojaval.com.br");

        //FabIntApiRestIntegracaoERPRestfull.ACOES_GET_OPCOES.getAcao(solicitacao);
    }

}
