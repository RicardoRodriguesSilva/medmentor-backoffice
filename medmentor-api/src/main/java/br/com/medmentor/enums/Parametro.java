package br.com.medmentor.enums;

public enum Parametro {
    ADMIN_EMAIL(1),
	PASS_EMAIL(2),
	HOST_EMAIL(3),
    PORT_EMAIL(4),
    QTD_HORAS_PLANTONISTA_DIA(5);

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