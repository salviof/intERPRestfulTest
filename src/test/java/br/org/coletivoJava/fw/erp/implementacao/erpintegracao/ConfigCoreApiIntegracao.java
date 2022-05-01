/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.simulacaoComunicacao.acoes.FabAcaoRestfullTestes;
import com.super_bits.modulos.SBAcessosModel.fabricas.FabAcaoProjetoSB;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.ConfiguradorCoreDeProjetoJarPersistenciaAbstrato;
import com.super_bits.modulosSB.SBCore.ConfigGeral.ItfConfiguracaoCoreCustomizavel;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.CentralComunicacaoDesktop;
import testesFW.ConfigCoreJunitPadraoDesenvolvedor;

/**
 *
 * @author sfurbino
 */
public class ConfigCoreApiIntegracao extends ConfiguradorCoreDeProjetoJarPersistenciaAbstrato {

    @Override
    public void defineFabricasDeACao(ItfConfiguracaoCoreCustomizavel pConfig) {

        setIgnorarConfiguracaoPermissoes(false);
        pConfig.setFabricaDeAcoes(new Class[]{FabAcaoProjetoSB.class, FabAcaoRestfullTestes.class});
        pConfig.setCentralComunicacao(CentralComunicacaoDesktop.class);
        pConfig.setClasseConfigPermissao(ConfigPermissaoTestesIntegracao.class);

    }

}
