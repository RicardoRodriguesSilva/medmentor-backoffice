package br.com.medmentor.dto;

import java.time.LocalDate;

public class PessoaFisicaDTO extends GenericDTO
{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private PessoaDTO pessoaDTO; 
    private String cpf;
    private LocalDate dataNascimento;
    private Integer idade;

    public PessoaFisicaDTO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PessoaDTO getPessoaDTO() {
		return pessoaDTO;
	}

	public void setPessoaDTO(PessoaDTO pessoaDTO) {
		this.pessoaDTO = pessoaDTO;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	@Override
	public String toString() {
		return "PessoaFisicaDTO [id=" + id + ", pessoaDTO=" + pessoaDTO + ", cpf=" + cpf + ", dataNascimento="
				+ dataNascimento + ", idade=" + idade + "]";
	}
}