// src/main/java/br/com/medmentor/model/CodTipoPessoa.java
package br.com.medmentor.enums;

public enum TipoPessoa {
    FISICA(1),
    JURIDICA(2);

    private final int codigo;

    TipoPessoa(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoPessoa fromCodigo(int codigo) {
        for (TipoPessoa tipo : TipoPessoa.values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de tipo de pessoa inválido: " + codigo);
    }
}