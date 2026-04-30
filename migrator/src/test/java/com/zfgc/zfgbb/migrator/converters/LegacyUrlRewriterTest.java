package com.zfgc.zfgbb.migrator.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

class LegacyUrlRewriterTest {

	@Test
	void url_to_thread_basic() {
		assertEquals(
				"see [thread=42]here[/thread] please",
				LegacyUrlRewriter.rewrite(
						"see [url=http://www.zfgc.com/index.php?topic=42.0]here[/url] please",
						Map.of()));
	}

	@Test
	void url_to_thread_with_msg() {
		assertEquals(
				"[thread=42 msg=128]here[/thread]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php?topic=42.msg128#msg128]here[/url]",
						Map.of()));
	}

	@Test
	void url_to_board() {
		assertEquals(
				"[board=15]click[/board]",
				LegacyUrlRewriter.rewrite(
						"[url=http://zfgc.com/forum/index.php?board=15.0]click[/url]",
						Map.of()));
	}

	@Test
	void url_to_member_semicolon_form() {
		assertEquals(
				"[member=789]profile[/member]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php?action=profile;u=789]profile[/url]",
						Map.of()));
	}

	@Test
	void url_to_member_ampersand_form() {
		assertEquals(
				"[member=789]profile[/member]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php?action=profile&u=789]profile[/url]",
						Map.of()));
	}

	@Test
	void iurl_handled_same_as_url() {
		assertEquals(
				"[thread=42]inline[/thread]",
				LegacyUrlRewriter.rewrite(
						"[iurl=http://www.zfgc.com/index.php?topic=42.0]inline[/iurl]",
						Map.of()));
	}

	@Test
	void non_zfgc_urls_left_alone() {
		String body = "[url=https://en.wikipedia.org/wiki/X]wiki[/url]";
		assertEquals(body, LegacyUrlRewriter.rewrite(body, Map.of()));
	}

	@Test
	void quote_link_rewritten_to_thread_msg_attrs() {
		assertEquals(
				"[quote author=Foo thread=42 msg=128 date=1234567890]body[/quote]",
				LegacyUrlRewriter.rewrite(
						"[quote author=Foo link=topic=42.msg128#msg128 date=1234567890]body[/quote]",
						Map.of()));
	}

	@Test
	void quote_without_link_left_alone() {
		String body = "[quote author=Foo date=123]body[/quote]";
		assertEquals(body, LegacyUrlRewriter.rewrite(body, Map.of()));
	}

	@Test
	void bare_zfgc_url_to_thread() {
		assertEquals(
				"see [thread=42]http://www.zfgc.com/index.php?topic=42.0[/thread] yo",
				LegacyUrlRewriter.rewrite(
						"see http://www.zfgc.com/index.php?topic=42.0 yo",
						Map.of()));
	}

	@Test
	void attach_refs_rewritten_via_map() {
		assertEquals(
				"image [attach=99]",
				LegacyUrlRewriter.rewrite(
						"image [attach=1]",
						Map.of(1, 99)));
	}

	@Test
	void unknown_attach_ref_left_alone() {
		assertEquals(
				"image [attach=42]",
				LegacyUrlRewriter.rewrite(
						"image [attach=42]",
						Map.of(1, 99)));
	}

	@Test
	void multiple_patterns_in_one_body() {
		assertEquals(
				"hi [member=5]foo[/member] check [thread=42 msg=128]this[/thread] [attach=99]",
				LegacyUrlRewriter.rewrite(
						"hi [url=http://www.zfgc.com/index.php?action=profile;u=5]foo[/url] "
								+ "check [url=http://www.zfgc.com/index.php?topic=42.msg128#msg128]this[/url] [attach=1]",
						Map.of(1, 99)));
	}

	@Test
	void url_to_resource() {
		assertEquals(
				"[resource=42]Sword tileset[/resource]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php#?action=resources&sa=view&id=42]Sword tileset[/url]",
						Map.of()));
	}

	@Test
	void url_to_game() {
		assertEquals(
				"[game=99]Triforce Saga[/game]",
				LegacyUrlRewriter.rewrite(
						"[url=http://www.zfgc.com/index.php#?action=games&sa=view&id=99]Triforce Saga[/url]",
						Map.of()));
	}

	@Test
	void null_body_returns_null() {
		assertEquals(null, LegacyUrlRewriter.rewrite(null, Map.of()));
	}
}
