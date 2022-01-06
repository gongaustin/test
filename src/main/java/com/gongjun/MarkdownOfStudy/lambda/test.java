package com.gongjun.MarkdownOfStudy.lambda;

import lombok.var;

public class test {
    /*
     *@Author:AustinGong
     *@Descrition://TODO
     *@Date:10:51 AM 2022/1/6
     *@Param:[args]
     *@return:void
     **/
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
