/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import jakarta.json.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sfurbino
 */
public class UtilSBRestFulEntityToJson {

    private static Map<Class, String> mapaNomesClassesConversaoToJson = new HashMap<>();
    private static Map<Class, String> mapaNomesClassesJsonToObjeto = new HashMap<>();
    private static Map<Class, Class> mapaConversorEntidadeToJson = new HashMap<>();
    private static Map<Class, Class> mapaConversorJsonToEntidade = new HashMap<>();

    public static JsonObject getJsonFromObjetoGenerico(ItfBeanSimples beanSimples) {
        if (beanSimples == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mapper.setAnnotationIntrospector(new AutoAtotacaoJacksonDefinindoCampoId());
        mapper.enable(MapperFeature.USE_ANNOTATIONS);
        mapper.registerModule(new Hibernate5Module());

        String json = null;
        try {
            json = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(beanSimples);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UtilSBRestFulEntityToJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return UtilSBCoreJson.getJsonObjectByTexto(json);
    }

    static class AutoAtotacaoJacksonDefinindoCampoId extends JacksonAnnotationIntrospector {

        public AutoAtotacaoJacksonDefinindoCampoId() {
            System.out.println("Test");
        }

        @Override
        public JsonIgnoreProperties.Value findPropertyIgnorals(Annotated a) {
            Class classe = a.getRawType();

            JsonIgnoreProperties.Value ignorados = JsonIgnoreProperties.Value
                    .forIgnoredProperties("mapaCamposInstanciados",
                            "novoBeanPreparado",
                            "mapaCampoPorAnotacao",
                            "controleCalculo",
                            "instancia",
                            "camposEsperados",
                            "mapaJustificativasExecucaoAcao",
                            "mapaAssistenteLocalizacao",
                            "mapeouTodosOsCampos"
                    );

            return ignorados;

        }

        @Override
        public String findImplicitPropertyName(AnnotatedMember m) {
            String nomePropriedade = super.findImplicitPropertyName(m);
            return nomePropriedade;
        }

        @Override
        public ObjectIdInfo findObjectIdInfo(com.fasterxml.jackson.databind.introspect.Annotated ann) {
            return new ObjectIdInfo(
                    PropertyName.construct("@id", null),
                    null,
                    ObjectIdGenerators.IntSequenceGenerator.class,
                    null);
        }

    }
}
