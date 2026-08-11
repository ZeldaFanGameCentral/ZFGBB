create or replace view zfgbb.wiki_revision_ref as
select wiki_page_revision_id,
       wiki_page_id,
       authored_ts,
       created_ts,
       author_name,
       summary,
       current_flag,
       content_size,
       status
from zfgbb.wiki_page_revision
