package br.com.medmentor.util;

public class NormalizadorDadosUtil {

    public static String removerFormatacaoCEP(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            return null;
        }
        return cep.replaceAll("[^\\d]", "");
    }

    public static String removerFormatacaoCNPJ(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return null;
        }
        return cnpj.replaceAll("[^\\d]", "");
    }
    
    public static String removerFormatacaoCPF(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return null;
        }
        return cpf.replaceAll("[^\\d]", "");
    }    

    public static String removerFormatacaoTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            return null;
        }

        return telefone.replaceAll("[^\\d]", "");
    }

    public static String apenasDigitos(String textoFormatado) {
        if (textoFormatado == null || textoFormatado.trim().isEmpty()) {
            return null;
        }
        return textoFormatado.replaceAll("[^\\d]", "");
    }
}