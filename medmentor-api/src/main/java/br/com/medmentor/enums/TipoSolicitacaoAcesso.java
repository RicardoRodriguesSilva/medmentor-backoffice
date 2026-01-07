package br.com.medmentor.enums;

public enum TipoSolicitacaoAcesso {
    NOVA_SENHA(1),
    RECUPERACAO_SENHA(2);

    private final int codigo;

    TipoSolicitacaoAcesso(int codigo) {
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