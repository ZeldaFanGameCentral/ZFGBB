# Migrator BBCode + URL rewriting

This document tracks the multi-phase work to make legacy SMF message bodies
render correctly under ZFGBB. It spans three repos (backend `ZFGCBB`,
migrator module within it, frontend `ZFGCBB-React`) so each phase is captured
here in one place.

## Why this exists

The legacy ZFGC SMF database has ~14 years of forum content. Two
problems on migration:

1. **URLs in message bodies point at SMF's URL structure** —
   `index.php?topic=N.0`, `index.php?action=profile;u=N`, etc. — which won't
   resolve on the React frontend (`/forum/thread/{id}/{page}`,
   `/user/profile/{id}`).
2. **SMF supports BBCodes that ZFGBB doesn't define yet** — `[me=]`,
   `[iurl=]`, `[hr]`, `[table]`, `[glow=]`, etc. — and would render as raw
   text after migration.

The fix is layered:

- Backend gains new BBCode definitions (semantic resource refs and the
  legacy formatting tags).
- Migrator rewrites URLs in message bodies into the new semantic BBCodes.
- Frontend swaps neutral resource-link markup into router-aware components.

## URL conventions

### Legacy (SMF on `zfgc.com`)

Top patterns seen in the message corpus, in rough frequency order:

| SMF URL | Maps to |
|---|---|
| `index.php?topic=N.0` / `?topic=N` | thread page 0 |
| `index.php?topic=N.msgM#msgM` | thread, scrolled to message M |
| `index.php?board=N.0` / `?board=N` | board page 0 |
| `index.php?action=profile;u=N` (also `&u=N`) | user profile |
| `index.php?action=profile;sa=warninglogs;u=N` | profile sub-page |
| `index.php?action=dlattach;topic=N;attach=M` | attachment download |
| `index.php#?action=resources&sa=view&id=N` | custom ZFGC resources page |
| `index.php#?action=games&sa=view&id=N` | custom ZFGC games page |

Hostnames: `zfgc.com`, `www.zfgc.com`, with or without `/forum/` prefix.
Appearances: bare URLs, inside `[url=...]` / `[iurl=...]`, in the `link=`
attribute of `[quote]`.

### New (ZFGBB React)

Routes already in the frontend (`ZFGCBB-React/src/pages/`):

| ZFGBB URL | Resource |
|---|---|
| `/forum/board/{boardId}/{pageNo}` | board |
| `/forum/thread/{threadId}/{pageNo}` | thread (page-paginated) |
| `/forum/memberList/{currentPage}` | member list |
| `/user/profile/{userId}` | user profile |

**Anchor format for individual messages within a thread:** to be added —
proposed `#msg-{messageId}`. The frontend will need to scroll-to-anchor on
mount. Resolving "which page does message M live on?" is deferred to a
small client-side API call at click time.

## BBCode inventory

### Already defined in ZFGBB

`b`, `u`, `i`, `s`, `pre`, `left`, `center`, `right`, `youtube`, `spoiler`,
`img`, `url`, `email`, `ftp`, `move`, `black`, `blue`, `quote`, `code`,
`color`, `green`, `list`, `li`, `size` (and color shortcuts).

### To be added — semantic resource refs (Phase 1)

Render to neutral HTML with `data-*` attributes; no hardcoded URLs.

| Tag | Frontend renders to |
|---|---|
| `[thread=N]Label[/thread]` | `<a class="bb-resource-link" data-resource="thread" data-thread-id="N">Label</a>` |
| `[thread=N msg=M]Label[/thread]` | `... data-thread-id="N" data-msg-id="M"` |
| `[board=N]Label[/board]` | `... data-resource="board" data-board-id="N"` |
| `[member=N]Label[/member]` | `... data-resource="member" data-user-id="N"` |
| `[quote msg=M author=... date=...]` | extend existing `quote` with optional `msg=` and optional `thread=` attrs |

### To be added — legacy formatting tags (Phase 1)

Bringing every legacy tag forward "for preservation"; render lossy is
acceptable for the styling-only ones.

| Tag | Render | Notes |
|---|---|---|
| `[me=Name]` | `*Name does X*` style — italicized prefix | IRC-style action; ~1500 in corpus |
| `[iurl=...]` | inline-flow `<a>` (variant of `url`) | ~525 in corpus |
| `[hr]` | `<hr/>` | ~1900 in corpus |
| `[table]` `[tr]` `[td]` | direct HTML pass-through | ~3700 combined |
| `[sup]` | `<sup>` | trivial |
| `[glow=color,duration]` | styled span (CSS shadow) | ~750 — keep readable, drop the duration animation |
| `[font=face]` | styled span with `font-family` | ~218 — sanitize face names |
| `[you]` | `<span class="bb-you-placeholder"></span>` | frontend swaps in viewer's name; **not** server-side rendered to avoid leaking identity across cached responses |
| `[request]` | no-op (emit inner content plain) | ~312 orphan opens; appears editorial |

