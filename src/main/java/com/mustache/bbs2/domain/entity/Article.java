package com.mustache.bbs2.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="article", schema="prac")
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //db에서 primarykey로 생성 됨

    //add만 할 때에는 id가 필요없었지만, edit을 할 때에 id가 필요하여 추가
    private Long id;
    private String title;
    private String content;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}



