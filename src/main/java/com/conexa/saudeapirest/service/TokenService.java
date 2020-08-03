/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.conexa.saudeapirest.models.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
		private static final long expirationTime = 1800000; //30 minutos
	private String key = "String Secreta";

	public String generateToken(Usuario user) {
		return Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis())).setSubject("Conexa Saúde API")
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS256, key).compact();
	}

	public Claims decodeToken(String token) {
		System.out.println("Decodificando token");
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		

	}
}
