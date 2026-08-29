package com.zfgc.zfgbb.content.renderer.bbcode;

import com.zfgc.zfgbb.model.forum.BBCodeConfig;

public record AuthoredOpener(BBCodeConfig config, String attributeText, BBCodeConfig.ParsedAttributes attributes) {}
