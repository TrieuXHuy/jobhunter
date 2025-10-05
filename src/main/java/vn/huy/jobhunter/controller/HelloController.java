package vn.huy.jobhunter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public String getHelloWorld() {
		return " 123 4 5 Hello World (Hỏi Dân IT & Eric)";
	}
}
