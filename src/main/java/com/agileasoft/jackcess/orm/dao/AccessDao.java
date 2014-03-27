package com.agileasoft.jackcess.orm.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.agileasoft.jackcess.orm.mapper.AccessMapper;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

public abstract class AccessDao<E> {
	protected AccessMapper<E> mapper;
	private File accessFile;

	public AccessDao(File accessFile, Class<E> clazz) {
		this.accessFile = accessFile;
		this.mapper = new AccessMapper<E>(clazz);
	}

	public List<E> findAll() throws IOException {
		Database db = DatabaseBuilder.open(accessFile);
		Table table = db.getTable(mapper.getTableName());
		List<E> listResult = new ArrayList<E>();
		for (Row row : table) {
			listResult.add(mapper.getMappedObject(row));
		}
		db.close();
		return listResult;
	}
}
