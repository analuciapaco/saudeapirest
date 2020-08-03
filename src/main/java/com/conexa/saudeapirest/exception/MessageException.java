/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */


package com.conexa.saudeapirest.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

public class MessageException extends RuntimeException {


	private static final long serialVersionUID = 1767749817939741532L;

	private Long codigoStatus;
	private HttpStatus status;
	private List<String> mensagens;

	public MessageException(String mensagem) {
		super(mensagem);
		this.status = HttpStatus.BAD_REQUEST;
	}

	public MessageException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	public MessageException(String mensagem, HttpStatus status) {
		super(mensagem);
		this.status = status;
	}

	public MessageException(String mensagem, Long codigoStatus, HttpStatus status) {
		super(mensagem);
		this.codigoStatus = codigoStatus;
		this.status = status;
	}

	public MessageException(String mensagem, List<String> mensagens, HttpStatus status) {
		super(mensagem);
		this.mensagens = mensagens;
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public Long getCodigoStatus() {
		return codigoStatus;
	}

	public List<String> getMensagens() {
		return mensagens;
	}
}
