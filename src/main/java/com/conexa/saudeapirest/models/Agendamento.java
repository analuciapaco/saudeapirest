/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "agendamento")
public class Agendamento {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonProperty("paciente")
	@JoinColumn(name = "id_paciente", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "FK_PACIENTE"))
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	Paciente paciente;

	@JsonProperty("medico")
	@JoinColumn(name = "id_medico", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "FK_MEDICO"))
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	Medico medico;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime data_hora_atendimento;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public LocalDateTime getData_hora_atendimento() {
		return data_hora_atendimento;
	}

	public void setData_hora_atendimento(LocalDateTime data_hora_atendimento) {
		this.data_hora_atendimento = data_hora_atendimento;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

}
