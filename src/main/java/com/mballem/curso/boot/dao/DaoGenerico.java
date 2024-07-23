package com.mballem.curso.boot.dao;

import java.util.List;

public interface DaoGenerico<T> {

	void save(T obj);

	void update(T obj);

	void delete(Long id);

	T findById(Long id);

	List<T> findAll();
}
