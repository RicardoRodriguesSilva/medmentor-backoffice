// src/main/java/br/com/medmentor/model/CodTipoPessoa.java
package br.com.medmentor.enums;

public enum Canal {
    PORTAL(1),
    MOBILE(2);

    private final int codigo;

    Canal(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static Canal fromCodigo(int codigo) {
        for (Canal tipo : Canal.values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código do canal inválido: " + codigo);
    }
}