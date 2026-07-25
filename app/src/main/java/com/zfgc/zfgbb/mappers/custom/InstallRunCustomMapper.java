package com.zfgc.zfgbb.mappers.custom;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface InstallRunCustomMapper {

	@Insert("""
			insert into zfgbb.install_run (install_id, state, last_completed_state, request_version)
			values (1, 'READY', 'READY', 1)
			on conflict (install_id) do nothing
			""")
	int restoreMissingInstallSingleton();

	@Update("""
			update zfgbb.install_run
			   set request_fingerprint=#{fingerprint}, content_pack=#{pack},
			       provision_recycle_bin=#{recycle}, site_name=#{siteName},
			       install_strategy=#{strategy},
			       state=case when state='FAILED' then last_completed_state else state end,
			       admin_user_id=case when #{supersedesEarlierRequest} then null
			                          else admin_user_id end,
			       last_error=null, updated_ts=current_timestamp
			 where install_id=1
			""")
	int claimInstall(@Param("fingerprint") String fingerprint, @Param("pack") String pack,
			@Param("recycle") boolean recycle, @Param("siteName") String siteName,
			@Param("strategy") String strategy,
			@Param("supersedesEarlierRequest") boolean supersedesEarlierRequest);

	@Insert("""
			insert into zfgbb.install_run (install_id, state, last_completed_state, request_version,
			       request_fingerprint, content_pack, provision_recycle_bin, site_name,
			       install_strategy)
			values (1, 'READY', 'READY', 1, #{fingerprint}, #{pack}, #{recycle}, #{siteName},
			       #{strategy})
			on conflict (install_id) do update
			   set state='READY', last_completed_state='READY', request_version=1,
			       request_fingerprint=#{fingerprint}, content_pack=#{pack},
			       provision_recycle_bin=#{recycle}, site_name=#{siteName},
			       install_strategy=#{strategy},
			       admin_user_id=null, last_error=null, updated_ts=current_timestamp
			""")
	int reestablishInstallAfterArchiveRestore(@Param("fingerprint") String fingerprint,
			@Param("pack") String pack, @Param("recycle") boolean recycle,
			@Param("siteName") String siteName, @Param("strategy") String strategy);

	@Update({"<script>",
			"update zfgbb.install_run",
			"   set state=#{next}, last_completed_state=#{next}, last_error=null,",
			"       updated_ts=current_timestamp",
			" where install_id=1 and state in ",
			"<foreach item='item' collection='expected' open='(' separator=',' close=')'>#{item}</foreach>",
			"</script>"})
	int advanceInstall(@Param("expected") List<String> expected, @Param("next") String next);

	@Update("""
			update zfgbb.install_run
			   set last_completed_state=state, state='FAILED', last_error=#{error},
			       updated_ts=current_timestamp
			 where install_id=1 and state not in ('INSTALLED', 'FAILED')
			""")
	int markInstallFailed(@Param("error") String error);
}
