package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	public EmployeeService employeeService;
	
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList",employeeList);
		return "employee/list";
	}
	
	@ModelAttribute
	public UpdateEmployeeForm setUppUpdateEmployeeForm() {
		UpdateEmployeeForm updateEmployeeForm = new UpdateEmployeeForm();
		return updateEmployeeForm;
	}
	
	@RequestMapping("/showDetail")
	public String showDetail(String id,Model model,UpdateEmployeeForm form) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee",employee);
		form.setId("" +employee.getId());
		form.setDepartmentsCount("" + employee.getDependentsCount());
		return "employee/detail";
	}
	
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {

		Employee employee = employeeService.showDetail(Integer.parseInt(form.getDepartmentsCount()));
		employee.setDependentsCount(employee.getDependentsCount());
		employeeService.update(employee);
		
	
		return "redirect:/employee/showList";
	}
	
}
