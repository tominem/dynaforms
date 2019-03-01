package com.strandum.dynaforms.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

@WebFilter(filterName="jsfXmlFilter", urlPatterns= {"/persons.xhtml"})
public class JsfXmlFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest r = (HttpServletRequest) request;
	    String rootPath = r.getSession().getServletContext().getRealPath("/");
	    String resourceXsl = rootPath + "resources/dynaforms/xsl";
	    String uri = r.getRequestURI();
	    String xhtmlFileName = uri.substring(uri.lastIndexOf("/")).replaceAll("jsf$", "xhtml"); // Change this if FacesServlet is not mapped on `*.jsf`.
	    File xhtmlFile = new File(rootPath, xhtmlFileName);

	    if (!xhtmlFile.exists()) { // Do your caching job.
	        String xmlFileName = xhtmlFileName.replaceAll("xhtml$", "xml");
	        String xslFileName = xhtmlFileName.replaceAll("xhtml$", "xsl");
	        File xmlFile = new File(resourceXsl, xmlFileName);
	        File xslFile = new File(resourceXsl, xslFileName);
	        Source xmlSource = new StreamSource(xmlFile);
	        Source xslSource = new StreamSource(xslFile);
	        Result xhtmlResult = new StreamResult(xhtmlFile);

	        try {
	            Transformer transformer = TransformerFactory.newInstance().newTransformer(xslSource);
//	            transformer.transform(null, xhtmlResult);
	            transformer.transform(xmlSource, xhtmlResult);
	        } catch (TransformerException e) {
	            throw new RuntimeException("Transforming failed.", e);
	        }
	    }

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
