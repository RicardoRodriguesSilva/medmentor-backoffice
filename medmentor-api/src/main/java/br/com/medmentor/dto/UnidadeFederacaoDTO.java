package br.com.medmentor.dto;

public class UnidadeFederacaoDTO extends GenericDTO {
	
	private static final long serialVersionUID = 1L;
	
	public Integer id;
	public String nome;
	public String sigla;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
    @Override
    public String toString() {
        return "UnidadeFederacaoDTO{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", sigla=" + sigla + 
               '}';
    }
}
