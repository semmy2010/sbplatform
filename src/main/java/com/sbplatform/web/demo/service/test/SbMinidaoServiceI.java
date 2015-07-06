package com.sbplatform.web.demo.service.test;

import java.util.List;

import com.sbplatform.web.demo.entity.test.SbMinidaoEntity;

/**
 * Minidao例子
 * @author fancq
 *
 */
public interface SbMinidaoServiceI {
	public List<SbMinidaoEntity> listAll(SbMinidaoEntity sbMinidao, int page, int rows);
	
	public SbMinidaoEntity getEntity(Class clazz, String id);
	
	public void insert(SbMinidaoEntity sbMinidao);
	
	public void update(SbMinidaoEntity sbMinidao);
	
	public void delete(SbMinidaoEntity sbMinidao);
	
	public void deleteAllEntitie(List<SbMinidaoEntity> entities);
	
	public Integer getCount();
	
	public Integer getSumSalary();
}
