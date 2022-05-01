/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeSimples;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfSistemaERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampoValidadorLogico;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampoValorLogico;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import jakarta.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author sfurbino
 */
@Entity
@Table(indexes = {
    @Index(name = "CHAVEPUBLICA", columnList = "chavePublica")})
@InfoObjetoSB(tags = "Sistema ERP Confiável", plural = "Sistemas Confiáveis")
public class SistemaERPConfiavel extends EntidadeSimples implements ItfSistemaERP {

    @Id
    @InfoCampo(tipo = FabTipoAtributoObjeto.ID, somenteLeitura = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @InfoCampo(tipo = FabTipoAtributoObjeto.AAA_NOME)
    @InfoCampoValorLogico(nomeCalculo = "nome Aplicação")
    private String nome;

    @InfoCampo(tipo = FabTipoAtributoObjeto.TEXTO_SIMPLES, label = "dominio")
    @Column(nullable = false)
    private String dominio;

    @InfoCampo(tipo = FabTipoAtributoObjeto.URL, label = "Endereço Recepção Código Autorização")
    @Column(nullable = false)
    private String urlRecepcaoCodigo;

    @Column(length = 8000)
    @InfoCampo(tipo = FabTipoAtributoObjeto.AAA_DESCRITIVO, obrigatorio = true)
    private String chavePublica;

    @InfoCampo(tipo = FabTipoAtributoObjeto.URL, label = "Url endpoint Restful")
    @Column(nullable = false)
    @InfoCampoValidadorLogico()
    private String urlPublicaEndPoint;

    @InfoCampoValorLogico(nomeCalculo = "Hash")
    private String hashChavePublica;

    @Override
    public String getDominio() {
        return dominio;
    }

    @Override
    public String getChavePublica() {
        return chavePublica;
    }

    @Override
    public String getUrlRecepcaoCodigo() {
        return urlRecepcaoCodigo;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public void setUrlRecepcaoCodigo(String urlRecepcaoCodigo) {
        this.urlRecepcaoCodigo = urlRecepcaoCodigo;
    }

    public void setChavePublica(String chavePublica) {
        this.chavePublica = chavePublica;
        hashChavePublica = String.valueOf(chavePublica.hashCode());
    }

    @Override
    public String getHashChavePublica() {
        return hashChavePublica;
    }

    public void setHashChavePublica(String hashChavePublica) {
        this.hashChavePublica = hashChavePublica;
    }

    @Transient
    private String comoJson;

    @Override
    public JsonObject getComoJson() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUrlPublicaEndPoint() {
        return urlPublicaEndPoint;
    }

    public void setUrlPublicaEndPoint(String urlPublicaEndPoint) {
        this.urlPublicaEndPoint = urlPublicaEndPoint;
    }

}
