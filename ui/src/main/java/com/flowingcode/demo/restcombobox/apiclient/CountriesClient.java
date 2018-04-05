package com.flowingcode.demo.restcombobox.apiclient;

/*-
 * #%L
 * REST Combobox UI
 * %%
 * Copyright (C) 2018 Flowing Code
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.stream.Stream;

import org.springframework.web.client.RestTemplate;

/**
 * Client for the Countries API.
 * 
 * @author Javier Godoy / Flowing Code
 */
public class CountriesClient {
	
	private static String ALL_COUNTRIES_URI = "http://127.0.0.1:8081/api/countries";
	
	private static String GET_COUNTRIES_URI = "http://127.0.0.1:8081/api/countries/list?offset={1}&limit={2}&filter={3}";
	
	private static String COUNT_URI = "http://127.0.0.1:8081/api/countries/count?filter={1}";
	
	public Stream<String> getAllCountries() {
		RestTemplate restTemplate = new RestTemplate();
	    String[] countries = restTemplate.getForObject(ALL_COUNTRIES_URI, String[].class);
	    return Stream.of(countries);
	}
	
	public Stream<String> getCountries(int offset, int limit, String filter) {
		RestTemplate restTemplate = new RestTemplate();
	    String[] countries = restTemplate.getForObject(GET_COUNTRIES_URI, String[].class, offset, limit, filter);
	    return Stream.of(countries);
	}

	public int getCount(String filter) {
		RestTemplate restTemplate = new RestTemplate();
	    Integer count = restTemplate.getForObject(COUNT_URI, Integer.class, filter);
	    return count;
	}
	
}
