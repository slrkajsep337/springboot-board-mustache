package com.mustache.bbs2.domain.dto;

import com.mustache.bbs2.domain.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
//@Setter
//@ToString
public class ArticleDto {
    //데이터 전송만을 위해서 사용, entity(Article)과는 구분해서 사용하는 것이 좋다.
    private Long id;
    private String title;
    private String content;

//    public ArticleDto(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }

    public Article toEntity() {
        return new Article(this.id, this.title, this.content);
//        return new Article(this.title, this.content);

    }

}
