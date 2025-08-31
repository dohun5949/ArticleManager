package org.example.controller;

import com.sun.security.jgss.GSSUtil;
import org.example.Util.Util;
import org.example.dto.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {

    private List<Member> memberList = new ArrayList<>();
    private int lastMemberId = 3;
    private Scanner sc;
    private String cmd;
    private Member loginedMember;

    public MemberController(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public void doAction(String cmd ,String methodName) {
        this.cmd = cmd;
        switch (methodName){
            case "list":
                showList();
                break;
            case "join":
                doJoin();
                break;
            case "login":
                doLogin();
                break;
            case "logout":
                doLogout();
                break;
        }
    }
    private boolean isLogin() {
        return loginedMember != null;
    }

    private void doLogout() {
        if(isLogin()){
            System.out.println("로그 아웃 상태입니다.");
        }
        loginedMember = null;
        System.out.println("로그아웃되었습니다.");
    }

    private void doLogin() {
        if(!isLogin()){
            System.out.println("이미 로그인 상태입니다.");
            return;
        }
        System.out.print("아이디 : ");
        String loginId = sc.nextLine().trim();
        System.out.print("비밀번호 : ");
        String loginPw = sc.nextLine().trim();
        Member member = getLogin(loginId);
        if(member == null){
            System.out.println("일치하는 회원이 없습니다.");
            return;
        }
        if(!member.getLoginPw().equals(loginPw)){
            System.out.println("비밀번호가 일치하지 않습니다.");
            return;
        }
        loginedMember = member;
        System.out.println(loginedMember.getName() +"님 로그인 성공");

    }

    private Member getLogin(String loginId) {
        for(Member m : memberList){
            if(m.getLoginId().equals(loginId)){
                return m;
            }
        }
        return null;
    }

    public void showList() {
        for (Member member : memberList) {
            System.out.println(member.toString());
        }
    }

    public void doJoin() {
        System.out.println("==회원가입==");
        int id = lastMemberId + 1;

        String loginId = null;
        while (true) {
            System.out.print("아이디 : ");
            loginId = sc.nextLine().trim();

            if (!isJoinableLoginId(loginId)) {
                System.out.println(loginId + "는 사용할 수 없는 아이디입니다.");
                continue;
            }
            break;
        }

        String loginPw = null;
        while (true) {
            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine().trim();
            System.out.print("비밀번호 : ");
            String loginPwCheck = sc.nextLine().trim();

            if (!loginPw.equals(loginPwCheck)) {
                System.out.println("비밀번호를 확인하세요.");
                continue;
            }
            break;
        }
        System.out.print("이름 : ");
        String name = sc.nextLine().trim();
        String regDate = Util.getNow();
        String updateDate = Util.getNow();

        Member member = new Member(id, loginId, loginPw, name, regDate, updateDate);

        memberList.add(member);

        System.out.println(id + "번 회원이 등록되었습니다.(개발자용)");
        System.out.println(name + "회원님 등록을 환영합니다.");

        lastMemberId++;
    }
    private boolean isJoinableLoginId(String loginId) {
        for (Member member : memberList) {
            if (member.getLoginId().equals(loginId)) {
                return false;
            }
        }
        return true;
    }

    public void makeMemberTestData() {
        System.out.println("회원 테스트 데이터 생성됨");
        memberList.add(new Member(1, "test1", "test1", "testName1", "2025-7-5", Util.getNow()));
        memberList.add(new Member(2, "test2", "test2", "testName2", Util.getNow(), Util.getNow()));
        memberList.add(new Member(3, "test3", "test3", "testName3", Util.getNow(), Util.getNow()));
    }

}