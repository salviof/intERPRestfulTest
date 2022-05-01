package org.coletivoJava.fw.projetos.integracao.implemetation.model.sistemaerpconfiavel;

import br.org.coletivoJava.fw.erp.implementacao.erpintegracao.model.SistemaERPConfiavel;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringValidador;
import com.super_bits.modulosSB.SBCore.modulos.objetos.validador.ValidacaoGenerica;
import org.coletivoJava.fw.projetos.integracao.api.model.sistemaerpconfiavel.ValidadorSistemaERPConfiavel;
import org.coletivoJava.fw.projetos.integracao.api.model.sistemaerpconfiavel.ValidadoresSistemaERPConfiavel;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campoInstanciado.ItfCampoInstanciado;
import com.super_bits.modulosSB.SBCore.modulos.objetos.validador.ErroValidacao;
import java.net.MalformedURLException;
import java.net.URL;

@ValidadorSistemaERPConfiavel(validador = ValidadoresSistemaERPConfiavel.URLPUBLICAENDPOINT)
public class ValidacaoSistemaERPConfiavelUrlPublicaEndPoint extends ValidacaoGenerica<SistemaERPConfiavel> {

    public ValidacaoSistemaERPConfiavelUrlPublicaEndPoint(
            ItfCampoInstanciado pCampo) {
        super(pCampo);
    }

    @Override
    public Object validar(Object pValor) throws ErroValidacao {

        String urlStr = (String) pValor;
        try {
            URL url = new URL(urlStr);
            if (UtilSBCoreStringValidador.isNuloOuEmbranco(getSistemaERPConfiavel().getDominio())) {
                getSistemaERPConfiavel().setDominio(url.getHost());
            }

        } catch (MalformedURLException ex) {
            throw new ErroValidacao("Url inválida");
        }
        return pValor;
    }

    public SistemaERPConfiavel getSistemaERPConfiavel() {
        return getObjetoDoAtributo();
    }
}
