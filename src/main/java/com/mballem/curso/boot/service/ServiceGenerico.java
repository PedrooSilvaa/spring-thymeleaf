package com.mballem.curso.boot.service;

import java.util.List;

public interface ServiceGenerico<T> {

	void salvar(T obj);
	
	void editar(T obj);
	
	void excluir(Long id);
	
	T buscarPorId(Long id);
	
	List<T> buscarTodos();
}