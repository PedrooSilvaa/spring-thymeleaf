package com.mballem.curso.boot.web.validator;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mballem.curso.boot.domain.Funcionario;

public class FuncionarioValidator implements Validator{

	@Override
	//o objeto a partir do formulario com o obj q essa classe realmente deve validar
	//o argumento é o obj q esta no formulario
	public boolean supports(Class<?> classeDoFormulario) {
		//se for true, vai p metodo validate
		System.out.println("\n\n=======retorno"+Funcionario.class.equals(classeDoFormulario));
		return Funcionario.class.equals(classeDoFormulario);
	}

	@Override
	//2 argumentos, object é q esta recebendo do formulario, o errors é o obj q vai lidar com a validacao
	public void validate(Object obj, Errors errors) {
		System.out.println("\n\n============primeiraLinha");
		Funcionario f = (Funcionario) obj;
		//no formulario foi digitado a data de entrada e recupera aqui
		LocalDate entrada = f.getDataEntrada();
		System.out.println("\n\n===================Data: "+f.getDataSaida()+"\n\n");
		if(f.getDataSaida().equals("")) {
			return;
		}
		if(f.getDataSaida().isBefore(entrada)) {
			//nome do campo, mensagem
			errors.rejectValue("dataSaida", "PosteriosDataEntrada.funcionario.dataSaida");
		}
	}
}
