package com.mballem.curso.boot.web.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mballem.curso.boot.domain.Departamento;
import com.mballem.curso.boot.service.DepartamentoService;

//tipo q esta recebendo na pagina e o tipo q vai retornar p controller
@Component
public class StringToDepartamentoConverter implements Converter<String, Departamento> {

	@Autowired
	private DepartamentoService service;

	@Override
	public Departamento convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		Long id = Long.parseLong(text);
		return service.buscarPorId(id);
	}

}
