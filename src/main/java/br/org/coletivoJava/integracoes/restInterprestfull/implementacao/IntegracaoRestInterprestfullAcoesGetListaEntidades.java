package br.org.coletivoJava.integracoes.restInterprestfull.implementacao;

import br.org.coletivoJava.integracoes.restInterprestfull.api.InfoIntegracaoRestInterprestfullRestfull;
import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.implementacao.AcaoApiIntegracaoComOauthAbstrato;
import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.FabTipoAgenteClienteApi;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;

@InfoIntegracaoRestInterprestfullRestfull(tipo = FabIntApiRestIntegracaoERPRestfull.ACOES_GET_LISTA_ENTIDADES)
public class IntegracaoRestInterprestfullAcoesGetListaEntidades
        extends
        AcaoApiIntegracaoComOauthAbstrato {

    public IntegracaoRestInterprestfullAcoesGetListaEntidades(
            final String pTipoAplicacaoERP,
            final FabTipoAgenteClienteApi pTipoAgente,
            final ItfUsuario pUsuario, final java.lang.Object... pParametro) {
        super(pTipoAplicacaoERP, FabIntApiRestIntegracaoERPRestfull.ACOES_GET_LISTA_ENTIDADES,
                pTipoAgente, pUsuario, pParametro);
    }
}
