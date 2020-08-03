/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.resources;

import java.util.List;

import javax.validation.Valid;

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

import com.conexa.saudeapirest.dto.UsuarioDTO;
import com.conexa.saudeapirest.dto.UsuarioMedicoDTO;
import com.conexa.saudeapirest.exception.MessageException;
import com.conexa.saudeapirest.models.Usuario;
import com.conexa.saudeapirest.service.AgendamentoService;
import com.conexa.saudeapirest.service.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Usuarios da Empresa Conexa Saúde")
@CrossOrigin(origins = "*")
public class UsuarioResource {

	@Autowired
	UsuarioService usuarioService;
	@Autowired
	AgendamentoService agendamentoService;

	@GetMapping("/usuarios")
	@ApiOperation(value = "Retorna um lista de Usuarios da Empresa Conexa Saúde")
	public List<UsuarioDTO> listarUsuarios() {
		try {
			return usuarioService.converterUsuarioListParaUsuarioDTOList(usuarioService.listarUsuarios());
		} catch (Exception e) {
			return (List<UsuarioDTO>) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/usuario/{id}")
	@ApiOperation(value = "Retorna um usuario específico")
	public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable(value = "id") long id) {
		try {
			return ResponseEntity
					.ok(usuarioService.converterUsuarioParaUsuarioDTO(usuarioService.buscarUsuarioPorId(id)));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/usuario")
	@ApiOperation(value = "Salva um usuario")
	public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) throws MessageException {
		return new ResponseEntity<UsuarioDTO>(
				(usuarioService.converterUsuarioParaUsuarioDTO(usuarioService.salvarUsuario(
						usuarioService.gerarToken(usuarioService.converterUsuarioDTOParaUsuario(usuarioDTO))))),
				HttpStatus.CREATED);

	}

	@PostMapping("/login")
	@ApiOperation(value = "Valida um usuario")
	public ResponseEntity<UsuarioMedicoDTO> validarUsuario(@RequestBody UsuarioDTO usuarioDTO,
			@RequestHeader String authorization) throws MessageException {
		if (!usuarioService.verificarTokenExpirado(authorization)) {
			Usuario usuario = usuarioService.converterUsuarioDTOParaUsuario(usuarioDTO);
			UsuarioDTO usuarioRetornoDTO = (usuarioService.converterUsuarioParaUsuarioDTO(usuarioService
					.buscarUsuarioCadastrado(usuarioService.validareAutenticarUsuario(usuario, authorization))));
			return new ResponseEntity<UsuarioMedicoDTO>(
					agendamentoService.carregarUsuarioMedicoDTO(usuarioRetornoDTO, authorization), HttpStatus.CREATED);
		} else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

	}

	@PostMapping("/logout")
	@ApiOperation(value = "Invalida Token")
	public ResponseEntity invalidarTokenUsuario(@RequestHeader String authorization) {
		try {
			usuarioService.invalidarTokenUsuario(authorization);
			return ResponseEntity.status(204).build();

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/usuario")
	@ApiOperation(value = "Apaga um usuario específico")
	public ResponseEntity deletarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		try {
			usuarioService.deletarUsuario(usuarioService.converterUsuarioDTOParaUsuario(usuarioDTO));

			return ResponseEntity.status(204).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@PutMapping("/usuario")
	@ApiOperation(value = "Altera um usuario específico")
	public ResponseEntity<UsuarioDTO> alterarUsuario(@RequestBody UsuarioDTO usuarioDTO) throws MessageException {
		try {
			return ResponseEntity.ok(usuarioService.converterUsuarioParaUsuarioDTO(
					usuarioService.salvarUsuario(usuarioService.converterUsuarioDTOParaUsuario(usuarioDTO))));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
