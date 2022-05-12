package com.generation.games_store.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.games_store.model.Usuario;
import com.generation.games_store.model.UsuarioLogin;
import com.generation.games_store.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Optional<Usuario> cadastrarUsuario(Usuario usuario){
		if(usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
				return Optional.empty();
		
		usuario.setPassword(criptografarSenha(usuario.getPassword()));
		return Optional.of(usuarioRepository.save(usuario));
	}
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario){
		if(usuarioRepository.findById(usuario.getId()).isPresent()) {
			
			usuario.setPassword(criptografarSenha(usuario.getPassword()));
			return Optional.ofNullable(usuarioRepository.save(usuario));
		}
		return Optional.empty();
	}
	
	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin){
  		
		  Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
	  

	   if(usuario.isPresent()){
	   
	   	if(compararSenhas(usuarioLogin.get().getPassword(), usuario.get().getPassword())){
	   		
	  		
	  		usuarioLogin.get().setId(usuario.get().getId());
	  		usuarioLogin.get().setName(usuario.get().getName());
	   		usuarioLogin.get().setImage(usuario.get().getImage());
	  		usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getPassword()));
	   		usuarioLogin.get().setPassword(usuario.get().getPassword());
	  			
	  		return usuarioLogin;
	   	}
	   }
	   return Optional.empty();
	  }

	private String gerarBasicToken(String usuario, String password) {
		String token = usuario + ":" + password; //Regra do HttpBasic
		   byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		   return "Basic " + new String(tokenBase64);
	}

	private boolean compararSenhas(String typedPassword, String basePassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	  	return encoder.matches(typedPassword, basePassword);
		
	}

	private String criptografarSenha(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	  	return encoder.encode(password);
	}
}
