/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "usuario")
public class Usuario {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonProperty("usuario")
	@Column(name = "usuario", unique = true, nullable = false)
	private String usuario;

	@JsonProperty("senha")
	private String senha;

	@JsonProperty("medico")
	@JoinColumn(name = "id_medico", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MEDICO_USUARIO"))
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	Medico medico;

	private String token;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
