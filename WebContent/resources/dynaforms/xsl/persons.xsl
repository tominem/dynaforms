<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

	<xsl:output method="xml"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />

	<xsl:template match="/">
		<html xmlns="http://www.w3.org/1999/xhtml"
			xmlns:h="http://java.sun.com/jsf/html"
			xmlns:f="http://java.sun.com/jsf/core"
			xmlns:ui="http://java.sun.com/jsf/facelets"
			xmlns:p="http://primefaces.org/ui"
			xmlns:pe="http://primefaces.org/ui/extensions">
			<h:head>
				<f:facet name="first">
					<meta name="viewport" content="width=device-width, initial-scale=1" />
				</f:facet>
			</h:head>
			<h:body>
			
				<style type="text/css">
					.ui-g div {
					    background-color: #ededed;
					    text-align: center;
					    border: 1px solid #fafafa;
					}
        		</style>

				<h:form id="mainForm" prependId="false">
					<p:outputPanel>
						<div class="ui-g">
						    <div class="ui-md-6 ui-lg-3">ui-md-6 ui-lg-3</div>
						    <div class="ui-md-6 ui-lg-3">ui-md-6 ui-lg-3</div>
						    <div class="ui-md-6 ui-lg-3">ui-md-6 ui-lg-3</div>
						    <div class="ui-md-6 ui-lg-3">ui-md-6 ui-lg-3</div>
						</div>
					</p:outputPanel>
				</h:form>

			</h:body>
		</html>
	</xsl:template>
</xsl:stylesheet>