/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.dto;

import java.time.LocalDateTime;

public class AgendamentoDTO {
	private long id;
	private PacienteDTO paciente;
	private MedicoDTO medico;
	private LocalDateTime data_hora_atendimento;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PacienteDTO getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteDTO paciente) {
		this.paciente = paciente;
	}

	public MedicoDTO getMedico() {
		return medico;
	}

	public void setMedico(MedicoDTO medico) {
		this.medico = medico;
	}

	public LocalDateTime getData_hora_atendimento() {
		return data_hora_atendimento;
	}

	public void setData_hora_atendimento(LocalDateTime data_hora_atendimento) {
		this.data_hora_atendimento = data_hora_atendimento;
	}

}
