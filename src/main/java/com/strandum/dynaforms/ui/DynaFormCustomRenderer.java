package com.strandum.dynaforms.ui;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.extensions.component.dynaform.DynaForm;
import org.primefaces.extensions.component.dynaform.DynaFormRenderer;
import org.primefaces.extensions.component.dynaform.UIDynaFormControl;
import org.primefaces.extensions.model.dynaform.AbstractDynaFormElement;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormModelElement;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.util.Constants;

public class DynaFormCustomRenderer extends DynaFormRenderer{
	
    private static final Logger LOGGER = Logger.getLogger(DynaFormCustomRenderer.class.getName());

    private static final String FACET_HEADER_REGULAR = "headerRegular";
    private static final String FACET_FOOTER_REGULAR = "footerRegular";
    private static final String FACET_HEADER_EXTENDED = "headerExtended";
    private static final String FACET_FOOTER_EXTENDED = "footerExtended";
    private static final String FACET_BUTTON_BAR = "buttonBar";

    private static final String GRID_CLASS = "ui-g";
    private static final String NESTED_GRID_CLASS = "pe-dynaform-nested-grid";
    private static final String CELL_CLASS = "";
    private static final String CELL_FIRST_CLASS = "pe-dynaform-cell-first";
    private static final String CELL_LAST_CLASS = "pe-dynaform-cell-last";
    private static final String LABEL_CLASS = "pe-dynaform-label";
    private static final String LABEL_INVALID_CLASS = "ui-state-error ui-corner-all";
    private static final String LABEL_INDICATOR_CLASS = "pe-dynaform-label-rfi";
    private static final String LABEL_CONTROL_TYPE_CLASS_FORMAT = "pe-dynaform-%s-label";

    private static final String FACET_BUTTON_BAR_TOP_CLASS = "pe-dynaform-buttonbar-top";
    private static final String FACET_BUTTON_BAR_BOTTOM_CLASS = "pe-dynaform-buttonbar-bottom";
    private static final String FACET_HEADER_CLASS = "pe-dynaform-headerfacet";
    private static final String FACET_FOOTER_CLASS = "pe-dynaform-footerfacet";
    private static final String EXTENDED_ROW_CLASS = "pe-dynaform-extendedrow";

    private static final String BUTTON_BAR_ROLE = "toolbar";
    private static final String GRID_CELL_ROLE = "gridcell";

    private static final String[] EMPTY_COLUMN_CLASSES = new String[] { Constants.EMPTY_STRING, Constants.EMPTY_STRING };
	
	@Override
	protected void encodeMarkup(FacesContext fc, DynaForm dynaForm, DynaFormModel dynaFormModel, boolean nestedGrid)
			throws IOException {
	       ResponseWriter writer = fc.getResponseWriter();

	        writer.startElement("div", dynaForm);

	        if (!nestedGrid) {
	            String clientId = dynaForm.getClientId(fc);
	            writer.writeAttribute("id", clientId, "id");
	        }

	        String styleClass = nestedGrid ? NESTED_GRID_CLASS : GRID_CLASS;
	        styleClass += dynaForm.getStyleClass() == null ? Constants.EMPTY_STRING : " " + dynaForm.getStyleClass();

	        //writer.writeAttribute("cellspacing", "0", "cellspacing");
	        writer.writeAttribute("class", styleClass, "styleClass");
	        if (dynaForm.getStyle() != null) {
	            writer.writeAttribute("style", dynaForm.getStyle(), "style");
	        }

	        //writer.writeAttribute("role", "grid", null);

	        // prepare labels with informations about corresponding control components
	        preRenderLabel(fc, dynaForm, dynaFormModel);

	        int totalColspan = getTotalColspan(dynaFormModel);
	        String bbPosition = dynaForm.getButtonBarPosition();

	        if (!nestedGrid && "top".equals(bbPosition) || "both".equals(bbPosition)) {
	            encodeFacet(fc, dynaForm, FACET_BUTTON_BAR, totalColspan, FACET_BUTTON_BAR_TOP_CLASS, BUTTON_BAR_ROLE, false, true);
	        }

	        if (!nestedGrid) {
	            encodeFacet(fc, dynaForm, FACET_HEADER_REGULAR, totalColspan, FACET_HEADER_CLASS, GRID_CELL_ROLE, false, true);
	        }

	        // encode regular grid
	        encodeBody(fc, dynaForm, dynaFormModel.getRegularRows(), false, true);

	        if (!nestedGrid) {
	            encodeFacet(fc, dynaForm, FACET_FOOTER_REGULAR, totalColspan, FACET_FOOTER_CLASS, GRID_CELL_ROLE, false, true);
	            encodeFacet(fc, dynaForm, FACET_HEADER_EXTENDED, totalColspan, FACET_HEADER_CLASS, GRID_CELL_ROLE, true,
	                        dynaForm.isOpenExtended());
	        }
	        // encode extended grid
	        encodeBody(fc, dynaForm, dynaFormModel.getExtendedRows(), true, dynaForm.isOpenExtended());

	        if (!nestedGrid) {
	            encodeFacet(fc, dynaForm, FACET_FOOTER_EXTENDED, totalColspan, FACET_FOOTER_CLASS, GRID_CELL_ROLE, true,
	                        dynaForm.isOpenExtended());
	        }

	        if (!nestedGrid && "bottom".equals(bbPosition) || "both".equals(bbPosition)) {
	            encodeFacet(fc, dynaForm, FACET_BUTTON_BAR, totalColspan, FACET_BUTTON_BAR_BOTTOM_CLASS, BUTTON_BAR_ROLE, false, true);
	        }

	        writer.endElement("div");
	}
	
