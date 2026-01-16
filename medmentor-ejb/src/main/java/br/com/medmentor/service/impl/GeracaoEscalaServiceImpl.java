package br.com.medmentor.service.impl;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.medmentor.dao.EmpresaProfissionalDAO;
import br.com.medmentor.dao.EscalaTrabalhoDAO;
import br.com.medmentor.dao.GeracaoEscalaDAO;
import br.com.medmentor.dao.ParametroDAO;
import br.com.medmentor.dto.GeracaoEscalaDTO;
import br.com.medmentor.enums.Parametro;
import br.com.medmentor.enums.PeriodoPlantao;
import br.com.medmentor.exception.MedmentorException;
import br.com.medmentor.filtro.dto.FiltroGeracaoEscalaDTO;
import br.com.medmentor.mapper.GeracaoEscalaMapper;
import br.com.medmentor.model.EmpresaProfissional;
import br.com.medmentor.model.EscalaTrabalho;
import br.com.medmentor.model.GeracaoEscala;
import br.com.medmentor.service.GeracaoEscalaService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless 
public class GeracaoEscalaServiceImpl implements GeracaoEscalaService {

	@Inject
	private EmpresaProfissionalDAO empresaProfissionalDAO;
	
	@Inject
	private EscalaTrabalhoDAO escalaTrabalhoDAO;
	
    @Inject 
    private GeracaoEscalaDAO geracaoEscalaDAO;
    
    @Inject 
    private ParametroDAO parametroDAO;      
    
    @Inject
    private GeracaoEscalaMapper geracaoEscalaMapper;

    public GeracaoEscalaServiceImpl() {

	}

	@Override
	public GeracaoEscalaDTO incluirGeracaoEscala(GeracaoEscalaDTO geracaoEscalaDTO) throws MedmentorException {
		GeracaoEscala geracaoEscala = geracaoEscalaMapper.toEntity(geracaoEscalaDTO);
		
		try {			
			Boolean isEscalaGerada = geracaoEscalaDAO.isEscalaGeradaByEmpresaUndidadeGestaoEData(
					geracaoEscala.getEmpresaUnidadeGestao().getId(), geracaoEscala.getDataGeracao());
			if (!isEscalaGerada) {
				geracaoEscala = geracaoEscalaDAO.create(geracaoEscala);

				//Gerar escala para o mes
				List<EmpresaProfissional> profssionais = empresaProfissionalDAO.findByEmpresaUnidadeGestao(geracaoEscalaDTO.getEmpresaUnidadeGestaoDTO().getId());
				br.com.medmentor.model.Parametro parametro = parametroDAO.findById(Parametro.QTD_HORAS_PLANTONISTA_DIA.getCodigo());
				Integer quantidadeHorasPlantonistaDia = Integer.valueOf(parametro.getValor());				
				
				List<EscalaTrabalho> listaEscalasTrabalho = this.gerarEscalaMensal(geracaoEscala, YearMonth.from(geracaoEscala.getDataGeracao()),
						profssionais, quantidadeHorasPlantonistaDia);
				
				if ((listaEscalasTrabalho!=null)&&(listaEscalasTrabalho.size()>0)) {
					for (EscalaTrabalho novaEscala: listaEscalasTrabalho) {
						escalaTrabalhoDAO.create(novaEscala);
					}
				}			
			} else {
				throw new MedmentorException("Existe uma escala gerada para essas informações!");
			}
			
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}		

		return geracaoEscalaMapper.toDto(geracaoEscala);
	}

