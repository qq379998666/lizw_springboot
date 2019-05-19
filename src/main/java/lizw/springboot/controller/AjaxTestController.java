package lizw.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.TreeMap;

@RestController
@RequestMapping(value="/ajax")
public class AjaxTestController {
	
	@RequestMapping(value="/ajax")
	public void test() {

		LinkedHashMap<String,String> link = new LinkedHashMap<String,String>();

		TreeMap<String,String> tree = new TreeMap<String,String>();

		
	}

}
