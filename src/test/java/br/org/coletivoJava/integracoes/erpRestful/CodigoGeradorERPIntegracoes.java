/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.erpRestful;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import br.org.coletivoJava.integracoes.intRestful.api.ConfiguradorCoreApiERPIntegracoes;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import org.apache.logging.log4j.LogManager;
import org.coletivojava.fw.api.objetoNativo.log.LogPadraoSB;
import org.junit.Test;
import testes.geradorCodigo.erp.dto.GeradorDTOInterface;
import testes.geradorCodigo.erp.dto.GeradorDTOPojo;
import testes.geradorCodigo.erp.dto.GeradorDTOProcessador;
import testes.geradorCodigo.erp.GeradorERPImplementacaoContexto;

/**
 *
 * @author sfurbino
 */
public class CodigoGeradorERPIntegracoes {

    @Test
    public void inicio() {
        try {
            //GeradorInterfaceDTO
            //GeradorDTOPojo
            //GeradorDTOProcessador
            //ERPContaPagarReceber.GALAX_PAY.getImplementacaoDoContexto().
            SBCore.configurar(new ConfiguradorCoreApiERPIntegracoes(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
            new GeradorERPImplementacaoContexto(ERPIntegracaoSistemasApi.RESTFUL).salvarEmDiretorioPadraCASO_NAO_EXISTA();

            for (Class entidade : ERPIntegracaoSistemasApi.RESTFUL.getInterfacesDeEntidade()) {
                GeradorDTOInterface geradorInterface = new GeradorDTOInterface(ERPIntegracaoSistemasApi.RESTFUL, entidade);
                geradorInterface.salvarEmDiretorioPadraoSubstituindoAnterior();
                GeradorDTOPojo geradorPojo = new GeradorDTOPojo(ERPIntegracaoSistemasApi.RESTFUL, entidade);
                geradorPojo.salvarEmDiretorioPadraCASO_NAO_EXISTA();
                GeradorDTOProcessador geradorProcessador = new GeradorDTOProcessador(ERPIntegracaoSistemasApi.RESTFUL, entidade);
                geradorProcessador.salvarEmDiretorioPadraCASO_NAO_EXISTA();

            }
        } catch (Throwable t) {
            LogManager.getLogger(LogPadraoSB.class).error("Erro Criando anotações", t);
        }
    }
}