	@Override
	public void excluirGeracaoEscala(Integer id) throws MedmentorException {
		try {
			
			GeracaoEscala geracaoEscala = geracaoEscalaDAO.findById(id);
			Boolean isEscalaTrabalho = escalaTrabalhoDAO.isEscalaTrabalhoByEmpresaUndidadeGestaoEData(geracaoEscala.getEmpresaUnidadeGestao().getId(), 
					geracaoEscala.getDataGeracao());
			
			if (isEscalaTrabalho) {
				escalaTrabalhoDAO.deleteAllByEmpresaUndidadeGestaoEData(geracaoEscala.getEmpresaUnidadeGestao().getId(), 
					geracaoEscala.getDataGeracao());
			}
			
			geracaoEscalaDAO.delete(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}				
	}

	@Override
	public void alterarGeracaoEscala(GeracaoEscalaDTO geracaoEscalaDTO) throws MedmentorException {
		
		GeracaoEscala escalaTrabalho = geracaoEscalaMapper.toEntity(geracaoEscalaDTO);
		try {
			geracaoEscalaDAO.update(escalaTrabalho);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}	
	}

	@Override
	public GeracaoEscalaDTO recuperarGeracaoEscalaPorId(Integer id) throws MedmentorException {
		GeracaoEscala escalaTrabalho;
		try {
			escalaTrabalho = geracaoEscalaDAO.findById(id);
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return geracaoEscalaMapper.toDto(escalaTrabalho);
	}

	@Override
	public List<GeracaoEscalaDTO> recuperarListaGeracaoEscala() throws MedmentorException {
		List<GeracaoEscalaDTO> listaDto = new ArrayList<GeracaoEscalaDTO>();
		try {
			listaDto = geracaoEscalaMapper.toListDto(geracaoEscalaDAO.findAll());
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		return listaDto;
	}

	@Override
	public List<GeracaoEscalaDTO> recuperarListaGeracaoEscalaPorFiltro(FiltroGeracaoEscalaDTO filtroGeracaoEscalaDTO)
			throws MedmentorException {
		List<GeracaoEscalaDTO> listaDto = new ArrayList<GeracaoEscalaDTO>();
		try {
			LocalDate dataInicio = null;
			if (filtroGeracaoEscalaDTO.getDataInicio()!=null) {
				dataInicio = LocalDate.parse(filtroGeracaoEscalaDTO.getDataInicio());
			}
			
			LocalDate dataFim = null;
			if (filtroGeracaoEscalaDTO.getDataFim()!=null) {
				dataFim = LocalDate.parse(filtroGeracaoEscalaDTO.getDataFim());
			}
			
			listaDto = geracaoEscalaMapper.toListDto(geracaoEscalaDAO.
					findByFiltros(filtroGeracaoEscalaDTO.getIdEmpresaUnidadeGestao(), dataInicio, dataFim));
		} catch (SQLException e) {
			throw new MedmentorException(e.getMessage(), e.getCause());
		}
		
		return listaDto;
	}
	
	private List<EscalaTrabalho> gerarEscalaMensal(GeracaoEscala geracaoEscala, YearMonth yearMonth, 
			List<EmpresaProfissional> profssionaisDisponiveis, Integer quantidadeHorasPlantonistaDia) {
        List<EscalaTrabalho> escalaCompleta = new ArrayList<>();
        
		// Mapas para gerenciar a disponibilidade de cada profissional ao longo do tempo.
        // professionalNextAvailableTime: Data/hora em que o profissional estará livre para iniciar seu *próximo* plantão.
        Map<Integer, LocalDateTime> professionalNextAvailableTime = new HashMap<>();
        // professionalLastShiftEndTime: Data/hora em que o profissional terminou seu *último* plantão.
        Map<Integer, LocalDateTime> professionalLastShiftEndTime = new HashMap<>();
        // professionalLastShiftDuration: Duração em horas do *último* plantão do profissional.
        Map<Integer, Duration> professionalLastShiftDuration = new HashMap<>();

        // Inicializa a disponibilidade de todos os profissionais para o início do mês.
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDateTime initialAvailability = startOfMonth.atStartOfDay();
        for (EmpresaProfissional profissional : profssionaisDisponiveis) {
            professionalNextAvailableTime.put(profissional.getId(), initialAvailability);
            professionalLastShiftEndTime.put(profissional.getId(), null); // Inicialmente sem plantão anterior
            professionalLastShiftDuration.put(profissional.getId(), null); // Inicialmente sem plantão anterior
        }

        // Loop por cada dia do mês (Regra 4: Deverá haver plantão em todos os dias do mês)
        for (LocalDate currentDate = startOfMonth; !currentDate.isAfter(yearMonth.atEndOfMonth()); currentDate = currentDate.plusDays(1)) {

            // Loop por cada período de plantão no dia (MANHA, TARDE, NOITE)
            for (PeriodoPlantao periodo : PeriodoPlantao.values()) {
                LocalDateTime shiftStartDateTime = periodo.getInicioDateTime(currentDate);
                LocalDateTime shiftEndDateTime = periodo.getFimDateTime(currentDate);
                int requiredProfessionals = geracaoEscala.getEmpresaUnidadeGestao().getNumeroPlantonistaPeriodo(); // Regra 2
                int assignedCount = 0;

                // 1. Filtrar profissionais elegíveis para este turno:
                // - Que estejam disponíveis (nextAvailableTime <= shiftStartDateTime)
                // - Ordenar por disponibilidade (quem está livre há mais tempo tem prioridade) e depois ID para consistência.
                List<EmpresaProfissional> eligibleProfessionals = profssionaisDisponiveis.stream()
                        .filter(p -> {
                            LocalDateTime nextAvailable = professionalNextAvailableTime.getOrDefault(p.getId(), initialAvailability);
                            return !shiftStartDateTime.isBefore(nextAvailable); // Início do plantão não pode ser antes da disponibilidade
                        })
                        .sorted(Comparator.comparing((EmpresaProfissional p) -> professionalNextAvailableTime.getOrDefault(p.getId(), initialAvailability))
                                        .thenComparing(EmpresaProfissional::getId)) // Para desempate e ordem estável
                        .collect(Collectors.toList());

                // Opcional: Embaralhar para distribuir os plantões de forma mais equitativa entre os elegíveis
                // Collections.shuffle(eligibleProfessionals);

                // 2. Atribuir profissionais às vagas necessárias para o período
                for (EmpresaProfissional profissional : eligibleProfessionals) {
                    if (assignedCount >= requiredProfessionals) {
                        break; // Já preencheu todas as vagas para este período
                    }

                    // Cria a entrada da escala para o profissional
                    EscalaTrabalho escala = new EscalaTrabalho(
                            0,
                            profissional,
                            geracaoEscala.getEmpresaUnidadeGestao(),
                            shiftStartDateTime,
                            shiftEndDateTime,
                            true, // bolAtivo (Regra 6)
                            false // bolRealizado (Regra 6)
                    );
                    escalaCompleta.add(escala);
                    assignedCount++;

                    // --- ATUALIZAÇÃO DA DISPONIBILIDADE DO PROFISSIONAL (Regra 5) ---
                    LocalDateTime currentShiftEnd = shiftEndDateTime;
                    Duration currentShiftDuration = periodo.getDuracao();
                    LocalDateTime newNextAvailableTime = currentShiftEnd; // Por padrão, disponível após o término do plantão

                    // Regra 5: Se o médico pegue 12 horas seguidas de plantão, deve ter folga de 24 horas.
                    // Cenário 1: O plantão atual já é de 12 horas (ex: Período NOITE)
                    if (currentShiftDuration.toHours() == 12) {
                        newNextAvailableTime = currentShiftEnd.plusHours(24);
                    }
                    // Cenário 2: O plantão atual é de 6 horas (MANHA ou TARDE) E foi precedido por outro de 6 horas no mesmo dia
                    else if (currentShiftDuration.toHours() == 6) {
                        LocalDateTime lastShiftEndTime = professionalLastShiftEndTime.get(profissional.getId());
                        Duration lastShiftDuration = professionalLastShiftDuration.get(profissional.getId());

                        // Verifica se o plantão atual começou imediatamente após o anterior E se ambos somam 12h
                        if (lastShiftEndTime != null && lastShiftDuration != null &&
                            shiftStartDateTime.isEqual(lastShiftEndTime) && // Início do atual é igual ao fim do anterior (consecutivo)
                            lastShiftDuration.toHours() == 6) { // E o anterior também foi de 6 horas
                            newNextAvailableTime = currentShiftEnd.plusHours(24);
                        }
                    }

                    // Atualiza os mapas de disponibilidade para o profissional
                    professionalNextAvailableTime.put(profissional.getId(), newNextAvailableTime);
                    professionalLastShiftEndTime.put(profissional.getId(), currentShiftEnd);
                    professionalLastShiftDuration.put(profissional.getId(), currentShiftDuration);
                }

                // 3. Se houver vagas não preenchidas, adiciona entradas com profissional nulo (Regra 6)
                while (assignedCount < requiredProfessionals) {
                    EscalaTrabalho escalaVazia = new EscalaTrabalho(
                    		0,
                            null, // EmpresaProfissional nula
                            geracaoEscala.getEmpresaUnidadeGestao(),
                            shiftStartDateTime,
                            shiftEndDateTime,
                            true, // bolAtivo (Regra 6)
                            false // bolRealizado (Regra 6)
                    );
                    escalaCompleta.add(escalaVazia);
                    assignedCount++;
                }
            }
        }

        // Ordena a escala final por data/hora de entrada, e depois por nome do profissional para melhor legibilidade
        escalaCompleta.sort(Comparator.comparing(EscalaTrabalho::getDataHoraEntrada)
                                    .thenComparing(e -> e.getEmpresaProfissional() != null ? e.getEmpresaProfissional().getId() : null)); // Nulos ficam por último

        return escalaCompleta;
    }
}
