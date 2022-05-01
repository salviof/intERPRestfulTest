/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.integracoes.restInterprestfull.implementacao;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.LinkEntidadesSistemasERP;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.consultaDinamica.ConsultaDinamicaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfServicoLinkEntreEntidadesErpRestfull;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimplesSomenteLeitura;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author salvio
 */
public class ServicoLinkEntreEntidadesErpRestfull implements ItfServicoLinkEntreEntidadesErpRestfull {

    @Override
    public String getCodigoApiExterna(ItfSistemaERP pSistema, ItfBeanSimples pEntidade) {
        LinkEntidadesSistemasERP linkEntreEntidades
                = new LinkEntidadesSistemasERP(
                        pSistema,
                        pEntidade
                );
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        try {
            ConsultaDinamicaDeEntidade consulta = new ConsultaDinamicaDeEntidade(LinkEntidadesSistemasERP.class, em);
            consulta.addcondicaoCampoIgualA("codigoIdentificador", linkEntreEntidades.getCodigoIdentificador());
            List<LinkEntidadesSistemasERP> linl = consulta.resultadoRegistros();
            if (linl.isEmpty()) {
                return null;
            } else {
                return linl.get(0).getCodigoSistemaRemoto();
            }

        } finally {
            UtilSBPersistencia.fecharEM(em);
        }
    }

    @Override
    public String getCodigoSistemaInterno(ItfSistemaERP pSistema, Class pEntidade, String pCodigoapiExterno) {
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        try {
            ConsultaDinamicaDeEntidade consulta = new ConsultaDinamicaDeEntidade(LinkEntidadesSistemasERP.class, em);
            consulta.addcondicaoCampoIgualA("codigoSistemaRemoto", pCodigoapiExterno);
            List<LinkEntidadesSistemasERP> linl = consulta.resultadoRegistros();
            if (linl.isEmpty()) {
                return null;
            } else {
                return linl.get(0).getCodigoInterno();
            }

        } finally {
            UtilSBPersistencia.fecharEM(em);
        }
    }

    @Override
    public boolean registrarCodigoLigacaoApi(ItfSistemaERP pSistema, ItfBeanSimples pEntidade, String codigoAPIExterna) {
        LinkEntidadesSistemasERP linkEntreEntidades
                = new LinkEntidadesSistemasERP(
                        pSistema,
                        pEntidade,
                        codigoAPIExterna
                );

        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        try {
            ConsultaDinamicaDeEntidade consulta = new ConsultaDinamicaDeEntidade(LinkEntidadesSistemasERP.class, em);
            consulta.addcondicaoCampoIgualA("codigoIdentificador", linkEntreEntidades.getCodigoIdentificador());
            List<LinkEntidadesSistemasERP> linl = consulta.resultadoRegistros();

            if (!linl.isEmpty()) {
                LinkEntidadesSistemasERP linkAtualizado = linl.get(0);
                linkAtualizado.setCodigoSistemaRemoto(codigoAPIExterna);
                UtilSBPersistencia.mergeRegistro(linkAtualizado);
            } else {
                UtilSBPersistencia.mergeRegistro(linkEntreEntidades);
            }

        } finally {
            UtilSBPersistencia.fecharEM(em);
        }
        return true;
    }

    @Override
    public String getCodigoApiExterna(Class pEntidade, int pCodigoInterno
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getCodigoSistemaInterno(Class pEntidade, int pCodigoapiExterno
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean registrarCodigoLigacaoApi(Class pEntidade, int codigoInterno, String codigoAPIExterna
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <T extends ItfBeanSimplesSomenteLeitura> T getObjetoDTOFromJson(Class< ? extends T> pClass,
            String Json
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
