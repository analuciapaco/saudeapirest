/**
 * @Empresa: Empresa Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conexa.saudeapirest.dto.PacienteDTO;
import com.conexa.saudeapirest.exception.MessageException;
import com.conexa.saudeapirest.service.PacienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Pacientes da Empresa Conexa Saúde")
@CrossOrigin(origins = "*")
public class PacienteResource {
	@Autowired
	PacienteService pacienteService;

	@GetMapping("/pacientes")
	@ApiOperation(value = "Retorna um lista de Pacientes da Empresa Conexa Saúde")
	public List<PacienteDTO> listarPacientes() {
		try {
			return pacienteService.converterPacienteListParaPacienteDTOList(pacienteService.listarPacientes());
		} catch (Exception e) {
			return (List<PacienteDTO>) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@GetMapping("/paciente/{id}")
	@ApiOperation(value = "Retorna um paciente específico")
	public ResponseEntity<PacienteDTO> buscarPacientePorId(@PathVariable(value = "id") String id) {
		try {
			return ResponseEntity
					.ok(pacienteService.converterPacienteParaPacienteDTO(pacienteService.buscarPacientePorId(id)));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@PostMapping("/paciente")
	@ApiOperation(value = "Salva um paciente")
	public ResponseEntity<PacienteDTO> salvarPaciente(@RequestBody PacienteDTO pacienteDTO) throws MessageException {

		try {
			return ResponseEntity.ok(pacienteService.converterPacienteParaPacienteDTO(
					pacienteService.salvarPaciente(pacienteService.converterPacienteDTOParaPaciente(pacienteDTO))));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@DeleteMapping("/paciente")
	@ApiOperation(value = "Apaga um paciente específico")
	public ResponseEntity deletarPaciente(@RequestBody PacienteDTO pacienteDTO) {
		try {
			pacienteService.deletarPaciente(pacienteService.converterPacienteDTOParaPaciente(pacienteDTO));
			return ResponseEntity.status(204).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@PutMapping("/paciente")
	@ApiOperation(value = "Altera um paciente específico")
	public ResponseEntity<PacienteDTO> alterarPaciente(@RequestBody PacienteDTO pacienteDTO) throws MessageException {
		try {
			return ResponseEntity.ok(pacienteService.converterPacienteParaPacienteDTO(
					pacienteService.salvarPaciente(pacienteService.converterPacienteDTOParaPaciente(pacienteDTO))));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

}
