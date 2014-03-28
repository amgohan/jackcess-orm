package com.agileasoft.jackacess.orm.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEST_CLIENT")
public class Client {
	@Column
	@Id
	@GeneratedValue
	private Integer idClient;
	
	@Column(name = "NOM_CLIENT")
	private String nomClient;
	
	@Column
	private Date dateNaissance;
	
	@Column(name = "SOLDE_CLIENT")
	private BigDecimal soldeClient;

	public Integer getIdClient() {
		return idClient;
	}

	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public BigDecimal getSoldeClient() {
		return soldeClient;
	}

	public void setSoldeClient(BigDecimal soldeClient) {
		this.soldeClient = soldeClient;
	}
	
	@Override
	public String toString() {
		return "idClient:" + idClient + ", nomClient:" + nomClient + ", dateNaissance:" + dateNaissance + ", soldeClient: " + soldeClient;
	}
}
