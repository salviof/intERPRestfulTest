package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json_bind_restful.SistemaERPAtual;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.ItfDTOSBJSON;
import br.org.coletivoJava.fw.api.erp.erpintegracao.model.ItfSistemaERPAtual;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.json.JsonObject;
import java.lang.String;

@JsonDeserialize(using = JsonBindDTOSistemaERPAtual.class)
public interface ItfDTOSistemaERPAtual extends ItfDTOSBJSON, ItfSistemaERPAtual {

    @Override
    public default String getChavePrivada() {
        return (String) getValorPorReflexao();
    }

    @Override
    public default String getChavePublica() {
        return (String) getValorPorReflexao();
    }

    @Override
    public default String getDominio() {
        return (String) getValorPorReflexao();
    }

    @Override
    public default boolean isTemImagemPequenaAdicionada() {
        return (boolean) getValorPorReflexao();
    }

    @Override
    public default String getSlugIdentificador() {
        return (String) getValorPorReflexao();
    }

    @Override
    public default String getNomeUnicoSlug() {
        return (String) getValorPorReflexao();
    }

    @Override
    public default String getNome() {
        return (String) getValorPorReflexao();
    }

    @Override
    public default String getNomeCurto() {
        return (String) getValorPorReflexao();
    }

    @Override
    public default String getIconeDaClasse() {
        return (String) getValorPorReflexao();
    }

    @Override
    public default int getId() {
        return (int) getValorPorReflexao();
    }

    @Override
    public default String getUrlRecepcaoCodigo() {
        return (String) getValorPorReflexao();
    }

    @Override
    public default String getHashChavePublica() {
        return (String) getValorPorReflexao();
    }

    @Override
    public default String getUrlPublicaEndPoint() {
        return (String) getValorPorReflexao();
    }

    @Override
    public default JsonObject getComoJson() {
        return getJsonModoPojo();
    }

}
