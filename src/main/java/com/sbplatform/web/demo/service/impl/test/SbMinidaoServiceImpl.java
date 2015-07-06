package com.sbplatform.web.demo.service.impl.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbplatform.web.demo.dao.test.SbMinidaoDao;
import com.sbplatform.web.demo.entity.test.SbMinidaoEntity;
import com.sbplatform.web.demo.service.test.SbMinidaoServiceI;

/**
 * Minidao例子
 * @author fancq
 *
 */
@Service("sbMinidaoService")
@Transactional
public class SbMinidaoServiceImpl implements SbMinidaoServiceI {
	@Autowired
	private SbMinidaoDao sbMinidaoDao;
	
	public List<SbMinidaoEntity> listAll(SbMinidaoEntity sbMinidao, int page, int rows) {
		List<SbMinidaoEntity> entities = sbMinidaoDao.getAllEntities2(sbMinidao, page, rows);
		return entities;
	}
	
	public SbMinidaoEntity getEntity(Class clazz, String id) {
		SbMinidaoEntity sbMinidao = (SbMinidaoEntity)sbMinidaoDao.getByIdHiber(clazz, id);
		return sbMinidao;
	}
	
	public void insert(SbMinidaoEntity sbMinidao) {
		sbMinidaoDao.saveByHiber(sbMinidao);
	}
	
	public void update(SbMinidaoEntity sbMinidao) {
		sbMinidaoDao.updateByHiber(sbMinidao);
	}
	
	public void delete(SbMinidaoEntity sbMinidao) {
		sbMinidaoDao.deleteByHiber(sbMinidao);
	}
	
	public void deleteAllEntitie(List<SbMinidaoEntity> entities) {
		for (SbMinidaoEntity entity : entities) {
			sbMinidaoDao.deleteByHiber(entity);
		}
	}
	
	public Integer getCount() {
		return sbMinidaoDao.getCount();
	}
	
	public Integer getSumSalary() {
		return sbMinidaoDao.getSumSalary();
	}
}