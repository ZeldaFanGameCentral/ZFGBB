package com.zfgc.zfgbb.migrator.jobs;
import java.util.List;
import java.util.stream.Stream;

public enum JobType {
	USERS,
	CATEGORIES,
	BOARDS,
	THREADS,
	MESSAGES,
	IPS,
	MESSAGE_HISTORY,
	USER_BIO_INFO,
	ATTACHMENTS,
	ATTACHMENT_FILES,
	USER_CONTACT_INFO,
	POLLS,
	POLL_CHOICES,
	USER_POLL_CHOICES,
	REACTIONS,
	MEMBER_GROUPS,
	PERSONAL_MESSAGES,
	SUBSCRIPTIONS,
	MODERATION_LOGS,
	WIKI_PAGES,
	PROJECTS,
	RESOURCES,
	CMS_COMMENTS,
	BBCODE_REWRITE,
	MIGRATE_SMF_INSTALLATION,
	MIGRATE_CMS_INSTALLATION,
	MIGRATE_EVERYTHING;

	private static final List<JobType> SMF_CONTENT = List.of(
			USERS,
			CATEGORIES,
			BOARDS,
			THREADS,
			MESSAGES,
			IPS,
			MESSAGE_HISTORY,
			USER_BIO_INFO,
			ATTACHMENTS,
			ATTACHMENT_FILES,
			USER_CONTACT_INFO,
			POLLS,
			POLL_CHOICES,
			USER_POLL_CHOICES,
			REACTIONS,
			MEMBER_GROUPS,
			PERSONAL_MESSAGES,
			SUBSCRIPTIONS,
			MODERATION_LOGS);

	private static final List<JobType> CMS_CONTENT = List.of(
			PROJECTS,
			RESOURCES,
			CMS_COMMENTS,
			WIKI_PAGES);

	public static final List<JobType> SMF_INSTALLATION_PIPELINE =
			Stream.concat(SMF_CONTENT.stream(), Stream.of(BBCODE_REWRITE)).toList();

	public static final List<JobType> CMS_INSTALLATION_PIPELINE =
			Stream.concat(CMS_CONTENT.stream(), Stream.of(BBCODE_REWRITE)).toList();

	public List<JobType> expand() {
		return switch (this) {
			case MIGRATE_SMF_INSTALLATION -> SMF_INSTALLATION_PIPELINE;
			case MIGRATE_CMS_INSTALLATION -> CMS_INSTALLATION_PIPELINE;
			case MIGRATE_EVERYTHING -> Stream.concat(SMF_CONTENT.stream(),
					CMS_INSTALLATION_PIPELINE.stream()).toList();
			default -> List.of(this);
		};
	}

	public boolean isPipeline() {
		return !expand().equals(List.of(this));
	}
}
