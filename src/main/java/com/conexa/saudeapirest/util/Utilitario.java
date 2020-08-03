/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.util;

import java.rmi.server.UID;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utilitario {

	public Date converte(LocalDate data) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date dataConvertida = Date.from(data.atStartOfDay(defaultZoneId).toInstant());
		return dataConvertida;
	}

	public String gerarId() {
	UID idRandomico = new UID();
	String idString = idRandomico.toString();
	String userId = idString.substring(1, 8);
	return userId;
	}

}
