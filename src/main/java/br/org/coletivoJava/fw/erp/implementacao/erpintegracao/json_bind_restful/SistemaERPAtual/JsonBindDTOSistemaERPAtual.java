package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json_bind_restful.SistemaERPAtual;

import com.super_bits.modulosSB.SBCore.integracao.libRestClient.api.erp.dto.DTO_SB_JSON_PROCESSADOR_GENERICO;
import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.json_bind_restful.SistemaERPAtual.DTOSistemaERPAtual;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;

public class JsonBindDTOSistemaERPAtual
        extends
        DTO_SB_JSON_PROCESSADOR_GENERICO<DTOSistemaERPAtual> {

    public JsonBindDTOSistemaERPAtual() {
        super(DTOSistemaERPAtual.class);
    }

    @Override
    public DTOSistemaERPAtual deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
