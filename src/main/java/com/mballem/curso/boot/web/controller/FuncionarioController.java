package com.mballem.curso.boot.web.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.boot.domain.Cargo;
import com.mballem.curso.boot.domain.Funcionario;
import com.mballem.curso.boot.domain.UF;
import com.mballem.curso.boot.service.CargoService;
import com.mballem.curso.boot.service.FuncionarioService;
import com.mballem.curso.boot.web.validator.FuncionarioValidator;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioService service;
	
	@Autowired
	private CargoService cargoService;
	
	//faz com q seja o primeiro metodo dentro dessa classe 
	//q sera executado
	@InitBinder
	//spring validator so vai ser encerrado depois q registrar a classe funcionariovalidator
	public void initBinder(WebDataBinder binder) {
		//spring sabe q essa classe vai utilizar a validacao
		binder.addValidators(new FuncionarioValidator());
	}
	
	@GetMapping("/cadastrar")
	public String cadastrar(Funcionario funcionario) {
		return "/funcionario/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("funcionarios", service.buscarTodos());
		return "/funcionario/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return "funcionario/cadastro";
		}
		
		service.salvar(funcionario);
		attr.addFlashAttribute("success", "Funcionario inserido com sucesso.");
		return "redirect:/funcionarios/cadastrar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("funcionario", service.buscarPorId(id));
		return "funcionario/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(Funcionario funcionario, BindingResult result, RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return "funcionario/cadastro";
		}
		
		service.editar(funcionario);
		attr.addFlashAttribute("success", "Funcionario editado com sucesso.");
		return "redirect:/funcionarios/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.excluir(id);
		attr.addFlashAttribute("success", "Funcionário excluido com sucesso.");
		return "redirect:/funcionarios/listar";
	}
	
	//pq o valor do nome n vai chegar no controller como um path da url. e sim como um atributo/parametro do request
	@GetMapping("/buscar/nome")
	public String getPorNome(@RequestParam("nome") String nome, ModelMap model) {
		model.addAttribute("funcionarios", service.buscarPorNome(nome));
		return "/funcionario/lista";
	}
	
	@GetMapping("/buscar/cargo")
	public String getPorCargo(@RequestParam("id") Long id, ModelMap model) {
		model.addAttribute("funcionarios", service.buscarPorCargo(id));
		return "/funcionario/lista";
	}
	
	@GetMapping("/buscar/data")
	public String getPorDatas(
			@RequestParam("entrada") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entrada,
			@RequestParam("saida") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate saida,
			ModelMap model) {
		model.addAttribute("funcionarios", service.buscarPorDatas(entrada, saida));
		return "/funcionario/lista";
	}
	
	@ModelAttribute("cargos")
	//envia a lista de cargos pro cmb
	public List<Cargo> getCargos(){
		return cargoService.buscarTodos();
	}
	
	@ModelAttribute("ufs")
	//envia o array de ufs pro cmb
	public UF[] getUFs() {
		return UF.values();
	}
}
