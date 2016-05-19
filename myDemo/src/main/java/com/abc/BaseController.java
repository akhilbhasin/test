package com.abc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BaseController {
	
	@RequestMapping("hi")
	public @ResponseBody String zzz(){
		return "ZZZZ";
	}

}
