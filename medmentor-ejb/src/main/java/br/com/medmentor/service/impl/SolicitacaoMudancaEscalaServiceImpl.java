package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.medmentor.dao.EmpresaProfissionalDAO;
import br.com.medmentor.dao.EscalaTrabalhoDAO;
import br.com.medmentor.dao.NotificacaoDAO;
import br.com.medmentor.dao.SolicitacaoMudancaEscalaDAO;
import br.com.medmentor.dto.SolicitacaoMudancaEscalaDTO;
import br.com.medmentor.enums.TipoSolicitacaoMudancaEscala;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroSolicitacaoMudancaEscalaDTO;
import br.com.medmentor.mapper.SolicitacaoMudancaEscalaMapper;
import br.com.medmentor.mobile.filtro.dto.FiltroSolicitacaoMudancaDTO;
import br.com.medmentor.model.EmpresaProfissional;
import br.com.medmentor.model.EscalaTrabalho;
import br.com.medmentor.model.Notificacao;
import br.com.medmentor.model.SolicitacaoMudancaEscala;
import br.com.medmentor.service.SolicitacaoMudancaEscalaService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Stateless 
public class SolicitacaoMudancaEscalaServiceImpl implements SolicitacaoMudancaEscalaService {
	
	@Inject 
    private EmpresaProfissionalDAO empresaProfissionalDAO;
    
	@Inject
	private EscalaTrabalhoDAO escalaTrabalhoDAO;
	
	@Inject
	private NotificacaoDAO notificacaoDAO;
	
    @Inject 
    private SolicitacaoMudancaEscalaDAO solicitacaoMudancaEscalaDAO; 
    
    @Inject
    private SolicitacaoMudancaEscalaMapper solicitacaoMudancaEscalaMapper;

    public SolicitacaoMudancaEscalaServiceImpl() {

	}

