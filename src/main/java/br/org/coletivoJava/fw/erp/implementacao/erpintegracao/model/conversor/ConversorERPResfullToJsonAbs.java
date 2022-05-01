/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.conversor;

import br.org.coletivoJava.fw.api.erp.erpintegracao.contextos.ERPIntegracaoSistemasApi;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreJson;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreReflexaoObjeto;
import com.super_bits.modulosSB.SBCore.UtilGeral.json.ErroProcessandoJson;
import com.super_bits.modulosSB.SBCore.modulos.erp.ItfServicoLinkDeEntidadesERP;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author salvio
 */
public class ConversorERPResfullToJsonAbs {

    private Map<String, String> mapeamentoCampos = new HashMap<>();
    private Map<String, String> mapeamentoCamposLista = new HashMap<>();
    private Map<String, JsonArrayBuilder> listagens = new HashMap<>();
    private static ItfServicoLinkDeEntidadesERP linkEntreEntidades = ERPIntegracaoSistemasApi.RESTFUL.getRepositorioLinkEntidadesByID();

    protected List<String> getListAtributosDaLista(String pAtributoEmLista, Map<String, String> pTabelaConversao) {
        if (!pAtributoEmLista.contains("[]")) {
            return new ArrayList<>();
        }
        String caminhoLista = pAtributoEmLista.substring(0, pAtributoEmLista.indexOf("[]"));
        List<String> atributos = new ArrayList<>();
        pTabelaConversao.keySet()
                .stream()
                .filter(atributo -> (atributo.contains(caminhoLista)) && atributo.contains("."))
                .map(atfull -> atfull.substring(atfull.indexOf(".") + 1, atfull.length()))
                .forEach(atributos::add);

        return atributos;
    }

    public ItfServicoLinkDeEntidadesERP getServicoLinkDeEntidadesEntreSistemas() {
        return linkEntreEntidades;
    }

    public void adicionarMapeamentoCampo(String pCaminhoAtributoLocal, String pCaminhoAtributoRemoto) {

    }

    public void adicionarMapeamentoCampo(String pCaminhoAtributoLocal) {
        adicionarMapeamentoCampo(pCaminhoAtributoLocal, pCaminhoAtributoLocal);
    }

    public JsonObjectBuilder buildObjeto(Map<String, String> pMapaValores, ItfBeanSimples pValor) {
        String idOrcamento = "0";
        if (pValor.getId() > 0) {
            idOrcamento = ERPIntegracaoSistemasApi.RESTFUL.getRepositorioLinkEntidadesByID().getCodigoApiExterna(pValor.getClass(), pValor.getId());
        }
        JsonObjectBuilder construtor = Json.createObjectBuilder();
        for (String valor : pMapaValores.keySet()) {
            processarAtributo(construtor, valor, pMapaValores, pValor);
        }
        for (Map.Entry<String, JsonArrayBuilder> entrada : listagens.entrySet()) {
            construtor.add(entrada.getKey(), entrada.getValue());
        }

        return construtor;
    }

