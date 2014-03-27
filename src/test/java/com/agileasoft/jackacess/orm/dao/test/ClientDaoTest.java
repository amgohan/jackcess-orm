package com.agileasoft.jackacess.orm.dao.test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.agileasoft.jackacess.orm.dao.ClientDao;
import com.agileasoft.jackacess.orm.model.Client;
import com.agileasoft.jackcess.orm.dao.AccessDao;

public class ClientDaoTest {
	private AccessDao<Client> clientDao;

	@Before
	public void setUp() throws Exception {
		File mdbFile = new File("./target/test-classes/bd1.mdb");
		clientDao = new ClientDao(mdbFile);
	}

	@Test
	public void findAllTest() throws IOException {
		List<Client> listClients = clientDao.findAll();
		assertNotNull(listClients);
		assertEquals(3, listClients.size());
		// idClient
		assertEquals(1, listClients.get(0).getIdClient().intValue());
		assertEquals(2, listClients.get(1).getIdClient().intValue());
		assertEquals(3, listClients.get(2).getIdClient().intValue());
		// dateNaissance
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals("2014-03-11", sdf.format(listClients.get(0).getDateNaissance()));
		assertEquals("2013-09-17", sdf.format(listClients.get(1).getDateNaissance()));
		assertNull(listClients.get(2).getDateNaissance());
		// nomClient
		assertEquals("client 1 é", listClients.get(0).getNomClient());
		assertEquals("client 2 à", listClients.get(1).getNomClient());
		assertNull(listClients.get(2).getNomClient());
		// soldeClient
		assertEquals(new BigDecimal("125.1500"), listClients.get(0).getSoldeClient());
		assertEquals(new BigDecimal("0.0000"), listClients.get(1).getSoldeClient());
		assertEquals(new BigDecimal("15.0000"), listClients.get(2).getSoldeClient());
	}

}
