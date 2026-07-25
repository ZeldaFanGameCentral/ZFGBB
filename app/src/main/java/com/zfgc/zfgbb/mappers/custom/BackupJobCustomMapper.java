package com.zfgc.zfgbb.mappers.custom;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zfgc.zfgbb.model.system.BackupJob;

public interface BackupJobCustomMapper {

	@Insert("""
			insert into zfgbb.backup_job (
				backup_id, state, revision, creator_user_id, created_ts, updated_ts,
				expires_ts
			) values (#{id}::uuid, 'CREATING', 0, #{creatorUserId}, #{now}, #{now}, #{expiresAt})
			""")
	int insertJob(@Param("id") String id, @Param("creatorUserId") Integer creatorUserId,
			@Param("now") OffsetDateTime now, @Param("expiresAt") OffsetDateTime expiresAt);

	@Select("""
			select backup_id, state, revision, created_ts, updated_ts, expires_ts,
			       creator_user_id, archive_bytes, archive_sha256,
			       installer_compatible, installer_anchor_administrator_id, last_error
			  from zfgbb.backup_job
			 where backup_id = #{id}::uuid
			""")
	@ConstructorArgs({
			@Arg(column = "backup_id", javaType = String.class),
			@Arg(column = "state", javaType = BackupJob.State.class),
			@Arg(column = "revision", javaType = long.class),
			@Arg(column = "created_ts", javaType = Instant.class),
			@Arg(column = "updated_ts", javaType = Instant.class),
			@Arg(column = "expires_ts", javaType = Instant.class),
			@Arg(column = "creator_user_id", javaType = Integer.class),
			@Arg(column = "archive_bytes", javaType = Long.class),
			@Arg(column = "archive_sha256", javaType = String.class),
			@Arg(column = "installer_compatible", javaType = Boolean.class),
			@Arg(column = "installer_anchor_administrator_id", javaType = Integer.class),
			@Arg(column = "last_error", javaType = String.class)
	})
	BackupJob findById(@Param("id") String id);

	@Select("""
			select backup_id, state, revision, created_ts, updated_ts, expires_ts,
			       creator_user_id, archive_bytes, archive_sha256,
			       installer_compatible, installer_anchor_administrator_id, last_error
			  from zfgbb.backup_job
			 order by created_ts desc
			""")
	@ConstructorArgs({
			@Arg(column = "backup_id", javaType = String.class),
			@Arg(column = "state", javaType = BackupJob.State.class),
			@Arg(column = "revision", javaType = long.class),
			@Arg(column = "created_ts", javaType = Instant.class),
			@Arg(column = "updated_ts", javaType = Instant.class),
			@Arg(column = "expires_ts", javaType = Instant.class),
			@Arg(column = "creator_user_id", javaType = Integer.class),
			@Arg(column = "archive_bytes", javaType = Long.class),
			@Arg(column = "archive_sha256", javaType = String.class),
			@Arg(column = "installer_compatible", javaType = Boolean.class),
			@Arg(column = "installer_anchor_administrator_id", javaType = Integer.class),
			@Arg(column = "last_error", javaType = String.class)
	})
	List<BackupJob> listJobs();

	@Update({"<script>",
			"update zfgbb.backup_job",
			"   set state=#{next}, last_error=#{error}, revision=revision+1,",
			"       updated_ts=#{now}",
			" where backup_id=#{id}::uuid and revision=#{revision} and state in ",
			"<foreach item='item' collection='expected' open='(' separator=',' close=')'>#{item}</foreach>",
			"</script>"})
	int transitionState(@Param("id") String id, @Param("revision") long revision,
			@Param("expected") Set<String> expected, @Param("next") String next,
			@Param("error") String error, @Param("now") OffsetDateTime now);

	@Update("""
			update zfgbb.backup_job
			   set state='READY', archive_bytes=#{archiveBytes}, archive_sha256=#{archiveSha256},
			       installer_compatible=#{installerCompatible},
			       installer_anchor_administrator_id=#{anchorAdminId}, last_error=null,
			       revision=revision+1, updated_ts=#{now}
			 where backup_id=#{id}::uuid and revision=#{revision} and state='CREATING'
			""")
	int completeJob(@Param("id") String id, @Param("revision") long revision,
			@Param("archiveBytes") long archiveBytes, @Param("archiveSha256") String archiveSha256,
			@Param("installerCompatible") boolean installerCompatible,
			@Param("anchorAdminId") Integer anchorAdminId, @Param("now") OffsetDateTime now);

	@Delete("""
			delete from zfgbb.backup_job
			 where backup_id=#{id}::uuid and revision=#{revision} and updated_ts < #{updatedBefore}
			   and state in ('CONSUMED', 'EXPIRED', 'FAILED')
			""")
	int deleteTerminal(@Param("id") String id, @Param("revision") long revision,
			@Param("updatedBefore") OffsetDateTime updatedBefore);
}
