package com.flowingcode.demo.restcombobox.ui;

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

import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;

import com.flowingcode.demo.restcombobox.apiclient.CountriesClient;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Sample UI demostrating the use of CallbackDataProvider for accessing a REST service.
 * 
 * @author Javier Godoy / Flowing Code
 */
@SuppressWarnings("serial")
@Theme(ValoTheme.THEME_NAME)
public class MyUI extends UI {

	private CountriesClient client = new CountriesClient();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();
        ComboBox<String> cbCountry = new ComboBox<>();
        cbCountry.setWidth(300, Unit.PIXELS);
        initializeItems(cbCountry);
                
        layout.addComponents(cbCountry);
        setContent(layout);
    }

    //private void initializeItems(ComboBox<String> cbCountry) {
    //	cbCountry.setItems(client.getAllCountries());
    //}
    
    private String getFilter(Query<?,String> query) {
    	return ((String)query.getFilter().orElse(null));
    }
    
    private void initializeItems(ComboBox<String> cbCountry) {
    	cbCountry.setDataProvider(new CallbackDataProvider<>(
    		query-> 
                client.getCountries(query.getOffset(),query.getLimit(), getFilter(query)),
            query->
                (int) client.getCount(getFilter(query))
        ));
	}

	@WebServlet(urlPatterns = "/*", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
        
}
