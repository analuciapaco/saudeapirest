/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.conexa.saudeapirest.dto.UsuarioDTO;
import com.conexa.saudeapirest.exception.MessageException;
import com.conexa.saudeapirest.models.Usuario;
import com.conexa.saudeapirest.repository.UsuarioRepository;

@Service
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;
	private final UserAuthenticationService userAuthenticationService;
	private final TokenService tokenService;
	private ModelMapper modelMapper = new ModelMapper();

	public UsuarioService(UsuarioRepository usuarioRepository, UserAuthenticationService userAuthenticationService,
			TokenService tokenService) {
		this.usuarioRepository = usuarioRepository;
		this.userAuthenticationService = userAuthenticationService;
		this.tokenService = tokenService;
	}

	public UsuarioDTO converterUsuarioParaUsuarioDTO(Usuario usuario) {
		this.modelMapper.typeMap(UsuarioDTO.class, Usuario.class).addMapping(UsuarioDTO::getMedico, Usuario::setMedico);
		return this.modelMapper.map(usuario, UsuarioDTO.class);

	}

	public Usuario converterUsuarioDTOParaUsuario(UsuarioDTO usuarioDTO) {
		this.modelMapper.typeMap(Usuario.class, UsuarioDTO.class).addMapping(Usuario::getMedico, UsuarioDTO::setMedico);
		return this.modelMapper.map(usuarioDTO, Usuario.class);

	}

	public Usuario buscarUsuarioCadastrado(Usuario usuario) throws MessageException {
		Usuario usuarioBanco = usuarioRepository.findByUsuarioAndSenha(usuario.getUsuario(), usuario.getSenha());
		if (usuarioBanco == null)
			throw new MessageException(
					"Não existe usuário cadastrado com os dados informados, favor solicitar acesso ao sistema ao administrador.",
					HttpStatus.CONFLICT);

		return usuarioBanco;
	}

	public Usuario validareAutenticarUsuario(Usuario dados, String authorization) throws MessageException {
		Usuario usuario = usuarioRepository.findByUsuarioAndSenha(dados.getUsuario(), dados.getSenha());
		Usuario usuarioAutenticado;
		if (usuario == null)
			throw new MessageException(
					"Não existe usuário cadastrado com os dados informados, favor solicitar acesso ao sistema ao administrador.",
					HttpStatus.CONFLICT);
		if (authorization.isEmpty())
			throw new MessageException("Token vazio, por favor verifique!.", HttpStatus.CONFLICT);
		try {
			usuarioAutenticado = userAuthenticationService.authenticate(usuario, authorization);
		} catch (Exception e) {
			throw new MessageException("Token inválido ou expirado, favor verificar!.", HttpStatus.CONFLICT);
		}
		return usuarioAutenticado;

	}

	public Usuario gerarToken(Usuario usuario) {
		usuario.setToken(tokenService.generateToken(usuario));
		return usuario;
	}

	public Usuario salvarUsuario(Usuario usuario) throws MessageException {
		validarCamposUsuario(usuario);
		Usuario usuarioComSenha = usuarioRepository.findByUsuarioAndSenha(usuario.getUsuario(), usuario.getSenha());
		if (usuarioComSenha != null) {
			usuarioComSenha.setToken(usuario.getToken());
			return usuarioRepository.save(usuarioComSenha);
		} else {
			Usuario loginComMesmoUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());
			if (loginComMesmoUsuario != null)
				throw new MessageException("Já existe outro colaborador com o mesmo usuário, favor alterar o usuário!",
						HttpStatus.CONFLICT);

			return usuarioRepository.save(usuario);
		}
	}

	public Usuario buscarUsuarioPeloToken(String tokenRecebido) {
		String token = tokenRecebido.substring(7, tokenRecebido.length());
		return usuarioRepository.findByToken(token);
	}

	public boolean invalidarTokenUsuario(String tokenRecebido) {
		try {
			Usuario usuario = buscarUsuarioPeloToken(tokenRecebido);
			if (usuario != null) {
				usuario.setToken(null);
				usuarioRepository.save(usuario);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean verificarTokenExpirado(String tokenRecebido) {
		Usuario usuario = buscarUsuarioPeloToken(tokenRecebido);
		if (usuario != null)
			return false;
		else
			return true;
	}

	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	public Usuario buscarUsuarioPorId(long id) {
		return usuarioRepository.findById(id);
	}

	public void deletarUsuario(Usuario usuario) {
		usuarioRepository.delete(usuario);
	}

	public List<UsuarioDTO> converterUsuarioListParaUsuarioDTOList(List<Usuario> lista) {
		List<UsuarioDTO> listaUsuarioDTO = lista.stream().map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
				.collect(Collectors.toList());
		return listaUsuarioDTO;
	}

	public void validarCamposUsuario(Usuario usuario) {
		if (usuario.getUsuario().isEmpty())
			throw new MessageException("O usuário não pode ficar em branco!", HttpStatus.CONFLICT);
		if (usuario.getSenha().isEmpty())
			throw new MessageException("A senha não pode ficar em branco!", HttpStatus.CONFLICT);
	}

}