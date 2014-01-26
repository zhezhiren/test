package com.plj.service.sys.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plj.dao.sys.FunctionDao;
import com.plj.domain.decorate.sys.Function;
import com.plj.service.sys.FunctionService;

/**
 * 
 * @author gdy
 *
 */
@Transactional
@Service("functionService")
public class FunctionServiceImpl implements FunctionService {

	@Override
	public Function insertFunction(Function function) {
		int i = functionDao.insert(function);
		if (i == 1) {
			return function;// TODO
		} else {
			return null;
		}
	}

	@Override
	public Integer updateFunction(Function function) {
		int i = functionDao.update(function);
		return i;
	}

	@Override
	public Integer deleteByIds(List<Integer> ids) {
		int result = 0;
		for (int i = 0; i < ids.size(); i++) {
			int count = functionDao.deleteById(ids.get(i));
			result += count;
		}
		return result;
	}

	public Integer deleteById(Integer id) {
		return functionDao.deleteById(id);
	}

	@SuppressWarnings("rawtypes")
	public List<Function> searchFunction(HashMap map) {
		return functionDao.searchFunction(map);
	}

	@Autowired
	private FunctionDao functionDao;
}