	@Override
	protected void encodeBody(FacesContext fc, DynaForm dynaForm, List<DynaFormRow> dynaFormRows, boolean extended,
			boolean visible) throws IOException {
		
        if (dynaFormRows == null || dynaFormRows.isEmpty()) {
            return;
        }

        ResponseWriter writer = fc.getResponseWriter();

        String columnClassesValue = dynaForm.getColumnClasses();
        String[] columnClasses = columnClassesValue == null ? EMPTY_COLUMN_CLASSES : columnClassesValue.split(",");
        String labelCommonClass = columnClasses[0].trim();
        String controlCommonClass = columnClasses.length > 1 ? columnClasses[1].trim() : EMPTY_COLUMN_CLASSES[1];

        for (DynaFormRow dynaFormRow : dynaFormRows) {
//            writer.startElement("div", null);
//            if (extended) {
//                writer.writeAttribute("class", EXTENDED_ROW_CLASS, null);
//            }
//            
//            writer.writeAttribute("class", CELL_CLASS, null);
//
//            if (!visible) {
//                writer.writeAttribute("style", "display:none;", null);
//            }

            //writer.writeAttribute("role", "row", null);

            List<AbstractDynaFormElement> elements = dynaFormRow.getElements();
            int size = elements.size();

            for (int i = 0; i < size; i++) {
                AbstractDynaFormElement element = elements.get(i);

                writer.startElement("div", null);
//                if (element.getColspan() > 1) {
//                    writer.writeAttribute("colspan", element.getColspan(), null);
//                }

//                if (element.getRowspan() > 1) {
//                    writer.writeAttribute("rowspan", element.getRowspan(), null);
//                }

                String styleClass = CELL_CLASS;
//                if (i == 0 && element.getColspan() == 1) {
//                    styleClass = styleClass + " " + CELL_FIRST_CLASS;
//                }
//
//                if (i == size - 1 && element.getColspan() == 1) {
//                    styleClass = styleClass + " " + CELL_LAST_CLASS;
//                }

                if (element instanceof DynaFormLabel) {
                    // render label
                    DynaFormLabel label = (DynaFormLabel) element;

                    writer.writeAttribute("class", (styleClass 
                                + " " + LABEL_CLASS
                                + " " + StringUtils.defaultIfBlank(label.getStyleClass(), Constants.EMPTY_STRING)
                                + " " + labelCommonClass).trim(), null);
//                    writer.writeAttribute("role", GRID_CELL_ROLE, null);

                    writer.startElement("label", null);
                    if (!label.isTargetValid()) {
                        writer.writeAttribute("class", LABEL_INVALID_CLASS, null);
                    }

                    writer.writeAttribute("for", label.getTargetClientId(), null);

                    if (label.getValue() != null) {
                        if (label.isEscape()) {
                            writer.writeText(label.getValue(), "value");
                        }
                        else {
                            writer.write(label.getValue());
                        }
                    }

                    if (label.isTargetRequired()) {
                        writer.startElement("span", null);
                        writer.writeAttribute("class", LABEL_INDICATOR_CLASS, null);
                        writer.write("*");
                        writer.endElement("span");
                    }

                    writer.endElement("label");
                }
                else if (element instanceof DynaFormControl) {
                    // render control
                    DynaFormControl control = (DynaFormControl) element;
                    dynaForm.setData(control);

                    // find control's cell by type
                    UIDynaFormControl cell = dynaForm.getControlCell(control.getType());

                    if (cell.getStyle() != null) {
                        writer.writeAttribute("style", cell.getStyle(), null);
                    }

                    if (cell.getStyleClass() != null) {
                        styleClass = styleClass + " " + cell.getStyleClass();
                    } else {
                    	styleClass = styleClass + " ui-g-12 ui-md-6 ui-lg-3";
                    }

                    writer.writeAttribute("class", (styleClass + " " + controlCommonClass).trim(), null);
//                    writer.writeAttribute("role", GRID_CELL_ROLE, null);

                    cell.encodeAll(fc);
                }
                else if (element instanceof DynaFormModelElement) {
                    DynaFormModelElement nestedModel = (DynaFormModelElement) element;

                    // render nested model
                    writer.writeAttribute("class", styleClass, null);
                    writer.writeAttribute("role", GRID_CELL_ROLE, null);

                    encodeMarkup(fc, dynaForm, nestedModel.getModel(), true);
                }

                writer.endElement("div");
            }

//            writer.endElement("div");
        }

        dynaForm.resetData();
		
	}
	
	protected void encodeFacet(FacesContext fc, DynaForm dynaForm, String name, int totalColspan, String styleClass,
			String role, boolean extended, boolean visible) throws IOException {
		final UIComponent facet = dynaForm.getFacet(name);
		if (facet != null && facet.isRendered()) {
			ResponseWriter writer = fc.getResponseWriter();
			writer.startElement("div", null);
			if (extended) {
				writer.writeAttribute("class", EXTENDED_ROW_CLASS, null);
			}
			
			writer.writeAttribute("class", "ui-g-12", null);

			if (!visible) {
				writer.writeAttribute("style", "display:none;", null);
			}

//			writer.writeAttribute("role", "row", null);
			writer.startElement("div", null);
//			if (totalColspan > 1) {
//				writer.writeAttribute("colspan", totalColspan, null);
//			}

			writer.writeAttribute("class", styleClass, null);
//			writer.writeAttribute("role", role, null);

			facet.encodeAll(fc);

			writer.endElement("div");
			writer.endElement("div");
		}
	}
	
}
