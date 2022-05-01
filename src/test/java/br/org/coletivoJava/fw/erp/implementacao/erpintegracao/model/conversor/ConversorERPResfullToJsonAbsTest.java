/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.conversor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author salvio
 */
public class ConversorERPResfullToJsonAbsTest {

    @Test
    public void testGetListAtributosDaLista() {
        ConversorERPResfullToJsonAbs conversor = new ConversorERPResfullToJsonAbs();

        Map<String, String> mapeamento = new HashMap<>();

        mapeamento.put("id", "@id");
        mapeamento.put("pessoa", "cliente");
        mapeamento.put("itenssazonais[]", "itens");
        mapeamento.put("itenssazonais[].valorsetup", "itens[].valorNegociado");
        mapeamento.put("itenssazonais[].tiposervico", "itens[].tipoServico");

        mapeamento.put("itemrecorrente[]", "itensRecorrentes");
        mapeamento.put("itemrecorrente[].valormensal", "itensRecorrentes[].valorNegociado");
        mapeamento.put("itemrecorrente[].tiposervico", "itensRecorrentes[].tipoServico");

        List<String> atributos = conversor.getListAtributosDaLista("itemrecorrente[].valormensal", mapeamento);

        atributos.stream().forEach(System.out::println);
    }

}
