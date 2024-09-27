package com.test.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto implements Serializable {
    private static final long serialVersionUID = 2082503192322391880L;
    @NotNull
    private Long roomId;
    @NotNull
    private Long senderId;

    private String content;

    public void setContent(String content) {
        this.content = content;
    }
}