/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.teste.servicoTeste.ModuloRestfulTeste;
import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.token.ItfTokenAcessoDinamico;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.token.ItfTokenRecuperacaoEmail;
import com.super_bits.modulosSB.SBCore.modulos.Controller.permissaoPadrao.ConfigPermissaoPadraoEmMemoria;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimplesSomenteLeitura;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfGrupoUsuario;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfUsuario;
import com.super_bits.modulosSB.SBCore.modulos.view.menu.ItfMenusDeSessao;
import com.super_bits.modulosSB.SBCore.modulos.view.menu.MenusDaSessao;
import java.util.List;
import javax.persistence.EntityManager;
import org.coletivojava.fw.api.objetoNativo.view.menu.MenuSBFW;

/**
 *
 * @author sfurbino
 */
public class ConfigPermissaoTestesIntegracao extends ConfigPermissaoPadraoEmMemoria {

    public ConfigPermissaoTestesIntegracao() {
        super(new Class[]{ModuloRestfulTeste.class});
    }

    @Override
    public ItfMenusDeSessao definirMenu(ItfGrupoUsuario pGrupo) {
        return new MenusDaSessao(new MenuSBFW(), new MenuSBFW());
    }

    @Override
    public ItfTokenRecuperacaoEmail gerarTokenRecuperacaoDeSenha(ItfUsuario pUsuario, int pMinutosValidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItfTokenAcessoDinamico gerarTokenDinamico(ItfFabricaAcoes pAcao, ItfBeanSimplesSomenteLeitura pItem, String pEmail) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isTokenDinamicoExiste(ItfFabricaAcoes pAcao, ItfBeanSimplesSomenteLeitura pItem, String pEmail) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void persitirMergePermissoes() {

    }

    @Override
    public List<ItfUsuario> configuraUsuarios() {
        List<ItfUsuario> usuarios = super.configuraUsuarios(); //To change body of generated methods, choose Tools | Templates.
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        List<UsuarioSB> usuariosDB = UtilSBPersistencia.getListaTodos(UsuarioSB.class, em);
        usuariosDB.stream().forEach(usuarios::add);
        UtilSBPersistencia.fecharEM(em);
        return usuarios;
    }

}
