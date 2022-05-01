/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.json;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.UtilSBRestFulEntityToJson;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.ConfigPercistenciaItegracaoSistemas;
import br.org.coletivoJava.integracoes.intRestful.api.ConfiguradorCoreApiERPIntegracoes;

import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.estrutura.ItfEstruturaCampoEntidade;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Test;

/**
 *
 * @author sfurbino
 */
public class SerializerTest {

    @Test
    public void testeJsonSerializacaoObjeto() {
        SBCore.configurar(new ConfiguradorCoreApiERPIntegracoes(), SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPercistenciaItegracaoSistemas());
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        List<UsuarioSB> usuarios = UtilSBPersistencia.getListaTodos(UsuarioSB.class, em);

        for (ItfEstruturaCampoEntidade campo : MapaObjetosProjetoAtual.getEstruturaObjeto(UsuarioSB.class).getCampos()) {
            System.out.println(campo.getNome());
        }
        for (UsuarioSB usr : usuarios) {
            System.out.println(UtilSBRestFulEntityToJson.getJsonFromObjetoGenerico(usr));
        }

    }

}
