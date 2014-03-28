jackcess-orm
============

ORM (with JPA annotations) for MS Access using jackcess

Download
----
Download [jackcess-orm-0.0.1.jar]


How to use
----

Create your model Entity (Client for example)

> if you don't specify name attribute for @Column it will auto convert the name from camelCase to underscoreCase and vis versa

```java
package com.agileasoft.jackacess.orm.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "TEST_CLIENT")
public class Client {
	@Column
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
```

Create your DAO for Client class

```java
package com.agileasoft.jackacess.orm.dao;

import java.io.File;
import java.io.IOException;

import com.agileasoft.jackacess.orm.model.Client;
import com.agileasoft.jackcess.orm.dao.AccessDao;

public class ClientDao extends AccessDao<Client> {

	public ClientDao(File accessFile) throws IOException {
		super(accessFile, Client.class);
	}
}
```
> DAO take a File Object pointing to your MS Access File
> See the junit testCase

Main class

```java
public static void main(String[] args) throws IOException {
  AccessDao<Client> clientDao = new new ClientDao(new File("/path/to/your/mdb/file.mdb"));
  List<Client> listClients = clientDao.findAll();
  for(Client client : listClients) {
    System.out.println(client.toString());
  }
}
```

[jackcess-orm-0.0.1.jar]:https://db.tt/nvlDTjc4
