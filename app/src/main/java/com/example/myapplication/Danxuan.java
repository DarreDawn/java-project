package com.example.myapplication;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;


@Table(name = "Danxuan")
public class Danxuan implements Serializable {
    @Column(name = "id", isId = true, autoGen = true)
    private String id;
    @Column(name = "question")//
    private String question;
    @Column(name = "a")//
    private String a;
    @Column(name = "b")//
    private String b;
    @Column(name = "c")//
    private String c;
    @Column(name = "d")//
    private String d;
    @Column(name = "answer")//
    private String answer;

    public Danxuan() {

    }

    public Danxuan(String question, String a, String b, String c, String d, String answer) {
        this.question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
