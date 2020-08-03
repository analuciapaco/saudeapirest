/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.conexa.saudeapirest.exception.ExpiredTokenException;
import com.conexa.saudeapirest.exception.InvalidTokenException;
import com.conexa.saudeapirest.exception.MessageException;
import com.conexa.saudeapirest.models.Usuario;
import com.conexa.saudeapirest.repository.UsuarioRepository;

import io.jsonwebtoken.Claims;

@Service
public class UserAuthenticationService {
	private final UsuarioRepository usuarioRepository;
	private TokenService tokenService;

	@Autowired
	public UserAuthenticationService(UsuarioRepository usuarioRepository, TokenService tokenService) {
		this.usuarioRepository = usuarioRepository;
		this.tokenService = tokenService;
	}

	public Usuario authenticate(Usuario dados, String tokenRecebido) {
		if (validate(tokenRecebido)) {
			return dados;
		} else {
			throw new MessageException("Token inválido, favor verificar!.", HttpStatus.CONFLICT);
		}

	}

	private boolean validate(String tokenRecebido) {
		try {
			String tokenTratado = tokenRecebido.replace("Bearer", "");
			Claims claims = tokenService.decodeToken(tokenTratado);
			System.out.println(claims.getIssuer());
			System.out.println(claims.getIssuedAt());

			// verifica se o token expirou
			if (claims.getExpiration().before(new Date(System.currentTimeMillis())))
				throw new ExpiredTokenException();
			System.out.println(claims.getExpiration());
			return true;

		} catch (ExpiredTokenException et) {
			et.printStackTrace();
			throw et;
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidTokenException();
		}

	}
}
