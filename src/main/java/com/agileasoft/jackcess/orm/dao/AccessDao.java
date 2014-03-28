package com.agileasoft.jackcess.orm.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.agileasoft.jackcess.orm.mapper.AccessMapper;
import com.healthmarketscience.jackcess.CursorBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.JackcessException;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

public abstract class AccessDao<E> {
	protected AccessMapper<E> mapper;
	private File accessFile;
	private Database db;
	private Table table;
	private Class<E> clazz;

	public AccessDao(File accessFile, Class<E> clazz) throws IOException {
		this.accessFile = accessFile;
		this.clazz = clazz;
		this.mapper = new AccessMapper<E>(clazz);
		db = DatabaseBuilder.open(this.accessFile);
		table = db.getTable(mapper.getTableName());
		if(table == null) {
			throw new JackcessException("relation " + mapper.getTableName() + "  does not exist");
		}
	}

	public List<E> findAll() throws IOException {
		List<E> listResult = new ArrayList<E>();
		for (Row row : table) {
			listResult.add(mapper.getMappedObject(row));
		}
		return listResult;
	}
	
	public E findById(Object id) throws IOException {
		final Row row = _findById(id);
		return mapper.getMappedObject(row);
	}
	
	
	
	public E findOne() throws IOException {
		for (Row row : table) {
			return mapper.getMappedObject(row);
		}
		return null;
	}
	
	public boolean save(E e) throws IOException {
		Map<String, Object> row = mapper.getMappedRow(e, true);
		table.addRowFromMap(row);
		return true;
	}
	
	public List<Boolean> save(List<E> l) throws IOException {
		List<Boolean> res = new ArrayList<Boolean>();
		for(E e : l) {
			if(e != null) {
				res.add(save(e));
			} else {
				res.add(false);
			}
		}
		return res;
	}
	
	public boolean removeById(Object id) throws IOException {
		final Row row = _findById(id);
		if(row != null) {
			table.deleteRow(row);
			return true;
		}
		return false;
	}
	
	public boolean removeFirst(E e) throws IOException {
		if (mapper.hasId()) {
			return removeById(mapper.getId(e));
		}
		Row row = CursorBuilder.findRow(table, mapper.getMappedRow(e, false));
		if (row != null) {
			table.deleteRow(row);
			return true;
		}
		return false;
	}
	
	protected Row _findById(Object id) throws IOException {
		if(!mapper.hasId()) {
			throw new JackcessException("this class " + clazz.getCanonicalName() + " does not have an @Id annotation.");
		}
		final String idColumnName = mapper.getDbColumn(mapper.getAttributeIdName());
		Row row = CursorBuilder.findRow(table, Collections.singletonMap(idColumnName, id));
		return row;
	}
	
	
}
