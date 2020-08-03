/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.conexa.saudeapirest.models.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

	Agendamento findById(long id);

	@Query("select agendamentos from Agendamento agendamentos where DATE(agendamentos.data_hora_atendimento)  = :dataDia ")
	List<Agendamento> findByAgendamentosDoDia(Date dataDia);
}
