package com.mkyong.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mkyong.common.model.Customer;
import com.mkyong.common.model.TestBean;

@Controller
@RequestMapping("/customer")
public class SignUpController {
	
	@Autowired
	 MessageSource MessageSource;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addCustomer(
            @Valid Customer customer,
            BindingResult result) {

		/*for (Object object : result.getAllErrors()) {
            if (object instanceof FieldError) {
				FieldError fieldError = (FieldError) object;

				System.out.println(fieldError.getField() + ":"
						+ fieldError.getCode());

			}

			if (object instanceof ObjectError) {
				ObjectError objectError = (ObjectError) object;

			}
		}*/

        if (result.hasErrors()) {
            return "SignUpForm";
        } else {
            return "Done";
        }

    }

    @RequestMapping(method = RequestMethod.GET)
    public String displayCustomerForm(ModelMap model) {

        model.addAttribute("customer", new Customer());
        return "SignUpForm";

    }

    @RequestMapping(value = "/a1",method = RequestMethod.GET)
    public String a1(ModelMap model) {

        model.addAttribute("testBean", new TestBean());
        return "a1";

    }

    @RequestMapping(value = "/a2", method = RequestMethod.POST)
    public String addCustomer(@Valid  TestBean testBean,
                              BindingResult result) {
        result.rejectValue("phone","testBean.data.repetition");
        if (result.hasErrors()) {
            return "a1";
        } else {
            return "Done";
        }

    }

    /**
     * 网站首页显示的项目列表0:互联网+,1：新兴产业,2:新金融
     */
    @RequestMapping(value = "/aaaa", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> test(@Valid @RequestBody TestBean bean, BindingResult result) {
        Map<String, String> map = null;
        if (result.hasErrors()) {
            map = getErrors(result);
        } else {
            map = new HashMap<String, String>();
            map.put("code", "success");
        }
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<String, String>();
        List<FieldError> list = result.getFieldErrors();
        for (FieldError error : list) {
            System.out.println("error.getField():" + error.getField());
            System.out.println("error.getDefaultMessage():" + error.getDefaultMessage());
            map.put(error.getField(), error.getDefaultMessage());
        }
        return map;
    }

}