/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.dto;

import java.util.List;

public class UsuarioMedicoDTO {

	private String token;
	private String medico;
	private String especialidade;
	private List<AgendamentoDoDiaDTO> agendamentos_hoje;

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public List getAgendamentos_hoje() {
		return agendamentos_hoje;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public void setAgendamentos_hoje(List<AgendamentoDoDiaDTO> agendamentos_hoje) {
		this.agendamentos_hoje = agendamentos_hoje;
	}

	public String getMedico() {
		return medico;
	}

	public void setMedico(String medico) {
		this.medico = medico;
	}

}
