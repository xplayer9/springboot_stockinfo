package com.stockApp.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.stock.dto.StockDTO;
import com.stockApp.service.StockService;


@Controller
public class StockController {
	
	@Autowired
	StockService service;
	
	private int timeout = 0;
	
	@GetMapping(value="/")
    public String GetDefault() {
        return "redirect:/index";
    }
	
	@GetMapping(value="/delete/{symbol}")
    public String GetDelete(@PathVariable(value = "symbol") String symbol) {
		service.deleteStock(symbol);
        return "redirect:/index";
    }
	
    @PostMapping(value="/add")
    public String postAdd(@ModelAttribute("StockDTO") StockDTO dto) {
    	if(service.exists(dto.getSymbol())) 
    		service.addStock(dto);
  
    	return "redirect:/index"; 
    }
    
    @GetMapping(value="/index")
    public String GetIndex(Model model, @ModelAttribute("StockDTO") StockDTO dto) {
    	String name = "Justin";
    	List<StockDTO> ll = new ArrayList<>();
    	try {
    		ll = service.listAll(name);
    		timeout = 0;
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		if(++timeout == 10)
    			return "index";
    		else
    			return "redirect:/index";
    	}
    	model.addAttribute("allStocks", ll);
        return "index";
    }
    
}
