package com.mballem.curso.boot.dao;

import org.springframework.stereotype.Repository;

import com.mballem.curso.boot.domain.Departamento;

@Repository
public class DepartamentoDao extends AbstractDao<Departamento, Long> implements DaoGenerico<Departamento> {

}
