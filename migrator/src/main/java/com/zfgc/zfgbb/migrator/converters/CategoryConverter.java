package com.zfgc.zfgbb.migrator.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.CategoryDbo;
import com.zfgc.zfgbb.mappers.CategoryDboMapper;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFCategoryDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFCategoryDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFCategoryDbMapper;

@Component
public class CategoryConverter extends AbstractConverter<Map<Integer, CategoryDbo>> {

	@Autowired
	public SMFCategoryDbMapper smfCategoryMapper;

	@Autowired
	public CategoryDboMapper categoryDboMapper;

	@Autowired
	public MigratorIdMapService idMap;

	@Override
	public JobType getType() {
		return JobType.CATEGORIES;
	}

	@Override
	@Transactional
	public Map<Integer, CategoryDbo> convertToZfgbb() {
		List<SMFCategoryDb> smfCategories = smfCategoryMapper.selectByExample(new SMFCategoryDbExample());
		Map<Integer, CategoryDbo> result = new HashMap<>();

		smfCategories.forEach((smfCategory) -> {
			Cancellable.check();
			CategoryDbo category = new CategoryDbo();

			category.setCategoryName(smfCategory.getName());
			category.setCategoryOrder(smfCategory.getCatOrder() != null
					? smfCategory.getCatOrder().shortValue()
					: 0);

			category.setMigrationHash(MigrationHasher.hash(smfCategory.getIdCat().toString()
					+ category.getCategoryName()
					+ category.getCategoryOrder()
					+ (category.getDescription() == null ? "" : category.getDescription())
					+ (category.getParentBoardId() == null ? "" : category.getParentBoardId())));

			Integer existingZfgbbId = idMap.lookupOrNull(LegacyEntityType.CATEGORY, smfCategory.getIdCat());
			if (existingZfgbbId == null) {
				categoryDboMapper.insert(category);
				idMap.record(LegacyEntityType.CATEGORY, smfCategory.getIdCat(), category.getCategoryId());
			} else {
				CategoryDbo existing = categoryDboMapper.selectByPrimaryKey(existingZfgbbId);
				if (existing == null) {
					category.setCategoryId(existingZfgbbId);
					categoryDboMapper.insert(category);
				} else if (JobContextHolder.isForce() || !Objects.equals(existing.getMigrationHash(), category.getMigrationHash())) {
					category.setCategoryId(existingZfgbbId);
					categoryDboMapper.updateByPrimaryKey(category);
				} else {
					category.setCategoryId(existingZfgbbId);
				}
			}

			result.put(smfCategory.getIdCat(), category);
		});

		return result;
	}
}
