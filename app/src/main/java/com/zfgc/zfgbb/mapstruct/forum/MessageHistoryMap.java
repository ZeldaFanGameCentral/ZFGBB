package com.zfgc.zfgbb.mapstruct.forum;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.CurrentMessageDbo;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.model.forum.Message;
import com.zfgc.zfgbb.model.forum.MessageHistory;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface MessageHistoryMap {
	@Mapping(target="id", ignore=true)
	@Mapping(target="unparsedText", ignore=true)
	MessageHistory toModel(MessageHistoryDbo dbo);

	@Mapping(target="id", ignore=true)
	@Mapping(target="unparsedText", ignore=true)
	@Mapping(target="currentFlag", ignore=true)
	@Mapping(target="ipAddressId", ignore=true)
	MessageHistory toModel(CurrentMessageDbo dbo);

	@Mapping(target="migrationHash", ignore=true)
	MessageHistoryDbo toDbo(MessageHistory model);

	@AfterMapping
	default void applyUnparsedText(MessageHistory model, @MappingTarget MessageHistoryDbo dbo) {
		if (model.getUnparsedText() != null)
			dbo.setMessageText(model.getUnparsedText());
	}

	@Mapping(target="messageHistoryId", ignore=true)
	@Mapping(target="messageText", ignore=true)
	@Mapping(target="currentFlag", ignore=true)
	@Mapping(target="ipAddressId", ignore=true)
	@Mapping(target="migrationHash", ignore=true)
	MessageHistoryDbo toDbo(Message message);
}
