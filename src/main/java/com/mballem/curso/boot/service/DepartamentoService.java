package com.mballem.curso.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.curso.boot.dao.DepartamentoDao;
import com.mballem.curso.boot.domain.Departamento;

@Service
@Transactional
public class DepartamentoService implements ServiceGenerico<Departamento> {

	@Autowired
	private DepartamentoDao dao;

	@Override
	public void salvar(Departamento obj) {
		dao.save(obj);
	}

	@Override
	public void editar(Departamento obj) {
		dao.update(obj);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Departamento buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Departamento> buscarTodos() {
		return dao.findAll();
	}

	public boolean departamentoTemCargo(Long id) {
		if(buscarPorId(id).getCargos().isEmpty()) {
			return false;
		}
		return true;
	}
}
