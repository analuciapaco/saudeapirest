/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexa.saudeapirest.models.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

	Medico findById(long id);

}
