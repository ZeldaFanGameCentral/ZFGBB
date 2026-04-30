package com.zfgc.zfgbb.migrator.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.dbo.CategoryDbo;
import com.zfgc.zfgbb.mappers.CategoryDboMapper;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFCategoryDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFCategoryDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFCategoryDbMapper;

@Component
public class CategoryConverter extends AbstractConverter {
	
	@Autowired
	public SMFCategoryDbMapper smfCategoryMapper;
	
	@Autowired
	public CategoryDboMapper categoryDboMapper;
	
	public Map<Integer,CategoryDbo> convertToZfgbb() {
		List<SMFCategoryDb> SMFCategories = smfCategoryMapper.selectByExample(new SMFCategoryDbExample());
		Map<Integer,CategoryDbo> result = new HashMap<>();
		
		SMFCategories.forEach((smfCategory) -> {
			Cancellable.check();
			CategoryDbo cat = new CategoryDbo();
			
			cat.setCategoryId(smfCategory.getIdCat());
			cat.setCategoryName(smfCategory.getName());

			cat.setMigrationHash(MigrationHasher.hash(cat.getCategoryId()
					+ cat.getCategoryName()
					+ (cat.getDescription() == null ? "" : cat.getDescription())
					+ (cat.getParentBoardId() == null ? "" : cat.getParentBoardId())));
			
			result.put(cat.getCategoryId(), cat);
			
			
			CategoryDbo existingCat = categoryDboMapper.selectByPrimaryKey(cat.getCategoryId());
			if(existingCat == null) {
				categoryDboMapper.insert(cat);
			}
			else if(!existingCat.getMigrationHash().equals(cat.getMigrationHash())) {
				categoryDboMapper.updateByPrimaryKey(cat);
			}
		});
		
		return result;
	}
}