## Phase plan

### Phase 1 — Backend: extend BBCode definitions

Files: `app/src/main/resources/db/migration/functions/R__03_bbcodes.sql`
(seed config), `BBCodeService.java` (parser, may need attribute support).

- New `[thread]`, `[board]`, `[member]` tag configs that render to
  `<a class="bb-resource-link" data-resource="..." data-x-id="...">label</a>`
- Extend `[quote]` config with optional `thread=` and `msg=` attrs;
  preserve existing `author=` and `date=`
- New legacy formatting tags (table above)
- Test: round-trip each new tag through `BBCodeService.parseText()` and
  assert expected HTML

### Phase 2 — Migrator: URL → semantic BBCode rewriter

Files: new `migrator/src/main/java/.../converters/BbcodeRewriter.java` plus
a new pipeline step (or extend `AttachmentsConverter`'s post-pass).

Pattern → rewrite table (greedy, applied per message body):

| SMF pattern (regex-ish) | Rewritten to |
|---|---|
| `[url=...?topic=X(\.0)?]L[/url]` | `[thread=X]L[/thread]` |
| `[url=...?topic=X.msgY#msgY]L[/url]` | `[thread=X msg=Y]L[/thread]` |
| `[url=...?board=B(\.0)?]L[/url]` | `[board=B]L[/board]` |
| `[url=...?action=profile;u=N]L[/url]` | `[member=N]L[/member]` |
| `[url=...?action=dlattach;topic=X;attach=A]L[/url]` | `[attach=A]L[/attach]` (attachment id rewritten by existing pass) |
| `[iurl=...]` (any of the above) | same as `[url=...]` mappings |
| `[quote ... link=topic=X.msgY#msgY ...]B[/quote]` | `[quote thread=X msg=Y ...]B[/quote]` (preserve cross-thread case) |
| bare `http(s)://(www\.)?zfgc\.com/(forum/)?index.php?topic=X.msgY#msgY` | `[thread=X msg=Y]link[/thread]` |
| `[member=N]Name[/member]` (SMF native) | already abstract; pass through |

Hostnames treated as legacy: `zfgc.com`, `www.zfgc.com` — anything else is
left untouched (third-party links).

Idempotency: same `migrator_attachment_ref_rewrites` table pattern, OR
extend it to track all body rewrites with a `kind` column.

### Phase 3 — Frontend: resource-link parser (deferred)

Files: ZFGCBB-React, message rendering pipeline (likely
`UserMessage.tsx` and `html-react-parser` config).

- `replace` function for `html-react-parser`: when seeing
  `<a class="bb-resource-link" data-resource="thread" data-thread-id data-msg-id?>`,
  swap to `<BBLink to="/forum/thread/N/1#msg-M" />` (page resolution TBD).
- `<span class="bb-you-placeholder"/>` swap to current viewer's username.
- Anchor-scroll-to-message on mount when URL has `#msg-N`.
- Page-of-message resolver: small API `GET /thread/{N}/page-of-msg/{M}` →
  `{ pageNo: K }` so links can navigate to the right page; or have
  `[thread=N msg=M]` resolve server-side at link-click time via a
  redirect.

### Phase 4 — Resource / games URLs

Backend + migrator parts done. Frontend resolution still deferred to Phase 3.

- `[resource=N]` and `[game=N]` BBCodes are seeded in
  `R__03_bbcodes.sql` and render to neutral
  `<a class="bb-resource-link" data-resource="resource" data-resource-id="N">`
  (and `data-resource="game" data-game-id="N"` for games).
- `LegacyUrlRewriter` recognizes `?action=resources&sa=view&id=N` and
  `?action=games&sa=view&id=N` (with `;` and `&` separator variants)
  and rewrites to the new BBCodes during migration.
- The React frontend's `html-react-parser` `replace` function (Phase 3)
  needs to handle `data-resource="resource"` and `data-resource="game"`
  values once the corresponding pages exist. Until then the rendered
  `<a>` tag has no working `href` and clicks no-op.

## Open items

- **Page-of-message resolution strategy** — server redirect vs client
  resolver. Not blocking Phase 1 or 2.
- **`[glow=]` animation fidelity** — full keyframe animation or static
  glow shadow. Not blocking.
- **Hostnames** — confirm whether the migrator should also rewrite
  `zfgc.org`, `forum.zfgc.com`, or any other historical hostnames if
  they appear in the data.
- **`[me=]` rendering style** — italic prefix vs styled block. Bikeshed.
- **Cross-thread quote permalink** — `[quote thread=X msg=Y]`'s rendered
  output should be a clickable jump-to-quote button. UX detail for the
  frontend.