    public void processarAtributo(JsonObjectBuilder pJsonBuilder, String pChave, Map<String, String> pAtributos, ItfBeanSimples pBeanConversao) {
        Class tipoEntidade = UtilSBCoreReflexaoObjeto.getClassExtraindoProxy(pBeanConversao.getClass().getSimpleName());
        if (pChave.contains("[]")) {
            String campoLista = pChave.substring(0, pChave.indexOf("[]"));
            if (listagens.containsKey(campoLista)) {
                return;
            }

            String atributoDoItem = pChave.substring(pChave.indexOf("[]"), pChave.length());
            List<ItfBeanSimples> listagem = (List) pBeanConversao.getCPinst(campoLista).getValor();
            if (!listagens.containsKey(campoLista)) {
                listagens.put(campoLista, Json.createArrayBuilder());
                List<ItfBeanSimples> listaBeans = (List) pBeanConversao.getCPinst(campoLista).getValor();
                int idx = 0;
                for (ItfBeanSimples item : listaBeans) {
                    JsonObjectBuilder objetoJsonItemArray = Json.createObjectBuilder();
                    List<String> atributos = getListAtributosDaLista(campoLista + "[]", pAtributos);
                    String codigo = ERPIntegracaoSistemasApi.RESTFUL.getRepositorioLinkEntidadesByID().getCodigoApiExterna(tipoEntidade, item.getId());
                    if (codigo == null) {
                        codigo = "0";
                    }
                    item.setId(Integer.valueOf(codigo));
                    for (String atributoObjeto : atributos) {
                        ItfCampoInstanciado campoInstanciado = item.getCPinst(atributoObjeto);

                        switch (campoInstanciado.getTipoPrimitivoDoValor()) {
                            case INTEIRO:
                                objetoJsonItemArray.add(atributoObjeto, campoInstanciado.getValorComoInteger());
                                break;
                            case NUMERO_LONGO:
                                objetoJsonItemArray.add(atributoObjeto, campoInstanciado.getValorComoLong());
                                break;
                            case LETRAS:
                                objetoJsonItemArray.add(atributoObjeto, campoInstanciado.getValor().toString());
                                break;
                            case DATAS:
                                Date data = (Date) campoInstanciado.getValor();
                                objetoJsonItemArray.add(atributoObjeto, data.getTime());
                                break;
                            case BOOLEAN:
                                objetoJsonItemArray.add(atributoObjeto, campoInstanciado.getValorComoBoolean());
                                break;
                            case DECIMAL:
                                objetoJsonItemArray.add(atributoObjeto, campoInstanciado.getValorComoDouble());
                                break;
                            case ENTIDADE:
                                if (campoInstanciado.getValor() != null) {
                                    String itemRemoto = ERPIntegracaoSistemasApi.RESTFUL.getRepositorioLinkEntidadesByID().getCodigoApiExterna(tipoEntidade, campoInstanciado.getValorComoItemSimples().getId());
                                    if (itemRemoto == null) {
                                        itemRemoto = "0";
                                    }
                                    try {
                                        JsonObjectBuilder itemSelecionado = UtilSBCoreJson.getJsonBuilderBySequenciaChaveValor("@id", itemRemoto);
                                        objetoJsonItemArray.add(atributoObjeto, itemSelecionado);
                                    } catch (ErroProcessandoJson ex) {

                                    }

                                }
                                break;
                            case OUTROS_OBJETOS:
                                break;
                            default:
                                objetoJsonItemArray.add(atributoObjeto, campoInstanciado.getValor().toString());

                        }

                    }

                    listagens.get(campoLista).add(idx, objetoJsonItemArray);

                    idx++;
                }

            }

            for (ItfBeanSimples item : listagem) {
                ItfCampoInstanciado campInstanciado = item.getCPinst(atributoDoItem);
            }

        } else {
            ItfCampoInstanciado campoInstanciado = pBeanConversao.getCPinst(pChave);

            switch (campoInstanciado.getTipoPrimitivoDoValor()) {
                case INTEIRO:
                    pJsonBuilder.add(pAtributos.get(pChave), campoInstanciado.getValorComoInteger());
                    break;
                case NUMERO_LONGO:
                    pJsonBuilder.add(pAtributos.get(pChave), campoInstanciado.getValorComoLong());
                    break;
                case LETRAS:
                    pJsonBuilder.add(pAtributos.get(pChave), campoInstanciado.getValor().toString());
                    break;
                case DATAS:
                    Date data = (Date) campoInstanciado.getValor();
                    pJsonBuilder.add(pAtributos.get(pChave), data.getTime());
                    break;
                case BOOLEAN:
                    pJsonBuilder.add(pAtributos.get(pChave), campoInstanciado.getValorComoBoolean());
                    break;
                case DECIMAL:
                    pJsonBuilder.add(pAtributos.get(pChave), campoInstanciado.getValorComoDouble());
                    break;
                case ENTIDADE:
                    if (campoInstanciado.getValor() != null) {
                        String itemRemoto = ERPIntegracaoSistemasApi.RESTFUL.getRepositorioLinkEntidadesByID().getCodigoApiExterna(tipoEntidade, campoInstanciado.getValorComoItemSimples().getId());
                        pJsonBuilder.add(pAtributos.get(pChave), itemRemoto);
                    }

                    break;
                case OUTROS_OBJETOS:
                    break;
                default:
                    pJsonBuilder.add(pAtributos.get(pChave), campoInstanciado.getValor().toString());
            }
        }

    }

    public Map<String, String> getMapeamentoTodosCapos() {
        return mapeamentoCampos;
    }
}
