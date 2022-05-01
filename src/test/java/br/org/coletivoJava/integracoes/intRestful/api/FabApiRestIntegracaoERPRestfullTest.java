/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.intRestful.api;

import br.org.coletivoJava.integracoes.restInterprestfull.api.FabIntApiRestIntegracaoERPRestfull;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.junit.Test;
import testes.testesSupers.TestesApiRest;

/**
 *
 * @author sfurbino
 */
public class FabApiRestIntegracaoERPRestfullTest extends TestesApiRest {

    public FabApiRestIntegracaoERPRestfullTest() {
    }

    /**
     * Test of values method, of class FabApiRestIntegracaoERPRestfull.
     */
    @Test
    public void gerarCodigo() {
        SBCore.configurar(new ConfiguradorCoreApiERPIntegracoes(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        gerarCodigosChamadasEndpoint(FabIntApiRestIntegracaoERPRestfull.class);
    }

}
