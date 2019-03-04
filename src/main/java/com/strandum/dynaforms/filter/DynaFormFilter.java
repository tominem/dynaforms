package com.strandum.dynaforms.filter;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.strandum.dynaforms.model.FormConfig;
import com.strandum.dynaforms.service.FormConfigService;
import com.sun.javafx.binding.StringFormatter;

@WebFilter(filterName="dynaFormFilter", urlPatterns= {"/forms/*"})
public class DynaFormFilter implements Filter{
	
	public static final String FORMS_PATH = "forms/";
	private FormConfigService formConfigService;
	
	private FilterConfig filterConfig;
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String requestUrl = req.getRequestURL().toString();
		int lastIndexOf = requestUrl.lastIndexOf("forms/");
		
		if (lastIndexOf != -1) {
			
			lastIndexOf += "forms/".length();
			
			String token = requestUrl.substring(lastIndexOf);
			FormConfig formConfig = getFormConfigService().findFormConfigByToken(token);
			
			if (formConfig != null) {
				String redirect = String.format("/%s/%s", "form", formConfig.getFormLayout().getResource());
				filterConfig.getServletContext().getRequestDispatcher(redirect).forward(request, response);
				return;
			}
			
		}
		
		chain.doFilter(request, response);
		
	}

	private FormConfigService getFormConfigService() {
		
		if (this.formConfigService == null) {
			this.formConfigService = 
					WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext())
						.getBean(FormConfigService.class);
		}
		
		return formConfigService;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
