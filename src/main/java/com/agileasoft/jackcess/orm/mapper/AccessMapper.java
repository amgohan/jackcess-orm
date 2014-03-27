package com.agileasoft.jackcess.orm.mapper;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;

import org.apache.commons.lang.WordUtils;

import com.healthmarketscience.jackcess.JackcessException;
import com.healthmarketscience.jackcess.Row;

public class AccessMapper<E> {
	private String _tableName;
	final private Map<String, String> _mapColumnsJavaKey = new HashMap<String, String>();
	final private Map<String, String> _mapColumnsDbKey = new HashMap<String, String>(); 
	final private Map<String, String> _mapFieldsType = new HashMap<String, String>();
	Class<E> clazz;
	
	public AccessMapper(Class<E> clazz) {
		this.clazz = clazz;
		Entity entityAnnotation = clazz.getAnnotation(Entity.class);
		if(entityAnnotation != null) {
			_tableName =  entityAnnotation.name();
		}
		if(_tableName == null || "".equals(_tableName)) {
			throw new EntityExistsException("@Entity must have name attribute defined");
		}
		
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			_mapFieldsType.put(field.getName(), field.getType().getCanonicalName());
			Column columnAnnotation = field.getAnnotation(Column.class);
			if(columnAnnotation != null) {
				String dbColumnName = columnAnnotation.name();
				if(dbColumnName == null || "".equals(dbColumnName)) {
					dbColumnName = camelcaseToUnderscore(field.getName());
				}
				_mapColumnsJavaKey.put(field.getName(), dbColumnName);
				_mapColumnsDbKey.put(dbColumnName, field.getName());
			}
		}
	}
	
	public String getEntityAttribute(String dbColumn) {
		return _mapColumnsDbKey.get(dbColumn);
	}
	
	public String getDbColumn(String javaAttribute) {
		return _mapColumnsJavaKey.get(javaAttribute);
	}
	
	public String getTableName() {
		return _tableName;
	}
	
	public E getMappedObject(Row row) throws JackcessException {
		try {
			E entity = clazz.newInstance();
			for (String col : _mapColumnsDbKey.keySet()) {
	        		final String javaAttributeName = _mapColumnsDbKey.get(col);
	                final String nomSetterMethode = "set" + WordUtils.capitalize(javaAttributeName);
	                final Class<?> paramType = Class.forName(_mapFieldsType.get(javaAttributeName));
	                entity.getClass().getDeclaredMethod(nomSetterMethode, paramType).invoke(entity, row.get(col));
	        }
			return entity;
		} catch (Exception e) {
			throw new JackcessException(e);
		}
    }
	
	private String camelcaseToUnderscore (String value) {
		if(value == null) return null;
		String regex = "([a-z])([A-Z])+";
        String replacement = "$1_$2";
        return value.replaceAll(regex, replacement).toUpperCase();
	}
}
