/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexa.saudeapirest.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findById(long id);

	Usuario findByUsuarioAndSenha(String usuario, String senha);

	Usuario findByToken(String token);

	Usuario findByUsuario(String usuario);

}
