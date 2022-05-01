package br.org.coletivoJava.integracoes.restInterprestfull.implementacao;

import br.org.coletivoJava.integracoes.restInterprestfull.api.InfoIntegracaoRestInterprestfullRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

@InfoIntegracaoRestInterprestfullRestfull(tipo = FabIntApiRestIntegracaoERPRestfull.ACOES_EXECUTAR_CONTROLLER)
public class IntegracaoRestInterprestfullAcoesExecutarController
        extends
        IntegracaoResfullPadrao {

    public IntegracaoRestInterprestfullAcoesExecutarController(
            final String pTipoAplicacaoERP,
            final FabTipoAgenteClienteApi pTipoAgente,
            final ItfUsuario pUsuario, final java.lang.Object... pParametro) {
        super(pTipoAplicacaoERP, FabIntApiRestIntegracaoERPRestfull.ACOES_EXECUTAR_CONTROLLER,
                pTipoAgente, pUsuario, pParametro);
    }

    @Override
    protected void executarAcao() {
        super.executarAcao(); //To change body of generated methods, choose Tools | Templates.
    }

}
