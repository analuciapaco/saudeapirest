/**
 * @Empresa: Conexa Saúde
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

import com.conexa.saudeapirest.dto.MedicoDTO;
import com.conexa.saudeapirest.exception.MessageException;
import com.conexa.saudeapirest.service.MedicoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Médicos da Empresa Conexa Saúde")
@CrossOrigin(origins = "*")
public class MedicoResource {
	@Autowired
	MedicoService medicoService;

	@GetMapping("/medicos")
	@ApiOperation(value = "Retorna um lista de Medicos da Empresa Conexa Saúde")
	public List<MedicoDTO> listarMedicos() {
		try {
			return medicoService.converterMedicoListParaMedicoDTOList(medicoService.listarMedicos());
		} catch (Exception e) {
			return (List<MedicoDTO>) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/medico/{id}")
	@ApiOperation(value = "Retorna um medico específico")
	public ResponseEntity<MedicoDTO> buscarMedicoPorId(@PathVariable(value = "id") long id) {

		try {
			return ResponseEntity.ok(medicoService.converterMedicoParaMedicoDTO(medicoService.buscarMedicoPorId(id)));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@PostMapping("/medico")
	@ApiOperation(value = "Salva um medico")
	public ResponseEntity<MedicoDTO> salvarMedico(@RequestBody MedicoDTO medicoDTO) throws MessageException {
		try {
			return ResponseEntity.ok(medicoService.converterMedicoParaMedicoDTO(
					medicoService.salvarMedico(medicoService.converterMedicoDTOParaMedico(medicoDTO))));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@DeleteMapping("/medico")
	@ApiOperation(value = "Apaga um medico específico")
	public ResponseEntity deletarMedico(@RequestBody MedicoDTO medicoDTO) {

		try {
			medicoService.deletarMedico(medicoService.converterMedicoDTOParaMedico(medicoDTO));
			return ResponseEntity.status(204).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/medico")
	@ApiOperation(value = "Altera um medico específico")
	public ResponseEntity<MedicoDTO> alterarMedico(@RequestBody MedicoDTO medicoDTO) throws MessageException {

		try {
			return ResponseEntity.ok(medicoService.converterMedicoParaMedicoDTO(
					medicoService.salvarMedico(medicoService.converterMedicoDTOParaMedico(medicoDTO))));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
