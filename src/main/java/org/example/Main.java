package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Article> articleList = new ArrayList<>();
    public static void main(String[] args) {

//        LocalDateTime now = LocalDateTime.now();
//        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        System.out.println(date);
        makeTestData();
        Scanner sc = new Scanner(System.in);

        System.out.println("== 프로그램 시작 ==");

        int lastId = 3;


        while (true) {
            LocalDateTime now = LocalDateTime.now();

            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();

            if (cmd.length() == 0) {
                System.out.println("명령어를 입력하세요");
                continue;
            }
            if (cmd.equals("exit")) {
                System.out.println("게시글 종료");
                break;
            }
            if (cmd.equals("article write")) {
                System.out.print("제목: ");
                String title = sc.nextLine();
                System.out.print("내용: ");
                String main = sc.nextLine();
                lastId++;
                String regDate = Util.getNow();
                String updateDate = Util.getNow();
                Article article = new Article(lastId, title, main, regDate, updateDate);
                articleList.add(article);
                System.out.println(lastId + "번 글이 생성되었습니다.");

            } else if (cmd.equals("article list")) {
                System.out.println("번호 / 제목 / 내용  / 등록일자");
                if(articleList.size() == 0){
                    System.out.println("게시글이 없습니다.");
                    continue;
                }
                for (int i = articleList.size() - 1; i >= 0; --i) {
                    Article article = articleList.get(i);
                    if (article.getBody().length() < 10) {
                        System.out.println(article.getId() + " / " + article.getTitle() + " / " + article.getBody() + " / "+ article.getRegDate());
                    } else {
                        System.out.println(article.getId() + " / " + article.getTitle() + " / " + article.getBody().substring(0, 10) + " / " + article.getRegDate());
                    }
                }


            } else if (cmd.startsWith("article detail")) {
                String[] str = cmd.split(" ");
                int num = Integer.parseInt(str[2]);

                Article a = null;
                for(Article article : articleList){
                    if(article.getId() == num){
                        a = article;
                        break;
                    }
                }
                if(a == null){
                    System.out.println("해당 게시글은 없습니다.");
                    continue;
                }
                System.out.println("등록일 : " + a.getRegDate());
                System.out.println("수정일 : " + a.getUpdateDate());
                System.out.println("번호 : " + a.getId());
                System.out.println("제목 : " + a.getTitle());
                System.out.println("내용 : " + a.getBody());

            } else if (cmd.startsWith("article delete")) {
                String[] str = cmd.split(" ");
                if (str.length > 3) {
                    System.out.println("delete 뒤에는 숫자만 가능합니다.");
                    return;
                }
                int num;
                try {
                    num = Integer.parseInt(str[2]);
                } catch (NumberFormatException e) {
                    System.out.println("delete 뒤에는 숫자만 입력 가능합니다.");
                    return;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("delete 이후에 숫자를 입력하시오");
                    return;
                }
                int index = -1;
                for (int i = 0; i < articleList.size(); i++) {
                    if (articleList.get(i).getId() == num) {
                        index = i;
                    }
                }
                if (index != -1) {
                    articleList.remove(index);
                    System.out.println(num + "번 글이 삭제 되었습니다.");
                } else if (!articleList.contains(index)) {
                    System.out.println(num + "번 게시글은 없습니다.");
                }


            } else if (cmd.startsWith("article modify")) {
                String[] str = cmd.split(" ");
                if (str.length > 3) {
                    System.out.println("modify 뒤에는 숫자만 가능합니다.");
                    return;
                }
                int num;
                try {
                    num = Integer.parseInt(str[2]);
                } catch (NumberFormatException e) {
                    System.out.println("delete 뒤에는 숫자만 입력 가능합니다.");
                    return;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("modify 이후에 숫자를 입력하시오");
                    return;
                }
                int index = -1;

                Article a = null;
                for (int i = 0; i < articleList.size(); i++) {
                    if (articleList.get(i).getId() == num) {
                        a = articleList.get(i);
                        break;
                    }
                }
                if (a == null) {
                    System.out.println(num + "번 게시글은 없습니다.");
                    continue;
                }
                System.out.println("기존 제목 : " + a.getTitle());
                System.out.println("기존 내용 : " + a.getBody());

                System.out.print("제목 : ");
                String newTitle = sc.nextLine();
                System.out.print("내용 : ");
                String newBody = sc.nextLine();

                a.setBody(newBody);
                a.setTitle(newTitle);
                a.setRegDate(Util.getNow());
                System.out.println(a.getId() + "번 게시글이 수정되었습니다.");


            } else {
                System.out.println("사용할 수 없는 명령어입니다.");
            }
        }
        System.out.println("프로그램 종료");
        sc.close();

    }
    private static void makeTestData(){
        System.out.println("테스트 데이터 생성됨");
        articleList.add(new Article(1, "제목1" , "내용1", "2025-08-31", Util.getNow()));
        articleList.add(new Article(2, "제목2" , "내용2", "2025-08-31", Util.getNow()));
        articleList.add(new Article(3, "제목3" , "내용3", "2025-08-31", Util.getNow()));
    }
}

