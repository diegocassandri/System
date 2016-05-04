package com.system.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.system.util.jsf.FacesMessages;

@Named
@ViewScoped
public class CalculoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesMessages messages;

	private double salario;
	private double liquido;
	private double comissoes;
	private double horasExtras;
	private int diasUteis;
	private int diasNaoUteis;
	
	
	public void calcular(){
		double valorHe = (salario / 220.0) * horasExtras;
		double dsrComissoes = (comissoes / diasUteis) * diasNaoUteis ;
		double dsrHe = (valorHe / diasUteis) * diasNaoUteis ;
		liquido = salario + comissoes + valorHe + dsrComissoes + dsrHe;
		messages.info("Valor Liquido: " + liquido);
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public double getLiquido() {
		return liquido;
	}

	public void setLiquido(double liquido) {
		this.liquido = liquido;
	}

	public double getComissoes() {
		return comissoes;
	}

	public void setComissoes(double comissoes) {
		this.comissoes = comissoes;
	}

	public double getHorasExtras() {
		return horasExtras;
	}

	public void setHorasExtras(double horasExtras) {
		this.horasExtras = horasExtras;
	}

	public int getDiasUteis() {
		return diasUteis;
	}

	public void setDiasUteis(int diasUteis) {
		this.diasUteis = diasUteis;
	}

	public int getDiasNaoUteis() {
		return diasNaoUteis;
	}

	public void setDiasNaoUteis(int diasNaoUteis) {
		this.diasNaoUteis = diasNaoUteis;
	}
	
	
	
}
