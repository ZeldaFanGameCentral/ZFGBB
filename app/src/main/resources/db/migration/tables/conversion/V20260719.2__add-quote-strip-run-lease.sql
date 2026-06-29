alter table zfgbb.quote_strip_run
	add column if not exists lease_owner uuid,
	add column if not exists lease_expires_ts timestamptz,
	add column if not exists heartbeat_ts timestamptz,
	add column if not exists attempt_no integer not null default 0;

alter table zfgbb.quote_strip_run
	drop constraint if exists ck_quote_strip_run_lease;

update zfgbb.quote_strip_run
set status = case status
		when 'APPLYING' then 'APPLY_PARTIAL'
		when 'REVERTING' then 'REVERT_PARTIAL'
		else 'FAILED'
	end,
	updated_ts = current_timestamp
where status in ('PLANNING', 'APPLYING', 'REVERTING') and lease_owner is null;

alter table zfgbb.quote_strip_run
	add constraint ck_quote_strip_run_lease check (
		(status in ('PLANNING', 'APPLYING', 'REVERTING')
			and lease_owner is not null and lease_expires_ts is not null and heartbeat_ts is not null)
		or
		(status not in ('PLANNING', 'APPLYING', 'REVERTING')
			and lease_owner is null and lease_expires_ts is null)
	);
