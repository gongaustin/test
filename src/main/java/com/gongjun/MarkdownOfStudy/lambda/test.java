package com.gongjun.MarkdownOfStudy.lambda;

import lombok.var;

public class test {

    public static void main(String[] args) {

        var aa = new AustinInterface() {
            @Override
            public void austin() {
                System.out.println("Austin");
            }
        };
        aa.austin();
    }


}
