package com.strandum.dynaforms.bean;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

@ManagedBean(name="editForm")
@ViewScoped
public class EditFormBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String xhtml;
	
	private String previewSrc;

	private String exception;
	
	public String getXhtml() {
		return xhtml;
	}

	public void setXhtml(String xhtml) {
		this.xhtml = xhtml;
	}
	
	public void reload() throws TransformerException {
		
		try {
			
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			File xhtmlFile = new File(rootPath, "showForm.xhtml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(new InputSource(new StringReader(getXhtml())));
		    DOMSource source = new DOMSource(doc);
			Result xhtmlResult = new StreamResult(xhtmlFile);
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, xhtmlResult);
			
			this.setException(null);
			
		} catch (Exception e) {
			
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			String errorMessage = errors.toString();	
			
			this.setException(errorMessage);
			
		}
		
	}

	public String getPreview() {
		return previewSrc;
	}

	public void setPreview(String previewSrc) {
		this.previewSrc = previewSrc;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

}
