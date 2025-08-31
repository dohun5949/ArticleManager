package org.example.ArticleManager;

import org.example.controller.ArticleController;
import org.example.controller.MemberController;
import org.example.controller.Controller;

import java.util.Scanner;

public class App {

    public void run() {

        Scanner sc = new Scanner(System.in);
        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);
        Controller controller = null;

        articleController.makeArticleTestData();
        memberController.makeMemberTestData();

        System.out.println("== 프로그램 시작 ==");

        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();
            if (cmd.equals("exit")) {
                break;
            }
            String[] str = cmd.split(" ");
            String controllerName = str[0];
            if (str.length == 1) {
                System.out.println("명령어를 입력하세요");
                continue;
            }
            String methodName = str[1];

            if (controllerName.equals("article")) {
                controller = articleController;
            } else if (controllerName.equals("member")) {
                controller = memberController;
            }
            if (cmd.length() == 0) {
                System.out.println("명령어를 입력하세요");
                continue;
            }
            controller.doAction(cmd, methodName);
        }
//
//            if (cmd.equals("member list")) {
//                memberController.showList();
//            } else if (cmd.equals("member join")) {
//                memberController.doJoin();
//            } else if (cmd.equals("article write")) {
//                articleController.doWrite();
//            } else if (cmd.startsWith("article list")) {
//                articleController.showList(cmd);
//            } else if (cmd.startsWith("article detail")) {
//                articleController.showDetail(cmd);
//            } else if (cmd.startsWith("article delete")) {
//                articleController.doDelete(cmd);
//            } else if (cmd.startsWith("article modify")) {
//                articleController.doModify(cmd);
//            } else {
//                System.out.println("사용할 수 없는 명령어입니다.");
//            }

        System.out.println("프로그램 종료");
        sc.close();
    }


}