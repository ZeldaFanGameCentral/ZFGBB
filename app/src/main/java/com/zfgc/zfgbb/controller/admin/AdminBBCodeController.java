package com.zfgc.zfgbb.controller.admin;

import com.zfgc.zfgbb.controller.BaseController;

import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarLoader;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.dataprovider.forum.BBCodeDataProvider;

@Slf4j
@RestController
@RequestMapping("/admin/bbcodes")
@PreAuthorize("hasRole('ROLE_ZFGC_SITE_ADMIN')")
@RequiredArgsConstructor
public class AdminBBCodeController extends BaseController {

	private final BBCodeGrammarLoader grammarLoader;

	@GetMapping
	public ResponseEntity<List<BBCodeDataProvider.BBCodeToggle>> listBBCodes() {
     log.info("Executing listBBCodes");
		return ResponseEntity.ok(grammarLoader.listBBCodes());
	}

	public record BBCodeEnabledRequest(Boolean enabled) {
	}

	public record BBCodeSurfaceRequest(String surface, Boolean honoured) {
	}

	@PutMapping("/{code}/surfaces")
	public ResponseEntity<BBCodeDataProvider.BBCodeToggle> setHonouredOnSurface(
			@PathVariable("code") String code, @RequestBody BBCodeSurfaceRequest request) {
		if (request == null || request.surface() == null || request.honoured() == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "surface and honoured are required");
		ContentScope surface;
		try {
			surface = ContentScope.valueOf(request.surface().toUpperCase(Locale.ROOT));
		} catch (IllegalArgumentException unknown) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unknown surface " + request.surface());
		}
		if (!surface.itsASurfaceContentIsReadOn())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"scope " + request.surface() + " is not a surface content is read on");
		try {
			return ResponseEntity.ok(grammarLoader.setBBCodeHonouredOn(code, surface, request.honoured()));
		} catch (IllegalArgumentException tooStructural) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, tooStructural.getMessage());
		}
	}

	@PutMapping("/{code}")
	public ResponseEntity<BBCodeDataProvider.BBCodeToggle> setEnabled(@PathVariable("code") String code,
			@RequestBody BBCodeEnabledRequest request) {
		if (request == null || request.enabled() == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "enabled is required");
		return ResponseEntity.ok(grammarLoader.setBBCodeEnabled(code, request.enabled()));
	}
}
