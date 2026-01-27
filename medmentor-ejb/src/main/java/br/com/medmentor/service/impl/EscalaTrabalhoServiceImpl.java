package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.medmentor.dao.EmpresaProfissionalDAO;
import br.com.medmentor.dao.EscalaTrabalhoDAO;
import br.com.medmentor.dao.NotificacaoDAO;
import br.com.medmentor.dto.ChaveHorasEscalaTrabalhoDTO;
import br.com.medmentor.dto.EscalaTrabalhoDTO;
import br.com.medmentor.dto.HorasEscalaTrabalhoDTO;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroEscalaTrabalhoDTO;
import br.com.medmentor.filtro.dto.FiltroHorasEscalaTrabalhoDTO;
import br.com.medmentor.mapper.EscalaTrabalhoMapper;
import br.com.medmentor.mobile.filtro.dto.FiltroEscalaDTO;
import br.com.medmentor.model.EmpresaProfissional;
import br.com.medmentor.model.EscalaTrabalho;
import br.com.medmentor.model.Notificacao;
import br.com.medmentor.service.EscalaTrabalhoService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Stateless 
public class EscalaTrabalhoServiceImpl implements EscalaTrabalhoService {

	@Inject 
	private EmpresaProfissionalDAO empresaProfissionalDAO;
	
    @Inject 
    private EscalaTrabalhoDAO escalaTrabalhoDAO;
    
    @Inject
    private NotificacaoDAO notificacaoDAO;
    
    @Inject
    private EscalaTrabalhoMapper escalaTrabalhoMapper;

    public EscalaTrabalhoServiceImpl() {

	}

