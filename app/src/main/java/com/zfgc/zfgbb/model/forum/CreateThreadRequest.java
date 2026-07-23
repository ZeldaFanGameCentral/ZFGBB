package com.zfgc.zfgbb.model.forum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateThreadRequest(
        @NotBlank @Size(min = 1, max = 100) String title, 
        @NotBlank @Size(max = 10000) String body) {}
