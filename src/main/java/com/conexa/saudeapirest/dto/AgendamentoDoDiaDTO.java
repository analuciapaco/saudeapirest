/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AgendamentoDoDiaDTO {
	private String id_paciente;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime data_hora_atendimento;

	
	public String getId_paciente() {
		return id_paciente;
	}

	public void setId_paciente(String id_paciente) {
		this.id_paciente = id_paciente;
	}

	public void setData_hora_atendimento(LocalDateTime data_hora_atendimento) {
		this.data_hora_atendimento = data_hora_atendimento;
	}

	public LocalDateTime getData_hora_atendimento() {
		return data_hora_atendimento;
	}

}