	@Override
	@Transactional
	public SolicitacaoMudancaEscalaDTO incluiSolicitacaoMudancaEscala(SolicitacaoMudancaEscalaDTO solicitacaoMudancaEscalaDTO) throws MedmentorException {
		SolicitacaoMudancaEscala solicitacaoMudancaEscala = solicitacaoMudancaEscalaMapper.toEntity(solicitacaoMudancaEscalaDTO);		
		try {			
			
			EscalaTrabalho escalaTrabalho = escalaTrabalhoDAO.findById(solicitacaoMudancaEscala.getEscalaTrabalhoSolicitada().getId());
			
			if (escalaTrabalho.getEmpresaProfissional()!=null) {
			
				if (!escalaTrabalho.getBolAtivo()) {
					throw new MedmentorException("Não é possível solicitar uma escala inativa!");
				}	
				
				if (escalaTrabalho.getDataHoraEntrada().isBefore(LocalDateTime.now())) {
					throw new MedmentorException("Não é possível solicitar uma escala de trabalho com data inferior a hoje!");
				}
				
				if (this.isPossuiSolicitacaoMudanca(solicitacaoMudancaEscala)) {
					throw new MedmentorException("Não é possível solicitar uma escala de trabalho que ja tenha sido solicitada anteriormente!");
				}				
			} else {
				
				solicitacaoMudancaEscala.setBolAcatada(true);
				
				EscalaTrabalho novaEscalaTrabalho = escalaTrabalho;
				novaEscalaTrabalho.setEmpresaProfissional(solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante());
				escalaTrabalhoDAO.create(novaEscalaTrabalho);
				
				escalaTrabalho.setBolAtivo(false);
				escalaTrabalho.setBolRealizado(false);
				escalaTrabalhoDAO.update(novaEscalaTrabalho);
			}
			
			this.geraNotificacaoParaSolicitacaoMundanca(solicitacaoMudancaEscala, escalaTrabalho, TipoSolicitacaoMudancaEscala.INCLUSAO);
			solicitacaoMudancaEscala = solicitacaoMudancaEscalaDAO.create(solicitacaoMudancaEscala);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		

		return solicitacaoMudancaEscalaMapper.toDto(solicitacaoMudancaEscala);
	}
	
	@Override
	public void incluiSolicitacaoMudancaEscala(Integer idProfissional, Integer idEscala) throws MedmentorException {
		
		SolicitacaoMudancaEscala solicitacaoMudancaEscala = new SolicitacaoMudancaEscala();	
		
		try {			
			
			EscalaTrabalho escalaTrabalho = escalaTrabalhoDAO.findById(solicitacaoMudancaEscala.getEscalaTrabalhoSolicitada().getId());
			solicitacaoMudancaEscala.setEscalaTrabalhoSolicitada(escalaTrabalho);

			EmpresaProfissional empresaProfissional = empresaProfissionalDAO.findByProfissional(idProfissional);
			solicitacaoMudancaEscala.setEmpresaProfissionalSolicitante(empresaProfissional);
			
			if (escalaTrabalho.getEmpresaProfissional()!=null) {
			
				if (!escalaTrabalho.getBolAtivo()) {
					throw new MedmentorException("Não é possível solicitar uma escala inativa!");
				}	
				
				if (escalaTrabalho.getDataHoraEntrada().isBefore(LocalDateTime.now())) {
					throw new MedmentorException("Não é possível solicitar uma escala de trabalho com data inferior a hoje!");
				}
				
				if (this.isPossuiSolicitacaoMudanca(solicitacaoMudancaEscala)) {
					throw new MedmentorException("Não é possível solicitar uma escala de trabalho que ja tenha sido solicitada anteriormente!");
				}				
			} else {
				
				solicitacaoMudancaEscala.setBolAcatada(true);
				
				EscalaTrabalho novaEscalaTrabalho = escalaTrabalho;
				novaEscalaTrabalho.setEmpresaProfissional(solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante());
				escalaTrabalhoDAO.create(novaEscalaTrabalho);
				
				escalaTrabalho.setBolAtivo(false);
				escalaTrabalho.setBolRealizado(false);
				escalaTrabalhoDAO.update(novaEscalaTrabalho);
			}
			
			this.geraNotificacaoParaSolicitacaoMundanca(solicitacaoMudancaEscala, escalaTrabalho, TipoSolicitacaoMudancaEscala.INCLUSAO);
			solicitacaoMudancaEscala = solicitacaoMudancaEscalaDAO.create(solicitacaoMudancaEscala);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
	}	

	@Override
	public void excluiSolicitacaoMudancaEscala(Integer id) throws MedmentorException {
		try {
			SolicitacaoMudancaEscala solicitacaoMudancaEscala = solicitacaoMudancaEscalaDAO.findById(id);
			
			if (solicitacaoMudancaEscala.getBolAcatada()) {
				throw new MedmentorException("Não é possível excluir ma solicitação de mudança de escala que já tenha sido acatada pelo profissional cedente!");
			}
			
			EscalaTrabalho escalaTrabalho = escalaTrabalhoDAO.findById(solicitacaoMudancaEscala.getEscalaTrabalhoSolicitada().getId());			
			this.geraNotificacaoParaSolicitacaoMundanca(solicitacaoMudancaEscala, escalaTrabalho, TipoSolicitacaoMudancaEscala.EXCLUSAO);
			solicitacaoMudancaEscalaDAO.delete(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}				
	}

	@Override
	public void aceitaSolicitacaoMudancaEscala(Integer id) throws MedmentorException {						
		try {
			SolicitacaoMudancaEscala solicitacaoMudancaEscala = solicitacaoMudancaEscalaDAO.findById(id);
			EscalaTrabalho escalaTrabalho = solicitacaoMudancaEscala.getEscalaTrabalhoSolicitada();

			//Ao aceitar, inativa a solicitacao de mudanca
			solicitacaoMudancaEscala.setBolAcatada(true);
			solicitacaoMudancaEscala.setBolAtiva(false);
			solicitacaoMudancaEscala.setDataHoraAtualizacao(LocalDateTime.now());
			
			//Inativa a escala de trabalho anterior
			escalaTrabalho.setBolAtivo(false);
			escalaTrabalho.setBolRealizado(false);
			escalaTrabalhoDAO.update(escalaTrabalho);
			
			//Cria uma nova escala trabalho ativa
			EscalaTrabalho novaEscalaTrabalho = new EscalaTrabalho(); 
			novaEscalaTrabalho.setBolAtivo(true);
			novaEscalaTrabalho.setBolRealizado(false);
			novaEscalaTrabalho.setBolDisponibilizado(false);
			novaEscalaTrabalho.setId(null);
			novaEscalaTrabalho.setEmpresaProfissional(solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante());
			novaEscalaTrabalho.setDataHoraEntrada(escalaTrabalho.getDataHoraEntrada());
			novaEscalaTrabalho.setDataHoraSaida(escalaTrabalho.getDataHoraSaida());
			novaEscalaTrabalho.setEmpresaUnidadeGestao(escalaTrabalho.getEmpresaUnidadeGestao());		
			escalaTrabalhoDAO.create(novaEscalaTrabalho);	

			this.geraNotificacaoParaSolicitacaoMundanca(solicitacaoMudancaEscala, escalaTrabalho, TipoSolicitacaoMudancaEscala.ACEITAR);
			solicitacaoMudancaEscalaDAO.update(solicitacaoMudancaEscala);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}
	
	@Override
	public void recusaSolicitacaoMudancaEscala(Integer id) throws MedmentorException {						
		try {
			SolicitacaoMudancaEscala solicitacaoMudancaEscala = solicitacaoMudancaEscalaDAO.findById(id);
			EscalaTrabalho escalaTrabalho = solicitacaoMudancaEscala.getEscalaTrabalhoSolicitada();

			//Ao aceitar, inativa a solicitacao de mudanca
			solicitacaoMudancaEscala.setBolAcatada(false);
			solicitacaoMudancaEscala.setBolAtiva(false);
			solicitacaoMudancaEscala.setDataHoraAtualizacao(LocalDateTime.now());
			
			this.geraNotificacaoParaSolicitacaoMundanca(solicitacaoMudancaEscala, escalaTrabalho, TipoSolicitacaoMudancaEscala.RECUSAR);
			solicitacaoMudancaEscalaDAO.update(solicitacaoMudancaEscala);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}	

	@Override
	public SolicitacaoMudancaEscalaDTO recuperaSolicitacaoMudancaEscalaPorId(Integer id) throws MedmentorException {
		SolicitacaoMudancaEscala escalaTrabalho;
		try {
			escalaTrabalho = solicitacaoMudancaEscalaDAO.findById(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return solicitacaoMudancaEscalaMapper.toDto(escalaTrabalho);
	}

	@Override
	public List<SolicitacaoMudancaEscalaDTO> recuperaListaSolicitacaoMudancaEscala() throws MedmentorException {
		List<SolicitacaoMudancaEscalaDTO> listaDto = new ArrayList<SolicitacaoMudancaEscalaDTO>();
		try {
			listaDto = solicitacaoMudancaEscalaMapper.toListDto(solicitacaoMudancaEscalaDAO.findAll());
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}

	@Override
	public List<SolicitacaoMudancaEscalaDTO> recuperaListaSolicitacaoMudancaEscalaPorFiltro(FiltroSolicitacaoMudancaEscalaDTO filtroSolicitacaoMudancaEscalaDTO)
			throws MedmentorException {
		List<SolicitacaoMudancaEscalaDTO> listaDto = new ArrayList<SolicitacaoMudancaEscalaDTO>();
		try {
			LocalDate dataInicio = null;
			if (filtroSolicitacaoMudancaEscalaDTO.getDataInicio()!=null) {
				dataInicio = LocalDate.parse(filtroSolicitacaoMudancaEscalaDTO.getDataInicio());
			}
			
			LocalDate dataFim = null;
			if (filtroSolicitacaoMudancaEscalaDTO.getDataFim()!=null) {
				dataFim = LocalDate.parse(filtroSolicitacaoMudancaEscalaDTO.getDataFim());
			}
			
			listaDto = solicitacaoMudancaEscalaMapper.toListDto(solicitacaoMudancaEscalaDAO.
					findByFiltros(filtroSolicitacaoMudancaEscalaDTO.getIdEmpresaProfissional(),  
							filtroSolicitacaoMudancaEscalaDTO.getIdEscalaTrabalho(), dataInicio, dataFim));
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return listaDto;
	}
	

	@Override
	public List<SolicitacaoMudancaEscalaDTO> recuperaListaSolicitacaoMudancaEscalaPorFiltro(
			FiltroSolicitacaoMudancaDTO filtroSolicitacaoEscalaDTO) throws MedmentorException {
		List<SolicitacaoMudancaEscalaDTO> listaDto = new ArrayList<SolicitacaoMudancaEscalaDTO>();
		try {
			LocalDate dataInicio = null;
			if (filtroSolicitacaoEscalaDTO.getDataInicio()!=null) {
				dataInicio = LocalDate.parse(filtroSolicitacaoEscalaDTO.getDataInicio());
			}
			
			LocalDate dataFim = null;
			if (filtroSolicitacaoEscalaDTO.getDataFim()!=null) {
				dataFim = LocalDate.parse(filtroSolicitacaoEscalaDTO.getDataFim());
			}
			
			listaDto = solicitacaoMudancaEscalaMapper.toListDto(solicitacaoMudancaEscalaDAO.
					findByFiltros(filtroSolicitacaoEscalaDTO.getIdUnidadeSaude(), dataInicio, dataFim));
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return listaDto;
	}	
	
	private Boolean isPossuiSolicitacaoMudanca(SolicitacaoMudancaEscala solicitacaoMudancaEscala) throws MedmentorException {
		
		Boolean isPossuiSolicitacaoMudanca = false;		
		try {
			List<SolicitacaoMudancaEscala> listaSolicitacaoMudancaEscala = solicitacaoMudancaEscalaDAO.
					findByFiltros(solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante().getId(),
							solicitacaoMudancaEscala.getEscalaTrabalhoSolicitada().getId(), null, null);
			if ((listaSolicitacaoMudancaEscala!=null)&&(listaSolicitacaoMudancaEscala.size()>0)) {
				isPossuiSolicitacaoMudanca = true;
			} else {
				isPossuiSolicitacaoMudanca = false;
			}
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
		
		return isPossuiSolicitacaoMudanca;
	}
	
	private void geraNotificacaoParaSolicitacaoMundanca(SolicitacaoMudancaEscala solicitacaoMudancaEscala, 
			EscalaTrabalho escalaTrabalho, TipoSolicitacaoMudancaEscala tipoSolicitacao) throws MedmentorException {
		
		try {
			DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			
			Notificacao notificacao = new Notificacao();
			notificacao.setBolLida(false);
			notificacao.setDataHora(LocalDateTime.now());
			
			if (escalaTrabalho.getEmpresaProfissional()!=null) {
			
				if (tipoSolicitacao.getCodigo() == TipoSolicitacaoMudancaEscala.INCLUSAO.getCodigo()) {
					notificacao.setEmpresaProfissional(escalaTrabalho.getEmpresaProfissional());
					notificacao.setDescricao("O profissional " + solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante().getProfissional().getPessoaFisica().getPessoa().getNomePessoa() 
							+ ", representado pela empresa " + solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante().getEmpresa().getNomeFantasia() 
							+ ", possui interesse na sua escala de trabalho com inicio em " + escalaTrabalho.getDataHoraEntrada().format(formatacao) 
							+ " e fim em " + escalaTrabalho.getDataHoraSaida().format(formatacao) 
							+ ", na unidade de saúde " 
							+ escalaTrabalho.getEmpresaUnidadeGestao().getEmpresa().getNomeFantasia() + ".");
				} else if (tipoSolicitacao.getCodigo() == TipoSolicitacaoMudancaEscala.EXCLUSAO.getCodigo()) {
					notificacao.setEmpresaProfissional(escalaTrabalho.getEmpresaProfissional());
					notificacao.setDescricao("O profissional " + solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante().getProfissional().getPessoaFisica().getPessoa().getNomePessoa() 
							+ ", representado pela empresa " + solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante().getEmpresa().getNomeFantasia() 
							+ ", retirou o interesse na sua escala de trabalho com inicio em " + escalaTrabalho.getDataHoraEntrada().format(formatacao) 
							+ " e fim em " + escalaTrabalho.getDataHoraSaida().format(formatacao) 
							+ ", na unidade de saúde " 
							+ escalaTrabalho.getEmpresaUnidadeGestao().getEmpresa().getNomeFantasia() + ".");
				} else if (tipoSolicitacao.getCodigo() == TipoSolicitacaoMudancaEscala.ACEITAR.getCodigo()) {
					notificacao.setEmpresaProfissional(solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante());
					notificacao.setDescricao("O profissional " + escalaTrabalho.getEmpresaProfissional().getProfissional().getPessoaFisica().getPessoa().getNomePessoa() 
							+ ", aceitou o seu interesse na sua escala de trabalho com inicio em " + escalaTrabalho.getDataHoraEntrada().format(formatacao) 
							+ " e fim em " + escalaTrabalho.getDataHoraSaida().format(formatacao) 
							+ ", na unidade de saúde " 
							+ escalaTrabalho.getEmpresaUnidadeGestao().getEmpresa().getNomeFantasia() + ".");
				} else if (tipoSolicitacao.getCodigo() == TipoSolicitacaoMudancaEscala.RECUSAR.getCodigo()) {
					notificacao.setEmpresaProfissional(solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante());
					notificacao.setDescricao("O profissional " + escalaTrabalho.getEmpresaProfissional().getProfissional().getPessoaFisica().getPessoa().getNomePessoa() 
							+ ", recusou o seu interesse na sua escala de trabalho com inicio em " + escalaTrabalho.getDataHoraEntrada().format(formatacao) 
							+ " e fim em " + escalaTrabalho.getDataHoraSaida().format(formatacao) 
							+ ", na unidade de saúde " 
							+ escalaTrabalho.getEmpresaUnidadeGestao().getEmpresa().getNomeFantasia() + ".");
				}
			} else {
				notificacao.setDescricao("Você assumiu a escala de trabalho com inicio em " + escalaTrabalho.getDataHoraEntrada().format(formatacao) 
						+ " e fim em " + escalaTrabalho.getDataHoraSaida().format(formatacao) 
						+ ", na unidade de saúde " 
						+ escalaTrabalho.getEmpresaUnidadeGestao().getEmpresa().getNomeFantasia() + ".");
				notificacao.setEmpresaProfissional(solicitacaoMudancaEscala.getEmpresaProfissionalSolicitante());
			}
						
			notificacao.setEmpresaUnidadeGestao(escalaTrabalho.getEmpresaUnidadeGestao());
			notificacaoDAO.create(notificacao);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
	}
}
