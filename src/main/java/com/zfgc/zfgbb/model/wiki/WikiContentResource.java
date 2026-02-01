package com.zfgc.zfgbb.model.wiki;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;

public class WikiContentResource extends BaseModel {
    @JsonIgnore
    private Integer resourceId;
    private String title;
    private String content;
    private String contentParsed;
    private String summary;
    private String summaryParsed;
    private String author;
    private String dateCreated;
    private String dateModified;
    private Integer version;

    @Override
    public Integer getId() {
        return resourceId;
    }

    @Override
    public void setId(Integer id) {
        resourceId = id;
    }

}
