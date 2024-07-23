package com.mballem.curso.boot.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.curso.boot.dao.FuncionarioDao;
import com.mballem.curso.boot.domain.Funcionario;

@Service
@Transactional
public class FuncionarioService implements ServiceGenerico<Funcionario> {

	@Autowired
	private FuncionarioDao dao;

	@Override
	public void salvar(Funcionario obj) {
		dao.save(obj);
	}

	@Override
	public void editar(Funcionario obj) {
		dao.update(obj);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Funcionario buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Funcionario> buscarTodos() {
		return dao.findAll();
	}

	@Transactional(readOnly = true)
	public List<Funcionario> buscarPorNome(String nome) {
		return dao.findByNome(nome);
	}

	public List<Funcionario> buscarPorCargo(Long id) {
		return dao.findByCargoId(id);
	}

	public List<Funcionario> buscarPorDatas(LocalDate entrada, LocalDate saida) {
		if(entrada != null && saida != null) {
			return dao.findByData(entrada, saida);
		}
		
		if(entrada != null) {
			return dao.findByData(entrada, null);
		}
		
		if(saida != null) {
			return dao.findByData(null, saida);
		}
		
		return new ArrayList<>();
	}
}
