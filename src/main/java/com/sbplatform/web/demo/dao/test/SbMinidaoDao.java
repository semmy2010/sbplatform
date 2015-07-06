package com.sbplatform.web.demo.dao.test;

import java.util.List;
import java.util.Map;

import com.sbplatform.sbdao.annotation.Arguments;
import com.sbplatform.sbdao.annotation.MiniDao;
import com.sbplatform.sbdao.annotation.ResultType;
import com.sbplatform.sbdao.annotation.Sql;
import com.sbplatform.sbdao.hibernate.MiniDaoSupportHiber;

import com.sbplatform.web.demo.entity.test.SbMinidaoEntity;

/**
 * Minidao例子
 * @author fancq
 * 
 */
@MiniDao
public interface SbMinidaoDao extends MiniDaoSupportHiber<SbMinidaoEntity> {
	@Arguments({"sbMinidao", "page", "rows"})
	public List<Map> getAllEntities(SbMinidaoEntity sbMinidao, int page, int rows);

	@Arguments({"sbMinidao", "page", "rows"})
	@ResultType("com.sbplatform.web.demo.entity.test.SbMinidaoEntity")
	public List<SbMinidaoEntity> getAllEntities2(SbMinidaoEntity sbMinidao, int page, int rows);

	//@Arguments("id")
	//SbMinidaoEntity getSbMinidao(String id);

	@Sql("SELECT count(*) FROM sb_minidao")
	Integer getCount();

	@Sql("SELECT SUM(salary) FROM sb_minidao")
	Integer getSumSalary();

	/*@Arguments("sbMinidao")
	int update(SbMinidaoEntity sbMinidao);

	@Arguments("sbMinidao")
	void insert(SbMinidaoEntity sbMinidao);

	@Arguments("sbMinidao")
	void delete(SbMinidaoEntity sbMinidao);*/
}
