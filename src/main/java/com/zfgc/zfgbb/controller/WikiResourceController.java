package com.zfgc.zfgbb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zfgc.zfgbb.services.core.ContentService;

@RestController
@RequestMapping("/resources/{resourceType}")
public class WikiResourceController extends BaseController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/{resourceId}")
    public String getWikiResource(
            @PathVariable("resourceType") String resourceType,
            @PathVariable("resourceId") Integer resourceId) {
        return resourceType;
    }

}
