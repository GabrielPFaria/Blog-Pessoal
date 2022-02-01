package org.generation.blogPessoal.security;


/* Esta classe é responsável por habilitar a segurança básica da aplicação e o login
 * na aplicação.*/


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@EnableWebSecurity
public class BasicSecurityComfig extends WebSecurityConfigurerAdapter{

	@Autowired   /*Informa que as informaçãoes que objeto tem a estrutura da classe Usuario*/
	private UserDetailsService userDetailsService;/*Basicamente define minha estrutura do objeto*/
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { //Authentication e uma classe que maneja a reques por validação
		
		//define a estrutura que vai validar o usuario																			
		auth.userDetailsService(userDetailsService);
//   EX: Usuario = {email, senha,....}
		
		//cria um usuario de "teste"
		 auth.inMemoryAuthentication()
			.withUser("root")//nome do usuario
			.password(passwordEncoder().encode("root"))//senha criptografada
			.authorities("ROLE_USER");//Autorizações

	
	}
	
	@Bean //Informa para o spring que o que esta abaixo e um "objeto" que podera ser usado em outros lugares dentro desta classe
	public PasswordEncoder passwordEncoder() {  //cria o metode Meto de criptografia e decriptação
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/usuarios/logar").permitAll() //Da autorização a esta rota para permitir o login
		.antMatchers("/usuarios/cadastrar").permitAll() //Da autorização a esta rota para permitir o Cadastro
		.antMatchers(HttpMethod.OPTIONS).permitAll() //Permite que estes metodos sejão acessaveis
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Govena o periodo de autorização
		.and().cors() //Pemite que requissições sejao feitas mesmo entre servidores diferentes
		.and().csrf().disable(); //
		
	}

}
