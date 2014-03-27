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
