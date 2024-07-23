package com.mballem.curso.boot.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.boot.domain.Cargo;
import com.mballem.curso.boot.domain.Departamento;
import com.mballem.curso.boot.service.CargoService;
import com.mballem.curso.boot.service.DepartamentoService;

@Controller
@RequestMapping("/cargos")
public class CargoController {

	@Autowired
	private CargoService service;
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Cargo cargo) {
		return "/cargo/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("cargos", service.buscarTodos());
		return "/cargo/lista";
	}
	
	@PostMapping("/salvar")
	//informa ao spring q ta fazendo a validacao pelo bean validation p o obj cargo
	//bindingresult, verifica se houve algum problema referente as validacoes
	public String salvar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {
		
		//se ocorreu algum erro de validacao, retorna p pagina de cadastro
		if(result.hasErrors()) {
			return "/cargo/cadastro";
		}
		
		service.salvar(cargo);
		attr.addFlashAttribute("success","Cargo inserido com sucesso.");
		return "redirect:/cargos/cadastrar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cargo", service.buscarPorId(id));
		return "cargo/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return "/cargo/cadastro";
		}
		
		service.editar(cargo);
		attr.addFlashAttribute("success","Registro atualizado com sucesso.");
		return "redirect:/cargos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	//tem como argumento o path q vai recuperar o id da url e tem um obj redirect p enviar a mensagem p a pagina
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		//se tiver funcionario no cargo, n podera ser excluido o cargo
		if(!service.cargoTemFuncionario(id)) {
			attr.addFlashAttribute("success","Cargo excluido com sucesso.");
			service.excluir(id);
		} else {
			attr.addFlashAttribute("fail","Cargo não excluido. Tem funcionário(s) vinculado(s).");
		}
		return "redirect:/cargos/listar";
	}
	
	@ModelAttribute("departamentos")
	public List<Departamento> listaDepartamentos(){
		return departamentoService.buscarTodos();
	}
}
