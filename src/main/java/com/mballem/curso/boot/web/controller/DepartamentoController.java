package com.mballem.curso.boot.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.boot.domain.Departamento;
import com.mballem.curso.boot.service.DepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

	@Autowired
	private DepartamentoService service;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Departamento departamento) {
		return "/departamento/cadastro";
	}
	 
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		//nome da variavel q espera la na pagina
		model.addAttribute("departamentos", service.buscarTodos());
		return "/departamento/lista";
	}
	
//	vai receber o submit do formulario e pega o objeto departamento q esta sendo enviado pelo formulario e insere
	@PostMapping("/salvar")
	public String salvar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return "/departamento/cadastro";
		}
		
		service.salvar(departamento);
		attr.addFlashAttribute("success", "Departamento inserido com sucesso.");
		return "redirect:/departamentos/cadastrar";
	}
	
	@GetMapping("/editar/{id}")
	//consegue recuperar da url o id q enviou como path
	//model, envia o departamento como variavel p pagina de cadastro
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("departamento", service.buscarPorId(id));
		return "/departamento/cadastro";
	}
	
	@PostMapping("/editar")
	//tem uma acao de redirect no return, por isso usa redirectattributes
	public String editar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return "/departamento/cadastro";
		}
		
		service.editar(departamento);
		attr.addFlashAttribute("success", "Departamento editado com sucesso.");
		return "redirect:/departamentos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		if(!service.departamentoTemCargo(id)) {
			service.excluir(id);
			model.addAttribute("success", "Departamento removido com sucesso.");
		} else {
			model.addAttribute("fail", "Departamento não removido. Possui cargo(s) vinculado(s).");
		}
		return listar(model);
	}
}
