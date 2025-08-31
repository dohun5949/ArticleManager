package org.example.controller;

import org.example.Util.Util;
import org.example.dto.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {
    private Scanner sc;
    private int lastId = 3;
    private List<Article> articleList = new ArrayList<>();
    private String cmd;

    public ArticleController(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public void doAction(String cmd ,String methodName) {
        this.cmd = cmd;
        switch (methodName){
            case "write":
                doWrite();
                break;
            case "list":
                showList();
                break;
            case "detail":
                showDetail();
                break;
            case "delete":
                doDelete();
                break;
            case "modify":
                doModify();
                break;
        }
    }

    public void doWrite() {
        System.out.println("==게시글 작성==");
        int id = lastId + 1;
        System.out.print("제목 : ");
        String title = sc.nextLine().trim();
        System.out.print("내용 : ");
        String body = sc.nextLine().trim();
        String regDate = Util.getNow();
        String updateDate = Util.getNow();

        articleList.add(new Article(id, title, body, regDate, updateDate));
        System.out.println(id + "번 게시글이 작성되었습니다.");
        lastId++;
    }

    public void showList() {
        System.out.println("==게시글 목록==");
        System.out.println("번호   /   제목   /   내용   /   등록일자");
        if (articleList.size() == 0) {
            System.out.println("게시글 없습니다.");
            return;
        }

        String searchKeyword = cmd.substring("article list".length()).trim();

        List<Article> forPrintArticles = articleList;
        if (searchKeyword.length() > 0) {
            forPrintArticles = new ArrayList<>();
            System.out.println("검색어 : " + searchKeyword);
            for (Article article : articleList) {
                if (article.getTitle().contains(searchKeyword)) {
                    forPrintArticles.add(article);
                }
            }
            if (forPrintArticles.size() == 0) {
                System.out.println("검색결과 없음");
                return;
            }
        }

        for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
            System.out.printf("%d   /   %s    /   %s   /   %s\n", forPrintArticles.get(i).getId(), forPrintArticles.get(i).getTitle(), forPrintArticles.get(i).getBody(), forPrintArticles.get(i).getRegDate());
        }
    }

    public void showDetail() {
        System.out.println("==게시글 상세보기==");
        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);
        if (foundArticle == null) {
            System.out.println(id + "번 게시글은 없습니다.");
        }

        System.out.println("번호 : " + foundArticle.getId());
        System.out.println("등록일 : " + foundArticle.getRegDate());
        System.out.println("수정일 : " + foundArticle.getUpdateDate());
        System.out.println("제목 : " + foundArticle.getTitle());
        System.out.println("내용 : " + foundArticle.getBody());
    }

    public void doDelete() {
        System.out.println("==게시글 삭제==");
        int id;
        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (NumberFormatException e) {
            return;
        }
        Article foundArticle = getArticleById(id);
        if (foundArticle == null) {
            System.out.println(id + "번 게시글은 없습니다.");
            return;
        }

        articleList.remove(foundArticle);
        System.out.println(id + "번 게시글이 삭제되었습니다.");
    }

    public void doModify() {
        System.out.println("==게시글 수정==");
        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);
        if (foundArticle == null) {
            System.out.println(id + "번 게시글은 없습니다.");
        }

        System.out.println("기존 제목 : " + foundArticle.getTitle());
        System.out.println("기존 내용 : " + foundArticle.getBody());
        System.out.print("새 제목 : ");
        String newTitle = sc.nextLine().trim();
        System.out.print("새 내용 : ");
        String newBody = sc.nextLine().trim();

        foundArticle.setTitle(newTitle);
        foundArticle.setBody(newBody);
        foundArticle.setUpdateDate(Util.getNow());
        System.out.println(id + "번 게시글이 수정되었습니다.");
    }

    private Article getArticleById(int id) {
        for (Article article : articleList) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;

    }
    public void makeArticleTestData() {
        System.out.println("게시글 테스트 데이터 생성됨");
        articleList.add(new Article(1, "제목1", "내용1", "2025-8-20 12:12:12", "2025-9-1 12:30:30"));
        articleList.add(new Article(2, "제목2", "내용2", "2025-8-26 12:23:12", Util.getNow()));
        articleList.add(new Article(3, "제목3", "내용3", "2025-8-27 12:12:44", Util.getNow()));
    }

}