package com.strandum.dynaforms.bean;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.jsf.FacesContextUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.strandum.dynaforms.model.FormLayout;
import com.strandum.dynaforms.repository.FormLayoutRepository;

@ManagedBean(name="editForm")
@ViewScoped
public class EditFormBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private  final String formPath = "form/";
	
	private String realFormPath;

	private String xhtml;
	
	private String previewSrc;

	private String exception;
	
	private FormLayout formLayout;
	
	private String formName;
	
	private String resource;
	
	@Autowired
	private FormLayoutRepository formLayoutRepository;
	
	@PostConstruct
	public void initialize() {
		
		FacesContextUtils
 			.getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
 			.getAutowireCapableBeanFactory().autowireBean(this);
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		realFormPath = rootPath + formPath;
		File xhtmlFile = new File(realFormPath);
		
		if (!xhtmlFile.exists()) {
			xhtmlFile.mkdirs();
		}
	}
	
	public String getXhtml() {
		return xhtml;
	}

	public void setXhtml(String xhtml) {
		this.xhtml = xhtml;
	}
	
	public void loadResourceByFormName() {
		FormLayout formLayout = formLayoutRepository.findByFormName(getFormName());
		if (formLayout != null) {
			this.formLayout = formLayout;
			setFormName(formLayout.getFormName());
			setResource(formLayout.getResource());
			setXhtml(formLayout.getXhtml());
			this.setPreview(formPath + getResource());
		}
	}
	
	public String save() {
		
		try {
			
			savePerform();
			
			generateXhtml(getResource());
			
			this.setException(null);
			
			return "";
			
		} catch (Exception e) {
			
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			String errorMessage = errors.toString();	
			
			this.setException(errorMessage);
			
			return null;
		}
		
	}
	
	@Transactional
	private int savePerform() {
		
		if (formLayout == null) {
			formLayout = new FormLayout();
		}
		
		formLayout.setFormName(getFormName());
		formLayout.setResource(getResource());
		formLayout.setXhtml(getXhtml());
		
		return formLayoutRepository.save(formLayout).getId();
	}

	public void reload() throws TransformerException {
		
		try {
			
			generateXhtml(getResource());
			
			this.setException(null);
			
		} catch (Exception e) {
			
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			String errorMessage = errors.toString();		
			
			this.setException(errorMessage);
			
		}
		
	}

	private void generateXhtml(String xhtmlFileName) throws ParserConfigurationException, SAXException, IOException,
			TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		File xhtmlFile = new File(realFormPath, xhtmlFileName);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(getXhtml())));
		DOMSource source = new DOMSource(doc);
		Result xhtmlResult = new StreamResult(xhtmlFile);
		
		this.setPreview(formPath + xhtmlFileName);
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(source, xhtmlResult);
	}

	public String getPreview() {
		return getPreviewSrc();
	}

	public void setPreview(String previewSrc) {
		this.setPreviewSrc(previewSrc);
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getPreviewSrc() {
		return previewSrc;
	}

	public void setPreviewSrc(String previewSrc) {
		this.previewSrc = previewSrc;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

}
