package com.system.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.system.model.Estado;
import com.system.repository.Estados;
import com.system.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Estado.class)
public class EstadoConverter implements Converter {
	// @Inject (ainda não é suportado)
	private Estados estados;

	public EstadoConverter() {
		this.estados = CDIServiceLocator.getBean(Estados.class);
	}

	/*@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Lancamento retorno = null;
		if (value != null) {
			retorno = this.lancamentos.porId(new Long(value));
		}
		return retorno;
	}*/
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Estado retorno = null;
		if (value != null && !"".equals(value)) {
			retorno = this.estados.porId(new Long(value));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Estado estado = ((Estado) value);
			return estado.getCodigo() == null ? null : estado.getCodigo().toString();
		}
		return null;
	}
}
