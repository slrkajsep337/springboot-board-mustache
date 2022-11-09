package com.mustache.bbs2.controller;

import com.mustache.bbs2.domain.dto.ArticleDto;
import com.mustache.bbs2.domain.entity.Article;
import com.mustache.bbs2.repository.ArticleRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {


    private final ArticleRepository articleRepository;

    // constructor 생성을 해주면 -> Spring이 ArticleRepository구현체를 DI (인터페이스 아님)
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @GetMapping("/new")
    public String createPage() {
        return "articles/new";
    }


    @PostMapping(value = "/posts")  //insert
    public String createArticle(ArticleDto form) {
        log.info(form.toString());
        Article savedArticle = articleRepository.save(form.toEntity());
        return String.format("redirect:/articles/%d",savedArticle.getId());
//        return String.format("id:%d",savedArticle.getId());
    }

    @GetMapping("/{id}") //한 id에 해당하는 정보 보여주는 페이지
    public String showSingle(@PathVariable Long id, Model model) {
        //jpa에서 optional을 return 한다 ? 그래서 optional로 감싸줘야함
        Optional<Article> optArticle = articleRepository.findById(id);
        if (!optArticle.isEmpty()) {
            model.addAttribute("article",optArticle.get());
            return "articles/show";

        } else {
            return "articles/error";
        }
    }

    @GetMapping("") //아무것도 없을 때에도 list로 가도록
    public String blankToList() {
        return "redirect:/articles/list";
    }


    @GetMapping("/list")
    public String showList(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles",articles);
        return "articles/list";
    }

    @GetMapping("/{id}/edit") //edit페이지로 이동시키는 controller
    public String edit(@PathVariable Long id, Model model) {
        Optional<Article> optArticle = articleRepository.findById(id);
        if (optArticle.isEmpty()) {
            model.addAttribute("article", optArticle.get());
            return "articles/error";
        } else {
            model.addAttribute("article",optArticle.get());
            return "articles/edit";
        }
    }

    @PostMapping("/{id}/update") //실질적으로 수정해주는 controller
    public String update(@PathVariable Long id, ArticleDto articleDto, Model model) {
        log.info("title:{} content:{}", articleDto.getTitle(), articleDto.getContent());
        //save함수는 동일 id일 경우 쿼리를 insert가 아닌 update로 바꾸어서 적용해준다. 그래서 수정이 가능
        Article article = articleRepository.save(articleDto.toEntity());
        model.addAttribute("article", article);
        return String.format("redirect:/articles/%d", article.getId());
    }

    @GetMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        articleRepository.deleteById(id);
        return String.format("redirect:/articles");
    }

}
