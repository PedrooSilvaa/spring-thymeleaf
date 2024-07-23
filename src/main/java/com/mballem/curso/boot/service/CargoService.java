package com.mballem.curso.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.curso.boot.dao.CargoDao;
import com.mballem.curso.boot.domain.Cargo;

/*quando abre uma transacao, esta bloqueando o acesso a tabela do 
banco. o spring sabe q n é p abrir uma transacao*/

/*caso de erro na operacao de insert, o processo de transacoes do 
spring vai gerar um rollback. faz com q um commit n seja executado 
e todas as operacoes sejam revertidas.*/

/*inclui dois daos dependentes no banco, se colocar um transactional em um 
metodo q involve só um dao, caso de um erro sera incluido uma parte do dado 
a outra n sera, entao os dados ficarao incompletos pois os dados sao dependentes. 
se colocar o transactional na camada service, envolvendo os dois daos, se 
der erro, sera interrompida as operacoes dos dois daos.*/

@Service
//por padrao é false
@Transactional
public class CargoService implements ServiceGenerico<Cargo> {

	@Autowired
	private CargoDao dao;

	@Override
	public void salvar(Cargo obj) {
		dao.save(obj);
	}

	@Override
	public void editar(Cargo obj) {
		dao.update(obj);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cargo buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cargo> buscarTodos() {
		return dao.findAll();
	}

	public boolean cargoTemFuncionario(Long id) {
		if(buscarPorId(id).getFuncionarios().isEmpty()) {
			return false;
		}
		return true;
	}
}
