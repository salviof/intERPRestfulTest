package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json_bind_restful.SistemaERPAtual;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.DTO_SBGENERICO;

public class DTOSistemaERPAtual extends DTO_SBGENERICO<ItfDTOSistemaERPAtual>
        implements
        ItfDTOSistemaERPAtual {

    public DTOSistemaERPAtual(String pJson) {
        super(JsonBindDTOSistemaERPAtual.class, pJson);
    }

    public DTOSistemaERPAtual() {
        super(null, null);
    }

}
