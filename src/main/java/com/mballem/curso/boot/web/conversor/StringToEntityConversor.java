package com.mballem.curso.boot.web.conversor;

import org.springframework.stereotype.Component;

@Component
public class StringToEntityConversor {
	
//	public <T> T convert(T obj) {
//		if(obj.getClass().equals(Cargo.class))
//			return (T) new ToCargo();
//		else
//			return (T) new ToDepartamento();
//	}
//
//	@Component
//	private class ToCargo implements Converter<String, Cargo> {
//
//		@Autowired
//		private CargoService service;
//		
//		@Override
//		public Cargo convert(String text) {
//			if(text.isEmpty()) {
//				return null;
//			}
//			Long id = Long.parseLong(text);
//			return service.buscarPorId(id);
//		}
//	}
//	
//	@Component
//	private class ToDepartamento implements Converter<String, Departamento> {
//
//		@Autowired
//		private DepartamentoService service;
//
//		@Override
//		public Departamento convert(String text) {
//			if (text.isEmpty()) {
//				return null;
//			}
//			Long id = Long.parseLong(text);
//			return service.buscarPorId(id);
//		}
//	}
}
