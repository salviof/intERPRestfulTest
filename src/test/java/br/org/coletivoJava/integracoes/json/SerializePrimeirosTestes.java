/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.coletivoJava.integracoes.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import java.io.IOException;

/**
 *
 * @author sfurbino
 */
public class SerializePrimeirosTestes extends JsonSerializer<ItfBeanSimples> {

    @Override
    public void serialize(ItfBeanSimples t, JsonGenerator jg, SerializerProvider sp) throws IOException {

    }

}
