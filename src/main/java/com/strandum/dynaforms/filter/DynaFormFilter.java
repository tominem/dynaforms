package com.strandum.dynaforms.filter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.strandum.dynaforms.model.FormConfig;
import com.strandum.dynaforms.model.FormLayout;
import com.strandum.dynaforms.service.FormConfigService;

@WebFilter(filterName="dynaFormFilter", urlPatterns= {"/forms/*"})
public class DynaFormFilter implements Filter{
	
	public static final String FORM_PATH = "form/";
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
		
		String requestUrl = req.getRequestURL().toString();
		int lastIndexOf = requestUrl.lastIndexOf(FORMS_PATH);
		
		if (lastIndexOf != -1) {
			
			lastIndexOf += FORMS_PATH.length();
			
			String token = requestUrl.substring(lastIndexOf);
			FormConfig formConfig = getFormConfigService().findFormConfigByToken(token);
			
			if (formConfig != null) {
				FormLayout formLayout = formConfig.getFormLayout();
				String xhtmlFile = formLayout.getResource();
				generateXhtmlIfNotExists(req, formLayout);
				String redirect = String.format("/%s%s", FORM_PATH, xhtmlFile);
				req.setAttribute("formConfigId", formConfig.getId());
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
	
	private void generateXhtmlIfNotExists(HttpServletRequest request, FormLayout formLayout) {
		
		try {
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			String realFormPath = rootPath + FORM_PATH;
			File realFormPathFolder = new File(realFormPath);
			
			if (!realFormPathFolder.exists()) {
				realFormPathFolder.mkdirs();
			}
			
			File xhtmlFile = new File(realFormPath, formLayout.getResource());
			if (!xhtmlFile.exists()) {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(new InputSource(new StringReader(formLayout.getXhtml())));
				DOMSource source = new DOMSource(doc);
				Result xhtmlResult = new StreamResult(xhtmlFile);
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.transform(source, xhtmlResult);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
