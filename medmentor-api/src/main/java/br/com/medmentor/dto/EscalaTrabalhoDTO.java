package br.com.medmentor.dto;

import java.time.LocalDateTime;

public class EscalaTrabalhoDTO extends GenericDTO {
	   
	private static final long serialVersionUID = 1L;

    private Integer id;
    private EmpresaProfissionalDTO empresaProfissionalDTO;
    private EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private Boolean bolAtivo;
    private Boolean bolRealizado;
    private Boolean bolDisponibilizado;

    public EscalaTrabalhoDTO() {
    	
    }

	public EscalaTrabalhoDTO(Integer id, EmpresaProfissionalDTO empresaProfissionalDTO,
			EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO, LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida,
			Boolean bolAtivo, Boolean bolRealizado, Boolean bolDisponibilizado) {
		super();
		this.id = id;
		this.empresaProfissionalDTO = empresaProfissionalDTO;
		this.empresaUnidadeGestaoDTO = empresaUnidadeGestaoDTO;
		this.dataHoraEntrada = dataHoraEntrada;
		this.dataHoraSaida = dataHoraSaida;
		this.bolAtivo = bolAtivo;
		this.bolRealizado = bolRealizado;
		this.bolDisponibilizado = bolDisponibilizado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EmpresaProfissionalDTO getEmpresaProfissionalDTO() {
		return empresaProfissionalDTO;
	}

	public void setEmpresaProfissionalDTO(EmpresaProfissionalDTO empresaProfissionalDTO) {
		this.empresaProfissionalDTO = empresaProfissionalDTO;
	}

	public EmpresaUnidadeGestaoDTO getEmpresaUnidadeGestaoDTO() {
		return empresaUnidadeGestaoDTO;
	}

	public void setEmpresaUnidadeGestaoDTO(EmpresaUnidadeGestaoDTO empresaUnidadeGestaoDTO) {
		this.empresaUnidadeGestaoDTO = empresaUnidadeGestaoDTO;
	}

	public LocalDateTime getDataHoraEntrada() {
		return dataHoraEntrada;
	}

	public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) {
		this.dataHoraEntrada = dataHoraEntrada;
	}

	public LocalDateTime getDataHoraSaida() {
		return dataHoraSaida;
	}

	public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
		this.dataHoraSaida = dataHoraSaida;
	}

	public Boolean getBolAtivo() {
		return bolAtivo;
	}

	public void setBolAtivo(Boolean bolAtivo) {
		this.bolAtivo = bolAtivo;
	}

	public Boolean getBolRealizado() {
		return bolRealizado;
	}

	public void setBolRealizado(Boolean bolRealizado) {
		this.bolRealizado = bolRealizado;
	}

	public Boolean getBolDisponibilizado() {
		return bolDisponibilizado;
	}

	public void setBolDisponibilizado(Boolean bolDisponibilizado) {
		this.bolDisponibilizado = bolDisponibilizado;
	}

	@Override
	public String toString() {
		return "EscalaTrabalhoDTO [id=" + id + ", empresaProfissionalDTO=" + empresaProfissionalDTO
				+ ", empresaUnidadeGestaoDTO=" + empresaUnidadeGestaoDTO + ", dataHoraEntrada=" + dataHoraEntrada
				+ ", dataHoraSaida=" + dataHoraSaida + ", bolAtivo=" + bolAtivo + ", bolRealizado=" + bolRealizado
				+ ", bolDisponibilizado=" + bolDisponibilizado + "]";
	}
}