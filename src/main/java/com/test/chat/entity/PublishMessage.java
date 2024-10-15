package com.test.chat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PublishMessage implements Serializable {

    /*
    * Redis에서 PublishMessage를 통해서 통신한다. -> Publisher or Subscriver
    * 따라서 Serializable 인터페이스를 상속한다.
    * */

    @Serial
    private static final long serialVersionUID = 2082503192322391880L;

    @NotNull
    private Long roomId;
    @NotNull
    private Long senderId;
    @NotNull
    private String content;

//     LocalDateTime 타입에 대한 직렬화, 역직렬화 도우미 선언
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createAt;
}
