package com.system.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.system.model.Lancamento;
import com.system.repository.Lancamentos;
import com.system.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Lancamento.class)
public class LancamentosConverter implements Converter {
	// @Inject (ainda não é suportado)
	private Lancamentos lancamentos;

	public LancamentosConverter() {
		this.lancamentos = CDIServiceLocator.getBean(Lancamentos.class);
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
		Lancamento retorno = null;
		if (value != null && !"".equals(value)) {
			retorno = this.lancamentos.porId(new Long(value));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Lancamento lancamento = ((Lancamento) value);
			return lancamento.getId() == null ? null : lancamento.getId().toString();
		}
		return null;
	}
}
