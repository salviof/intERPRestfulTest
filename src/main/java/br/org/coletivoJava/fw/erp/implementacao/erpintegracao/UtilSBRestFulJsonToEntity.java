/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao;

import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaCampo;
import com.super_bits.modulosSB.SBCore.modulos.geradorCodigo.model.EstruturaDeEntidade;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.MapaObjetosProjetoAtual;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author salvio
 */
public class UtilSBRestFulJsonToEntity {

    public static void aplicarValor(ItfBeanSimples pEntidade, String pChave, JsonValue pValorJson) {
        EntityManager em = UtilSBPersistencia.getEntyManagerPadraoNovo();
        try {
            ItfCampoInstanciado campo = pEntidade.getCPinst(pChave);
            switch (campo.getFabricaTipoAtributo().getTipoPrimitivo()) {
                case INTEIRO:
                    pEntidade.getCampoInstanciadoByNomeOuAnotacao(pChave)
                            .setValor(pValorJson);
                    break;
                case NUMERO_LONGO:

                    break;
                case LETRAS:

                    pEntidade.getCampoInstanciadoByNomeOuAnotacao(pChave)
                            .setValor(pValorJson);

                    break;
                case DATAS:

                    break;
                case BOOLEAN:

                    break;
                case DECIMAL:

                    break;
                case ENTIDADE:

                    switch (campo.getFabricaTipoAtributo()) {
                        case OBJETO_DE_UMA_LISTA:
                            ItfCampoInstanciado cp = pEntidade.getCampoInstanciadoByNomeOuAnotacao(pChave);

                            String tipoCampo = cp.getTipoCampoSTR();
                            //    ItfBeanSimples objeto = (ItfBeanSimples) UtilSBPersistencia.
                            //          getRegistroByID(entidadeFilho, pJsonAtributosAtualizadosObjeto.getJsonObject(campo.getNomeDeclarado())
                            //                .getInt("id"), em);
                            //cp.setValor(objeto);
                            break;
                        case LISTA_OBJETOS_PARTICULARES:
                        case LISTA_OBJETOS_PUBLICOS:
                            JsonArray jsonArray = pValorJson.asJsonArray();
                            jsonArray.stream().forEach(jsonValue -> {
                                EstruturaDeEntidade estruturaObjeto = MapaObjetosProjetoAtual.getEstruturaObjeto(campo.getObjetoDoAtributo().getClass().getSimpleName());
                                EstruturaCampo estruturaCampo = estruturaObjeto.getCampoByNomeDeclarado(pChave);
                                String objetoDaLista = estruturaCampo.getClasseCampoDeclaradoOuTipoLista();
                                Class entidade = MapaObjetosProjetoAtual.getClasseDoObjetoByNome(objetoDaLista);
                                ItfBeanSimples item = null;
                                JsonObject itemJson = jsonValue.asJsonObject();
                                if (itemJson.containsKey("@id")) {
                                    item = (ItfBeanSimples) UtilSBPersistencia.getRegistroByID(entidade, itemJson.getInt("@id"), em);
                                } else {
                                    try {
                                        item = (ItfBeanSimples) entidade.newInstance();
                                    } catch (InstantiationException | IllegalAccessException ex) {
                                        Logger.getLogger(UtilSBRestFulJsonToEntity.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                                for (String atributoDoItem : itemJson.asJsonObject().keySet()) {
                                    ItfCampoInstanciado campoInstanciado = item.getCampoInstanciadoByNomeOuAnotacao(atributoDoItem);
                                    if (!campoInstanciado.isCampoNaoInstanciado()) {
                                        aplicarValor(item, atributoDoItem, itemJson.get(atributoDoItem));
                                    }
                                }

                            });
                            System.out.println("Listas ainda não foram definidas");
                        default:
                            throw new AssertionError(campo.getFabricaTipoAtributo().name());

                    }

                    break;

                case OUTROS_OBJETOS:
                    break;
                default:
                    throw new AssertionError(campo.getFabricaTipoAtributo().getTipoPrimitivo().name());

            }
        } catch (Throwable t) {

        } finally {
            UtilSBPersistencia.fecharEM(em);
        }
    }

    public static void aplicarAtributosJsonEmEntidade(ItfBeanSimples pEntidade, JsonObject pJsonAtributosAtualizadosObjeto) {
        if (pJsonAtributosAtualizadosObjeto == null || pEntidade == null) {
            return;
        }

        pJsonAtributosAtualizadosObjeto.asJsonObject().keySet().stream().forEach(nomeAtributo -> {
            if (nomeAtributo != null && !nomeAtributo.contains("@")) {
                ItfCampoInstanciado campoInstanciado = pEntidade.getCampoInstanciadoByNomeOuAnotacao(nomeAtributo);
                if (!campoInstanciado.isCampoNaoInstanciado()) {
                    aplicarValor(pEntidade, nomeAtributo, pJsonAtributosAtualizadosObjeto.get(nomeAtributo));
                }
            }

        });

    }
}
