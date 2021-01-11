package spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class PostMVCController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView firstView() {
		ModelAndView mav = new ModelAndView("first");
		return mav;
	}
}
