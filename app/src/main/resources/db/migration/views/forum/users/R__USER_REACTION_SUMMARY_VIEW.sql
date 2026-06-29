create or replace view zfgbb.user_reaction_summary_view as
select recv.user_id,
       coalesce(sum(rt.points), 0) as reputation_points,
       count(*) filter (where rt.points > 0) as positive_count,
       count(*) filter (where rt.points < 0) as negative_count,
       count(*) as reaction_count
from (
	select r.reaction_type_id, m.owner_id as user_id
	from zfgbb.reaction r
	join zfgbb.message m on r.reactable_type = 'MESSAGE' and m.message_id = r.reactable_id
	union all
	select r.reaction_type_id, e.created_user_id as user_id
	from zfgbb.reaction r
	join zfgbb.content_entity e on r.reactable_type = e.entity_type and e.content_entity_id = r.reactable_id
	union all
	select r.reaction_type_id, w.created_user_id as user_id
	from zfgbb.reaction r
	join zfgbb.wiki_page w on r.reactable_type = 'WIKI_PAGE' and w.wiki_page_id = r.reactable_id
) recv
join zfgbb.reaction_type rt on rt.reaction_type_id = recv.reaction_type_id
where recv.user_id is not null
group by recv.user_id;
