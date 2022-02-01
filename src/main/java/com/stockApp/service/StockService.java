package com.stockApp.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stock.dto.StockDTO;
import com.stockApp.model.StockModel;
import com.stockApp.repository.StockRepository;

@Service
public class StockService {
	
	@Autowired
	StockRepository rep;
	
	private String quoto = "https://financialmodelingprep.com/api/v3/quote/";
	//private String apiKey = "?apikey=cde60046dfbfa16b001a6b6acea55582";
	private String apiKey = "?apikey=d1f8636b9df280532258cc61137e6f24";
	
	HttpClient client = HttpClientBuilder.create().build();
	Type aType = new TypeToken<List<StockDTO>>() {}.getType();
	Gson gson = new Gson();
	
	public void addStock(StockDTO dto) {
		StockModel m = new StockModel();
		m.setName("Justin");
		m.setStock(dto.getSymbol());
		rep.save(m);
	}
	
	public void deleteStock(String symbol) {
		rep.deleteSymbol(symbol);
	}

	public List<StockDTO> listAll(String userName) throws Exception {
		
		StringBuilder sb = new StringBuilder(quoto);
		List<StockDTO> ans = new ArrayList<>();
		int len = sb.length();
		
		for(StockModel m:rep.listAll(userName)) {
			sb.append(m.getStock()).append(apiKey);
			System.out.println(sb.toString());
			
			//Use HttpGet to query real-time stock info
			//use Gson to parse Json HttpResponse
	        HttpGet request = new HttpGet(sb.toString());
	        HttpResponse response = client.execute(request);
	        HttpEntity entity = response.getEntity();
	        String content = EntityUtils.toString(entity);
		    List<StockDTO> cur = gson.fromJson(content, aType);
		    ans.add(cur.get(0));
			//
			sb.setLength(len);
		}
	    return ans;
	}
	//Check if stock symbol exist or not
	public boolean exists(String symbol){
		return rep.exists(symbol);
	}
}




















