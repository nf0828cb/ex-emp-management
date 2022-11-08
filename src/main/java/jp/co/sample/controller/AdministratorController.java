package jp.co.sample.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

@Controller
@RequestMapping("")
public class AdministratorController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private AdministratorService administratorService;
	
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		InsertAdministratorForm insertAdministratorForm = new InsertAdministratorForm();
		return insertAdministratorForm;
		
	}
	
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
		
	}
	
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		administrator.setName(form.getName());
		administrator.setMailAddress(form.getMailAddress());
		administrator.setPassword(form.getPassword());
		
		administratorService.insert(administrator);
		return "/";
	}
	
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		LoginForm loginForm = new LoginForm();
		return loginForm;
	}
	
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	@RequestMapping("/login")
	public String login(LoginForm form,Model model) {
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		if(administrator== null) {
			model.addAttribute("message","メールアドレスまたはパスワードが不正です");
			return "administrator/login";
		}else {
			session.setAttribute("administratorName",administrator.getName());
			return "forward:/employee/showList";
		}
		
	}
	
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}

}
