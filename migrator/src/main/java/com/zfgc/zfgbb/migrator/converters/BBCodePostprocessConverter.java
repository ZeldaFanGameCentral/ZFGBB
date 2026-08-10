package com.zfgc.zfgbb.migrator.converters;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ContentEntityDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.ProjectNewsDbo;
import com.zfgc.zfgbb.dbo.ProjectNewsDboExample;
import com.zfgc.zfgbb.dbo.TeamDbo;
import com.zfgc.zfgbb.dbo.TeamDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.mappers.ProjectNewsDboMapper;
import com.zfgc.zfgbb.mappers.TeamDboMapper;
import com.zfgc.zfgbb.mappers.UserBioInfoDboMapper;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BBCodePostprocessConverter extends AbstractConverter<Void> {

	private static final Logger logger = LoggerFactory.getLogger(BBCodePostprocessConverter.class);

	private final MessageHistoryDboMapper messageHistoryMapper;

	private final UserBioInfoDboMapper userBioInfoMapper;

	private final ContentEntityDboMapper contentEntityMapper;

	private final ProjectNewsDboMapper projectNewsMapper;

	private final TeamDboMapper teamMapper;

	private final MigratorIdMapService idMap;

	private final LegacyMarkupRewriter legacyMarkupRewriter;

	@Override
	public JobType getType() {
		return JobType.BBCODE_REWRITE;
	}

	@Override
	@Transactional
	public Void convertToZfgbb() {
		LegacyIdMaps maps = new LegacyIdMaps(
				idMap.getAllForType(LegacyEntityType.THREAD),
				idMap.getAllForType(LegacyEntityType.MESSAGE),
				idMap.getAllForType(LegacyEntityType.BOARD),
				idMap.getAllForType(LegacyEntityType.USER),
				idMap.getAllForType(LegacyEntityType.ATTACHMENT),
				idMap.getAllForType(LegacyEntityType.GAME));

		LegacyUrlRewriter rewriter = LegacyUrlRewriter.forLegacyHost(
				JobContextHolder.getLegacyHost(),
				JobContextHolder.getAppBaseUrl());

		rewriteAll("message_history rows", rewriter, maps,
				messageHistoryMapper.selectByExample(new MessageHistoryDboExample()),
				MessageHistoryDbo::getMessageText, MessageHistoryDbo::setMessageText,
				messageHistoryMapper::updateByPrimaryKey);
		rewriteAll("signature(s)", rewriter, maps,
				userBioInfoMapper.selectByExample(new UserBioInfoDboExample()),
				UserBioInfoDbo::getSignature, UserBioInfoDbo::setSignature,
				userBioInfoMapper::updateByPrimaryKey);
		rewriteAll("content entity summaries", rewriter, maps,
				contentEntityMapper.selectByExample(new ContentEntityDboExample()),
				ContentEntityDbo::getSummary, ContentEntityDbo::setSummary,
				contentEntityMapper::updateByPrimaryKey);
		rewriteAll("project news bodies", rewriter, maps,
				projectNewsMapper.selectByExample(new ProjectNewsDboExample()),
				ProjectNewsDbo::getBody, ProjectNewsDbo::setBody,
				projectNewsMapper::updateByPrimaryKey);
		rewriteAll("team descriptions", rewriter, maps,
				teamMapper.selectByExample(new TeamDboExample()),
				TeamDbo::getDescription, TeamDbo::setDescription,
				teamMapper::updateByPrimaryKey);
		return null;
	}

	private <T> void rewriteAll(String what, LegacyUrlRewriter rewriter, LegacyIdMaps maps, List<T> rows,
			Function<T, String> read, BiConsumer<T, String> write, Consumer<T> save) {
		int rewritten = 0;
		for (T row : rows) {
			Cancellable.check();
			String body = read.apply(row);
			if (body == null) {
				continue;
			}
			String updated = legacyMarkupRewriter.rewriteRetiredCodes(rewriter.rewriteBody(body, maps));
			if (!updated.equals(body)) {
				write.accept(row, updated);
				save.accept(row);
				rewritten++;
			}
		}
		if (rewritten > 0) {
			logger.info("Rewrote BBCode references in {} {}", rewritten, what);
		}
	}
}
