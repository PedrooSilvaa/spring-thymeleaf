package com.mballem.curso.boot.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public abstract class AbstractDao<T, PK extends Serializable> {

	@SuppressWarnings("unchecked")
	/*atribui qual o tipo da entidade que a operacao que vem de um dao especifico como 
	CargoDAO, DepartamentoDAO, esta passando para abstractDao*/
	/*se ta fazendo uma consulta em cargoDao, a classe de entidade q vai fazer a consulta é Cargo
	esse recurso declarado atricuido a entityClass serve p pegar essa entidade a partir 
	da assinatura da classe abstractDao*/
	private final Class<T> entityClass = 
		(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	//o spring injeta o entity entityManager
	@PersistenceContext
	private EntityManager entityManager;

	/*se tiver a necessidade de realizar uma consulta q abstractdao n fornece. entao pode 
	usar esse metodo p ter acesso aos recursos da jpa p realizar essa consulta*/
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/*nos metodos save, update e delete, n foi utilizado o abrir transacao e depois da operacao 
	persist, merge ou operacao de consulta, commitar a transacao. a parte de transacao sera 
	gerenciada pelo spring, essa configuracao sera feita na camada de servico*/
	public void save(T entity) {
		entityManager.persist(entity);
	}

	public void update(T entity) {
		entityManager.merge(entity);
	}

	public void delete(PK id) {
		entityManager.remove(entityManager.getReference(entityClass, id));
	}

	public T findById(PK id) {
		return entityManager.find(entityClass, id);
	}

	public List<T> findAll() {
		return entityManager
				//getsimplename vai dar a entidade q vai realizar a consulta
				//entityClass é a expressao .class
				.createQuery("from " + entityClass.getSimpleName(), entityClass)
				.getResultList();
	}

	protected List<T> createQueryList(String jpql, Object... params) {
		// classe de entidade referente a essa consulta
		TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return query.getResultList();
	}

	protected T createQueryOne(String jpql, Object... params) {
		TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return query.getSingleResult();
	}
}