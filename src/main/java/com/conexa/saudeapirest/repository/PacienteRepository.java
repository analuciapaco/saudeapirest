/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexa.saudeapirest.models.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, String> {

	Optional<Paciente> findById(String id);

}
