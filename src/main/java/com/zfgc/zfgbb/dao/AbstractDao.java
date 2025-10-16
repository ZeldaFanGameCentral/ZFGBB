package com.zfgc.zfgbb.dao;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.zfgc.zfgbb.dbo.AbstractDbo;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;

public abstract class AbstractDao<DbExample, DbMapper, Dbo extends AbstractDbo> {
	
	protected DbMapper mapper;
	public abstract Optional<Dbo> get(Integer id);
	public abstract List<Dbo> get(DbExample ex);
	
	public AbstractDao(DbMapper mapper) {
        this.mapper = mapper;
    }
	
	public Dbo save(Dbo toSave) {
		
		if(toSave.getPkId() == null) {
			create(toSave);
		}
		else {
			//get record from database
			Dbo existing = get(toSave.getPkId()).orElseThrow(() -> new ZfgcNotFoundException());
			if(existing.getUpdatedTime().isAfter(toSave.getUpdatedTime())) {
				//concurrency problem, get this garbo outta here
				throw new ConcurrentModificationException();
			}
		
			update(toSave);
		}
		
		//return the most up to date version of the record
		return get(toSave.getPkId()).get();
	}
	
	public DbMapper getMapper() {
		return mapper;
	}
	
	protected abstract void update(Dbo toSave);
	protected abstract void create(Dbo toCreate);
	public void delete(DbExample id) { throw new RuntimeException("delete not supported"); }
	
}