	@Override
	public EscalaTrabalhoDTO incluiEscalaTrabalho(EscalaTrabalhoDTO escalaTrabalhoDTO) throws MedmentorException {
		EscalaTrabalho escalaTrabalho = escalaTrabalhoMapper.toEntity(escalaTrabalhoDTO);
		
		try {
			escalaTrabalho = escalaTrabalhoDAO.create(escalaTrabalho);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		

		return escalaTrabalhoMapper.toDto(escalaTrabalho);
	}

	@Override
	public void excluiEscalaTrabalho(Integer id) throws MedmentorException {
		try {
			escalaTrabalhoDAO.delete(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}				
	}

	@Override
	public void alteraEscalaTrabalho(EscalaTrabalhoDTO escalaTrabalhoDTO) throws MedmentorException {
		
		EscalaTrabalho escalaTrabalho = escalaTrabalhoMapper.toEntity(escalaTrabalhoDTO);
		try {
			escalaTrabalhoDAO.update(escalaTrabalho);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public EscalaTrabalhoDTO recuperaEscalaTrabalhoPorId(Integer id) throws MedmentorException {
		EscalaTrabalho escalaTrabalho;
		try {
			escalaTrabalho = escalaTrabalhoDAO.findById(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return escalaTrabalhoMapper.toDto(escalaTrabalho);
	}

	@Override
	public List<EscalaTrabalhoDTO> recuperaListaEscalaTrabalho() throws MedmentorException {
		List<EscalaTrabalhoDTO> listaDto = new ArrayList<EscalaTrabalhoDTO>();
		try {
			listaDto = escalaTrabalhoMapper.toListDto(escalaTrabalhoDAO.findAll());
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}

	@Override
	public List<EscalaTrabalhoDTO> recuperaListaEscalaTrabalhoPorFiltro(FiltroEscalaTrabalhoDTO filtroEscalaTrabalhoDTO)
			throws MedmentorException {
		List<EscalaTrabalhoDTO> listaDto = new ArrayList<EscalaTrabalhoDTO>();
		try {
			
			LocalDate dataInicio = null;
			if (filtroEscalaTrabalhoDTO.getDataInicio()!=null) {
				dataInicio = LocalDate.parse(filtroEscalaTrabalhoDTO.getDataInicio());
			}
			
			LocalDate dataFim = null;
			if (filtroEscalaTrabalhoDTO.getDataFim()!=null) {
				dataFim = LocalDate.parse(filtroEscalaTrabalhoDTO.getDataFim());
			}			
			
			listaDto = escalaTrabalhoMapper.toListDto(escalaTrabalhoDAO.findByFiltros(
					filtroEscalaTrabalhoDTO.getIdEmpresaProfissional(), filtroEscalaTrabalhoDTO.getIdEmpresaUnidadeGestao(), dataInicio, dataFim));
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}

	@Override
	public List<EscalaTrabalhoDTO> recuperaListaEscalaTrabalhoPorFiltro(FiltroEscalaDTO filtroEscalaDTO)
			throws MedmentorException {
		List<EscalaTrabalhoDTO> listaDto = new ArrayList<EscalaTrabalhoDTO>();
		try {
			
			LocalDate dataInicio = null;
			if (filtroEscalaDTO.getDataInicio()!=null) {
				dataInicio = LocalDate.parse(filtroEscalaDTO.getDataInicio());
			}
			
			LocalDate dataFim = null;
			if (filtroEscalaDTO.getDataFim()!=null) {
				dataFim = LocalDate.parse(filtroEscalaDTO.getDataFim());
			}			
			
			listaDto = escalaTrabalhoMapper.toListDto(escalaTrabalhoDAO.findByFiltros(filtroEscalaDTO.getIdProfissional(), filtroEscalaDTO.getIdGestoraSaude(), 
					filtroEscalaDTO.getIdUnidadeSaude(), dataInicio, dataFim));
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}
	
	@Override
	public List<HorasEscalaTrabalhoDTO> recuperaListaHorasEscalaTrabalhoPorFiltro(
			FiltroHorasEscalaTrabalhoDTO filtroHorasTrabalhadasDTO) throws MedmentorException {
		List<HorasEscalaTrabalhoDTO> listaDto = new ArrayList<HorasEscalaTrabalhoDTO>();
		try {
			
			LocalDate dataInicio = null;
			if (filtroHorasTrabalhadasDTO.getDataInicio()!=null) {
				dataInicio = LocalDate.parse(filtroHorasTrabalhadasDTO.getDataInicio());
			}
			
			LocalDate dataFim = null;
			if (filtroHorasTrabalhadasDTO.getDataFim()!=null) {
				dataFim = LocalDate.parse(filtroHorasTrabalhadasDTO.getDataFim());
			}			
			
			List<EscalaTrabalhoDTO> listaEscalaTrabalhoDto = new ArrayList<EscalaTrabalhoDTO>();
			listaEscalaTrabalhoDto = escalaTrabalhoMapper.toListDto(escalaTrabalhoDAO.findByFiltros(filtroHorasTrabalhadasDTO.getIdProfissional(), filtroHorasTrabalhadasDTO.getIdGestoraSaude(), 
					filtroHorasTrabalhadasDTO.getIdUnidadeSaude(), dataInicio, dataFim));
			
			Map<ChaveHorasEscalaTrabalhoDTO, HorasEscalaTrabalhoDTO> mapaHorasEscala = new HashMap<ChaveHorasEscalaTrabalhoDTO, HorasEscalaTrabalhoDTO>();
			if ((listaEscalaTrabalhoDto!=null)&&(listaEscalaTrabalhoDto.size()>0)) {
				for (EscalaTrabalhoDTO escalaTrabalhoDto: listaEscalaTrabalhoDto) {
					
					ChaveHorasEscalaTrabalhoDTO chaveDTO = new ChaveHorasEscalaTrabalhoDTO();
					chaveDTO.setIdEmpresaGestao(escalaTrabalhoDto.getEmpresaUnidadeGestaoDTO().getEmpresaGestoraDTO().getId());
					chaveDTO.setIdEmpresaUnidadeGestao(escalaTrabalhoDto.getEmpresaUnidadeGestaoDTO().getId());
					chaveDTO.setIdEmpresaProfissional(escalaTrabalhoDto.getEmpresaProfissionalDTO().getId());
					
					HorasEscalaTrabalhoDTO horasEscalaTrabalhoDTO = null;
					if (mapaHorasEscala.containsKey(chaveDTO)) {
						horasEscalaTrabalhoDTO = mapaHorasEscala.get(chaveDTO);
					} else {
						horasEscalaTrabalhoDTO = new HorasEscalaTrabalhoDTO();
						horasEscalaTrabalhoDTO.setEmpresaGestaoDTO(escalaTrabalhoDto.getEmpresaUnidadeGestaoDTO().getEmpresaGestoraDTO());
						horasEscalaTrabalhoDTO.setEmpresaProfissionalDTO(escalaTrabalhoDto.getEmpresaProfissionalDTO());
						horasEscalaTrabalhoDTO.setEmpresaUnidadeGestaoDTO(escalaTrabalhoDto.getEmpresaUnidadeGestaoDTO());
					}
					
					if (escalaTrabalhoDto.getBolAtivo()) {
						if (escalaTrabalhoDto.getDataHoraSaida().isBefore(LocalDateTime.now())) {
							if (escalaTrabalhoDto.getBolRealizado() == true) {
								LocalTime totalHorasTrabalhadas = LocalTime.parse(horasEscalaTrabalhoDTO.getTotalHorasTrabalhadas());
								Duration tempoTrabalhado = Duration.between(escalaTrabalhoDto.getDataHoraEntrada(), escalaTrabalhoDto.getDataHoraSaida());
								totalHorasTrabalhadas = totalHorasTrabalhadas.plus(tempoTrabalhado);
								horasEscalaTrabalhoDTO.setTotalHorasTrabalhadas(totalHorasTrabalhadas.toString());
							} else {
								LocalTime totalHorasNaoTrabalhadas = LocalTime.parse(horasEscalaTrabalhoDTO.getTotalHorasNaoTrabalhadas());
								Duration tempoNaoTrabalhado = Duration.between(escalaTrabalhoDto.getDataHoraEntrada(), escalaTrabalhoDto.getDataHoraSaida());
								totalHorasNaoTrabalhadas = totalHorasNaoTrabalhadas.plus(tempoNaoTrabalhado);
								horasEscalaTrabalhoDTO.setTotalHorasNaoTrabalhadas(totalHorasNaoTrabalhadas.toString());
							}								
						} else {
							LocalTime totalHorasATrabalhar = LocalTime.parse(horasEscalaTrabalhoDTO.getTotalHorasATrabalhar());
							Duration tempoTrabalhado = Duration.between(escalaTrabalhoDto.getDataHoraEntrada(), escalaTrabalhoDto.getDataHoraSaida());
							totalHorasATrabalhar = totalHorasATrabalhar.plus(tempoTrabalhado);
							horasEscalaTrabalhoDTO.setTotalHorasATrabalhar(totalHorasATrabalhar.toString());
						}
						
						mapaHorasEscala.put(chaveDTO, horasEscalaTrabalhoDTO);
					}	
				}
			}
			
			listaDto = mapaHorasEscala.values().stream().collect(Collectors.toList());
			
			
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}

	@Override
	public void confirmaEscalaTrabalho(Integer id) throws MedmentorException {
		try {
			
			EscalaTrabalho escalaTrabalho = escalaTrabalhoDAO.findById(id);
			
			if (!escalaTrabalho.getBolAtivo()) {
				throw new MedmentorException("Não é possível confirmar uma escala inativa!");
			}	
			
			if (escalaTrabalho.getDataHoraSaida().isAfter(LocalDateTime.now())) {
				throw new MedmentorException("Não é possível confirmar uma escala de trabalho com data superior a hoje!");
			}
			
			escalaTrabalhoDAO.confirmaEscalaTrabalho(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}
	
	@Override
	public void cancelaEscalaTrabalho(Integer id) throws MedmentorException {
		try {
			
			EscalaTrabalho escalaTrabalho = escalaTrabalhoDAO.findById(id);
			
			if (!escalaTrabalho.getBolAtivo()) {
				throw new MedmentorException("Não é possível confirmar uma escala inativa!");
			}	
			
			if (escalaTrabalho.getDataHoraSaida().isAfter(LocalDateTime.now())) {
				throw new MedmentorException("Não é possível confirmar uma escala de trabalho com data superior a hoje!");
			}
			
			escalaTrabalhoDAO.cancelaEscalaTrabalho(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}	
	

	@Override
	@Transactional
	public void disponibilzaEscalaTrabalho(Integer id) throws MedmentorException {
		try {
			EscalaTrabalho escalaTrabalho = escalaTrabalhoDAO.findById(id);
			
			if (!escalaTrabalho.getBolAtivo()) {
				throw new MedmentorException("Não é possível disponibilizar uma escala inativa!");
			}	
			
			if (escalaTrabalho.getDataHoraEntrada().isBefore(LocalDateTime.now())) {
				throw new MedmentorException("Não é possível disponibilizar uma escala de trabalho com data inferior a hoje!");
			}
			
			List<EmpresaProfissional> listaProfssionais = empresaProfissionalDAO.findByEmpresaUnidadeGestao(
					escalaTrabalho.getEmpresaUnidadeGestao().getId());
			
			if ((listaProfssionais!=null)&&(listaProfssionais.size()>0)) {
				for (EmpresaProfissional empresaProfissional: listaProfssionais)
					if (empresaProfissional.getId()!=escalaTrabalho.getEmpresaProfissional().getId()) {
						
						DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
						
						Notificacao notificacao = new Notificacao();
						notificacao.setBolLida(false);
						notificacao.setDataHora(LocalDateTime.now());
						notificacao.setDescricao("Disponibilização da escala de trabalho com inicio em " + escalaTrabalho.getDataHoraEntrada().format(formatacao) 
								+ " e fim em " + escalaTrabalho.getDataHoraSaida().format(formatacao) 
								+ ", na unidade de saúde " 
								+ escalaTrabalho.getEmpresaUnidadeGestao().getEmpresa().getNomeFantasia() + ".");
						notificacao.setEmpresaProfissional(escalaTrabalho.getEmpresaProfissional());
						notificacao.setEmpresaUnidadeGestao(escalaTrabalho.getEmpresaUnidadeGestao());
						notificacaoDAO.create(notificacao);
					}					
			}			
			
			escalaTrabalhoDAO.disponibilzaEscalaTrabalho(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}	
}
