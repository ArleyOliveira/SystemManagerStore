/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Utility;

import java.math.BigDecimal;
import java.util.Locale;

/**
 *
 * @author ARLEY
 */
public class BigDecimalFormater {
    public static BigDecimal casasDecimais(int casas, BigDecimal valor) {
        String quantCasas = "%." + casas + "f", textoValor = "0";
        try {
            textoValor = String.format(Locale.getDefault(), quantCasas, valor);
        } catch (java.lang.IllegalArgumentException e) {
            // Quando os digitos com 2 casas decimais forem Zeros, exemplo: 0.000001233888.
            // Nao existe valor 0,00 , logo entra na java.lang.IllegalArgumentException.
            if (e.getMessage().equals("Digits < 0")) {
                textoValor = "0";
            }
        }
        return new BigDecimal(textoValor.replace(",", "."));
    }
}
