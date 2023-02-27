package br.com.reps.permissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface PermissionsConfig {
	
	@PreAuthorize("hasAnyAuthority('GREMISTA')")
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface isGremista{}
	
	@PreAuthorize("hasAnyAuthority('ALUNO')")
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface isUsuario{}

}
