package com.zfgc.zfgbb.migrator.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

class LegacyUrlRewriterTest {

	private static LegacyIdMaps maps(
			Map<Integer, Integer> threadMap,
			Map<Integer, Integer> messageMap,
			Map<Integer, Integer> boardMap,
			Map<Integer, Integer> userMap,
			Map<Integer, Integer> attachmentMap) {
		return new LegacyIdMaps(threadMap, messageMap, boardMap, userMap, attachmentMap);
	}

	@Test
	void url_to_thread_basic() {
		assertEquals(
				"see [thread=42]here[/thread] please",
				LegacyUrlRewriter.rewrite(
						"see [url=http://www.zfgc.com/index.php?topic=42.0]here[/url] please",
						maps(Map.of(42, 42), Map.of(), Map.of(), Map.of(), Map.of())));
	}

	@Test
	void url_to_thread_with_msg() {
		assertEquals(
				"[thread=42 msg=128]here[/thread]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php?topic=42.msg128#msg128]here[/url]",
						maps(Map.of(42, 42), Map.of(128, 128), Map.of(), Map.of(), Map.of())));
	}

	@Test
	void url_to_board() {
		assertEquals(
				"[board=15]click[/board]",
				LegacyUrlRewriter.rewrite(
						"[url=http://zfgc.com/forum/index.php?board=15.0]click[/url]",
						maps(Map.of(), Map.of(), Map.of(15, 15), Map.of(), Map.of())));
	}

	@Test
	void url_remaps_thread_id() {
		assertEquals(
				"see [thread=1042]here[/thread] please",
				LegacyUrlRewriter.rewrite(
						"see [url=http://www.zfgc.com/index.php?topic=42.0]here[/url] please",
						maps(Map.of(42, 1042), Map.of(), Map.of(), Map.of(), Map.of())));
	}

	@Test
	void url_remaps_board_id() {
		assertEquals(
				"[board=2015]click[/board]",
				LegacyUrlRewriter.rewrite(
						"[url=http://zfgc.com/forum/index.php?board=15.0]click[/url]",
						maps(Map.of(), Map.of(), Map.of(15, 2015), Map.of(), Map.of())));
	}

	@Test
	void url_to_member_semicolon_form() {
		assertEquals(
				"[member=789]profile[/member]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php?action=profile;u=789]profile[/url]",
						maps(Map.of(), Map.of(), Map.of(), Map.of(789, 789), Map.of())));
	}

	@Test
	void url_to_member_ampersand_form() {
		assertEquals(
				"[member=789]profile[/member]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php?action=profile&u=789]profile[/url]",
						maps(Map.of(), Map.of(), Map.of(), Map.of(789, 789), Map.of())));
	}

	@Test
	void iurl_handled_same_as_url() {
		assertEquals(
				"[thread=42]inline[/thread]",
				LegacyUrlRewriter.rewrite(
						"[iurl=http://www.zfgc.com/index.php?topic=42.0]inline[/iurl]",
						maps(Map.of(42, 42), Map.of(), Map.of(), Map.of(), Map.of())));
	}

	@Test
	void unknown_thread_id_leaves_url_alone() {
		String body = "see [url=http://www.zfgc.com/index.php?topic=42.0]here[/url] please";
		assertEquals(body, LegacyUrlRewriter.rewrite(body, LegacyIdMaps.empty()));
	}

	@Test
	void non_zfgc_urls_left_alone() {
		String body = "[url=https://en.wikipedia.org/wiki/X]wiki[/url]";
		assertEquals(body, LegacyUrlRewriter.rewrite(body, LegacyIdMaps.empty()));
	}

	@Test
	void quote_link_rewritten_to_thread_msg_attrs_drops_author_and_date() {
		assertEquals(
				"[quote thread=42 msg=128]body[/quote]",
				LegacyUrlRewriter.rewrite(
						"[quote author=Foo link=topic=42.msg128#msg128 date=1234567890]body[/quote]",
						maps(Map.of(42, 42), Map.of(128, 128), Map.of(), Map.of(), Map.of())));
	}

	@Test
	void quote_link_remaps_ids() {
		assertEquals(
				"[quote thread=1042 msg=2128]body[/quote]",
				LegacyUrlRewriter.rewrite(
						"[quote author=Foo link=topic=42.msg128#msg128 date=1234567890]body[/quote]",
						maps(Map.of(42, 1042), Map.of(128, 2128), Map.of(), Map.of(), Map.of())));
	}

	@Test
	void quote_without_link_left_alone() {
		String body = "[quote author=Foo date=123]body[/quote]";
		assertEquals(body, LegacyUrlRewriter.rewrite(body, LegacyIdMaps.empty()));
	}

	@Test
	void bare_zfgc_url_to_thread() {
		assertEquals(
				"see [thread=42]http://www.zfgc.com/forum/thread/42/1[/thread] yo",
				LegacyUrlRewriter.rewrite(
						"see http://www.zfgc.com/index.php?topic=42.0 yo",
						maps(Map.of(42, 42), Map.of(), Map.of(), Map.of(), Map.of())));
	}

	@Test
	void label_replaced_when_label_equals_url() {
		assertEquals(
				"[board=2]http://www.zfgc.com/forum/board/2/1[/board]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php?board=2.0]http://www.zfgc.com/index.php?board=2.0[/url]",
						maps(Map.of(), Map.of(), Map.of(2, 2), Map.of(), Map.of())));
	}

	@Test
	void label_replaced_when_label_is_blank() {
		assertEquals(
				"[board=2]http://www.zfgc.com/forum/board/2/1[/board]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php?board=2.0]   [/url]",
						maps(Map.of(), Map.of(), Map.of(2, 2), Map.of(), Map.of())));
	}

	@Test
	void meaningful_label_preserved() {
		assertEquals(
				"[board=2]check this out[/board]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php?board=2.0]check this out[/url]",
						maps(Map.of(), Map.of(), Map.of(2, 2), Map.of(), Map.of())));
	}

	@Test
	void attach_refs_rewritten_via_map() {
		assertEquals(
				"image [attach=99]",
				LegacyUrlRewriter.rewrite(
						"image [attach=1]",
						maps(Map.of(), Map.of(), Map.of(), Map.of(), Map.of(1, 99))));
	}

	@Test
	void unknown_attach_ref_left_alone() {
		assertEquals(
				"image [attach=42]",
				LegacyUrlRewriter.rewrite(
						"image [attach=42]",
						maps(Map.of(), Map.of(), Map.of(), Map.of(), Map.of(1, 99))));
	}

	@Test
	void attach_ref_not_double_mapped_when_already_zfgbb_id() {
		assertEquals(
				"image [attach=99]",
				LegacyUrlRewriter.rewrite(
						"image [attach=99]",
						maps(Map.of(), Map.of(), Map.of(), Map.of(), Map.of(1, 99))));
	}

	@Test
	void multiple_patterns_in_one_body() {
		assertEquals(
				"hi [member=5]foo[/member] check [thread=42 msg=128]this[/thread] [attach=99]",
				LegacyUrlRewriter.rewrite(
						"hi [url=http://www.zfgc.com/index.php?action=profile;u=5]foo[/url] "
								+ "check [url=http://www.zfgc.com/index.php?topic=42.msg128#msg128]this[/url] [attach=1]",
						maps(Map.of(42, 42), Map.of(128, 128), Map.of(), Map.of(5, 5), Map.of(1, 99))));
	}

	@Test
	void url_to_resource() {
		assertEquals(
				"[resource=42]Sword tileset[/resource]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php#?action=resources&sa=view&id=42]Sword tileset[/url]",
						LegacyIdMaps.empty()));
	}

	@Test
	void url_to_game() {
		assertEquals(
				"[game=99]Triforce Saga[/game]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php#?action=games&sa=view&id=99]Triforce Saga[/url]",
						LegacyIdMaps.empty()));
	}

	@Test
	void null_body_returns_null() {
		assertEquals(null, LegacyUrlRewriter.rewrite(null, LegacyIdMaps.empty()));
	}

	@Test
	void legacy_host_url_rewritten_when_configured() {
		LegacyUrlRewriter rewriter = LegacyUrlRewriter.forLegacyHost("localhost:8090");
		assertEquals(
				"see [board=2]click[/board] now",
				rewriter.rewriteBody(
						"see [url=http://localhost:8090/index.php?board=2.0]click[/url] now",
						maps(Map.of(), Map.of(), Map.of(2, 2), Map.of(), Map.of())));
	}

	@Test
	void legacy_host_bare_url_rewritten_when_configured() {
		LegacyUrlRewriter rewriter = LegacyUrlRewriter.forLegacyHost("localhost:8090");
		assertEquals(
				"see [thread=42]http://localhost:8090/forum/thread/42/1[/thread] yo",
				rewriter.rewriteBody(
						"see http://localhost:8090/index.php?topic=42.0 yo",
						maps(Map.of(42, 42), Map.of(), Map.of(), Map.of(), Map.of())));
	}

	@Test
	void legacy_host_blank_falls_back_to_zfgc_only() {
		LegacyUrlRewriter rewriter = LegacyUrlRewriter.forLegacyHost("");
		String body = "[url=http://localhost:8090/index.php?board=2.0]click[/url]";
		assertEquals(body,
				rewriter.rewriteBody(body,
						maps(Map.of(), Map.of(), Map.of(2, 2), Map.of(), Map.of())));
	}

	@Test
	void rewriting_is_idempotent_for_url_inside_already_rewritten_bbcode() {
		LegacyUrlRewriter rewriter = LegacyUrlRewriter.forLegacyHost("localhost:8090");
		String body = "[url=http://localhost:8090/index.php?board=2.0]http://localhost:8090/index.php?board=2.0[/url]";
		LegacyIdMaps idMaps = maps(Map.of(), Map.of(), Map.of(2, 2), Map.of(), Map.of());
		String once = rewriter.rewriteBody(body, idMaps);
		String twice = rewriter.rewriteBody(once, idMaps);
		assertEquals(once, twice);
	}

	@Test
	void zfgc_url_still_rewritten_when_legacy_host_configured() {
		LegacyUrlRewriter rewriter = LegacyUrlRewriter.forLegacyHost("localhost:8090");
		assertEquals(
				"[thread=42]here[/thread]",
				rewriter.rewriteBody(
						"[url=http://www.zfgc.com/index.php?topic=42.0]here[/url]",
						maps(Map.of(42, 42), Map.of(), Map.of(), Map.of(), Map.of())));
	}

	@Test
	void app_base_url_overrides_matched_origin_in_canonical_label() {
		LegacyUrlRewriter rewriter = LegacyUrlRewriter.forLegacyHost("localhost:8090", "http://localhost:5173");
		assertEquals(
				"[board=2]http://localhost:5173/forum/board/2/1[/board]",
				rewriter.rewriteBody(
						"[url=http://localhost:8090/index.php?board=2.0]http://localhost:8090/index.php?board=2.0[/url]",
						maps(Map.of(), Map.of(), Map.of(2, 2), Map.of(), Map.of())));
	}

	@Test
	void app_base_url_trailing_slash_normalized() {
		LegacyUrlRewriter rewriter = LegacyUrlRewriter.forLegacyHost(null, "http://localhost:5173/");
		assertEquals(
				"[thread=42]http://localhost:5173/forum/thread/42/1[/thread]",
				rewriter.rewriteBody(
						"[url=http://www.zfgc.com/index.php?topic=42.0]http://www.zfgc.com/index.php?topic=42.0[/url]",
						maps(Map.of(42, 42), Map.of(), Map.of(), Map.of(), Map.of())));
	}
}
