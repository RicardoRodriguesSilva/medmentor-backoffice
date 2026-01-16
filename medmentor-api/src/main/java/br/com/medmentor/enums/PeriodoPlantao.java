package br.com.medmentor.enums;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum PeriodoPlantao {
	
    MANHA(LocalTime.of(7, 0), LocalTime.of(13, 0), Duration.ofHours(6)),
    TARDE(LocalTime.of(13, 0), LocalTime.of(19, 0), Duration.ofHours(6)),
    NOITE(LocalTime.of(19, 0), LocalTime.of(7, 0), Duration.ofHours(12)); 

    private final LocalTime inicio;
    private final LocalTime fim;
    private final Duration duracao;

    PeriodoPlantao(LocalTime inicio, LocalTime fim, Duration duracao) {
        this.inicio = inicio;
        this.fim = fim;
        this.duracao = duracao;
    }

    public LocalTime getInicio() { return inicio; }
    public LocalTime getFim() { return fim; }
    public Duration getDuracao() { return duracao; }

    /**
     * Calcula a data/hora de início de um período para uma data específica.
     */
    public LocalDateTime getInicioDateTime(LocalDate date) {
        return date.atTime(inicio);
    }

    /**
     * Calcula a data/hora de término de um período para uma data específica.
     * Para o período Noturno, o término ocorre no dia subsequente.
     */
    public LocalDateTime getFimDateTime(LocalDate date) {
        if (this == NOITE) {
            return date.plusDays(1).atTime(fim);
        }
        return date.atTime(fim);
    }
}
