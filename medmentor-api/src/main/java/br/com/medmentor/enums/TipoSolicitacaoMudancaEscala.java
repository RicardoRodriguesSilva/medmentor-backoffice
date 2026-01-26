package br.com.medmentor.enums;

public enum TipoSolicitacaoMudancaEscala {
    INCLUSAO(1),
    EXCLUSAO(2),
    ACEITAR(3),
    RECUSAR(4);

    private final int codigo;

    TipoSolicitacaoMudancaEscala(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoSolicitacaoAcesso fromCodigo(int codigo) {
        for (TipoSolicitacaoAcesso tipo : TipoSolicitacaoAcesso.values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código do canal inválido: " + codigo);
    }
}
