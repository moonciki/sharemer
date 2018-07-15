package sharemer.business.manager.master.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sharemer.business.manager.master.anno.NeedLogin;

@Controller
public class HomeController {

	@RequestMapping("/")
	@NeedLogin
	public String home(){
		return "home";
	}
}
