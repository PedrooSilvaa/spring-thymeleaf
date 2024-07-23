package com.mballem.curso.boot.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mballem.curso.boot.domain.Funcionario;

@Repository
public class FuncionarioDao extends AbstractDao<Funcionario, Long> implements DaoGenerico<Funcionario> {

	public List<Funcionario> findByNome(String nome) {
		String jpql;
//		jpql = "select f from Funcionario f where f.nome like :nome";
//		TypedQuery<Funcionario> query = getEntityManager().createQuery(jpql, Funcionario.class) ;
//		query.setParameter("nome", nome);
//		return query.getResultList();
		jpql = "select f from Funcionario f where f.nome like concat('%', ?1, '%')";
		return createQueryList(jpql, nome);
	}

	public List<Funcionario> findByCargoId(Long id) {
		String jpql = "select f from Funcionario f where f.cargo.id = ?1";
		return createQueryList(jpql, id);
	}

	public List<Funcionario> findByData(LocalDate entrada, LocalDate saida) {
		StringBuilder jpql = new StringBuilder("select f from Funcionario f ");
		
		if(entrada != null && saida != null) {
			jpql.append("where f.dataEntrada >= ?1 and f.dataSaida <= ?2 ")
				.append("order by f.dataEntrada asc");
			return createQueryList(jpql.toString(), entrada, saida);
		}
		
		if(entrada != null) {
			jpql.append("where f.dataEntrada = ?1 ")
				.append("order by f.dataEntrada asc");
		return createQueryList(jpql.toString(), entrada);
		}
		
		if(saida != null) {
			jpql.append("where f.dataSaida = ?1 ")
				.append("order by f.dataEntrada asc");
		return createQueryList(jpql.toString(), saida);
		}
		
		return null;
	}

}
