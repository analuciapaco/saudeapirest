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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conexa.saudeapirest.dto.AgendamentoDTO;
import com.conexa.saudeapirest.dto.AgendamentoDoDiaDTO;
import com.conexa.saudeapirest.exception.MessageException;
import com.conexa.saudeapirest.service.AgendamentoService;
import com.conexa.saudeapirest.service.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Agendamentos da Empresa Conexa Saúde")
@CrossOrigin(origins = "*")
public class AgendamentoResource {
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	AgendamentoService agendamentoService;

	@GetMapping("/agendamentos")
	@ApiOperation(value = "Retorna um lista de Agendamentos da Empresa Conexa Saúde")
	public List<AgendamentoDTO> listaAgendamentos() {
		try {
			return agendamentoService
					.converterAgendamentoListParaAgendamentoDTOList(agendamentoService.listarAgendamentos());
		} catch (Exception e) {
			return (List<AgendamentoDTO>) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/agendamento/{id}")
	@ApiOperation(value = "Retorna um agendamento específico")
	public ResponseEntity<AgendamentoDTO> buscarAgendamentoPorId(@PathVariable(value = "id") long id) {
		try {
			return ResponseEntity.ok(agendamentoService
					.converterAgendamentoParaAgendamentoDTO(agendamentoService.buscarAgendamentoPorId(id)));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@PostMapping("/agendamento")
	@ApiOperation(value = "Salva um agendamento")
	public ResponseEntity<AgendamentoDTO> salvarAgendamento(@RequestBody AgendamentoDoDiaDTO agendamentoDoDiaDTO,
			@RequestHeader String authorization) throws MessageException {
		if (!usuarioService.verificarTokenExpirado(authorization)) {
			return new ResponseEntity(agendamentoService.salvarAgendamentoDoDia(agendamentoDoDiaDTO, authorization),
					HttpStatus.CREATED);
		} else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@DeleteMapping("/agendamento")
	@ApiOperation(value = "Apaga um agendamento específico")
	public ResponseEntity deletarAgendamento(@RequestBody AgendamentoDTO agendamentoDTO) {
		try {
			agendamentoService
					.deletarAgendamento(agendamentoService.converterAgendamentoDTOParaAgendamento(agendamentoDTO));
			return ResponseEntity.status(204).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/agendamento")
	@ApiOperation(value = "Altera um agendamento específico")
	public ResponseEntity<AgendamentoDTO> alterarAgendamento(@RequestBody AgendamentoDTO agendamentoDTO) throws MessageException {
		try {
			return ResponseEntity.ok(agendamentoService.converterAgendamentoParaAgendamentoDTO(agendamentoService
					.salvarAgendamento(agendamentoService.converterAgendamentoDTOParaAgendamento(agendamentoDTO))));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
