// src/main/java/br/com/medmentor/model/CodTipoPessoa.java
package br.com.medmentor.enums;

public enum Parametro {
    EMAIL(1),
    USER_EMAIL(2),
	PASS_EMAIL(3),
	HOST_EMAIL(4),
    PORT_EMAIL(5);

    private final int codigo;

    Parametro(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static Parametro fromCodigo(int codigo) {
        for (Parametro tipo : Parametro.values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Parametro inválido: " + codigo);
    }
}