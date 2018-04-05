package com.flowingcode.demo.restcombobox.apiservice;

/*-
 * #%L
 * Countries API service
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

import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Javier Godoy / Flowing Code
 */
@RestController
public class CountriesController {


   @RequestMapping("/api/countries")
   @ResponseBody public String[] getCountries() {
   	   return countries().toArray(String[]::new);
   }    
    		
    @RequestMapping("/api/countries/list")
    public String[] getCountries(
    		@RequestParam(value="filter") String filter,
    		@RequestParam(value="offset") int offset, 
    		@RequestParam(value="limit")  int count) {
    	return countries().filter(country->filter(country,filter))
    			.skip(offset).limit(count).toArray(String[]::new);
    }    
    
    @RequestMapping("/api/countries/count")
    public int getCountries(
    		@RequestParam(value="filter") String filter) {
    	return (int) countries().filter(country->filter(country,filter)).count();
    }
    
    private boolean filter(String country, String filter) {
    	return filter.isEmpty() || country.toLowerCase().contains(filter.toLowerCase());
    }
    
    private static Stream<String> countries() {
    	return Stream.of(Locale.getISOCountries())
    	.map(countryCode -> new Locale("", countryCode))
    	.map(locale->locale.getDisplayCountry(Locale.ENGLISH))
    	.sorted();
    }
    
}
