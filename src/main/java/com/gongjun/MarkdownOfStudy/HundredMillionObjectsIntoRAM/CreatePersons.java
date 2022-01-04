package com.gongjun.MarkdownOfStudy.HundredMillionObjectsIntoRAM;

import com.spire.ms.System.Collections.ArrayList;
import lombok.var;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * @Description:批量创建对象
 * @Author:GongJun
 * @Date:2021/12/24
 */
public class CreatePersons {
    //随机的名字
    private static String[] names = {"austin gong","miss lee","mac wang","red beans","java3y","敖丙"};
    //随机的地址
    private static String[] addresses = {"怡心湖街道","华阳街道","公兴街道","万安街道","桂溪街道","东升街道"};
    //随机的公司
    private static String[] companies ={"淘宝","朴朴超市","天猫","京东","家乐福","永辉"};

    private static Random random = new Random();

    //获取时间
    public static LocalDateTime now = LocalDateTime.now();

    public static List<Person> getPersonList(int count){
        var personList = new ArrayList();
        for (int i = 0; i < count; i++) {
            Person person = getPerson();
            personList.add(person);
        }
        return personList;
    }

    private static Person getPerson(){
            var person = Person.builder()
                    .id(random.nextLong())
                    .address("四川省成都市"+addresses[random.nextInt(addresses.length)])
                    .age(random.nextInt(99))
                    .cellPhone(15000000000L+random.nextInt(99999999))
                    .createTime(now)
                    .createUser("austin")
                    .sex(random.nextInt(2))
                    .salary(BigDecimal.valueOf(random.nextInt(9999)))
                    .name(names[random.nextInt(names.length)])
                    .isSingle(random.nextInt(2))
                    .company(companies[random.nextInt(companies.length)])
                    .build();
        return person;
    }


    //获得一个确定的person，需传入一个date，什么作用这里先别管，后面一看就懂
    public static Person getFixedPerson(LocalDateTime date) {
        var person = Person.builder()
                .id(18966666666L)
                .name("austin gong")
                .age(30)
                .cellPhone(18966666666L)
                .salary(new BigDecimal(99999))
                .company("天猫")
                .isSingle(0)
                .sex(0)
                .address("上海市徐家汇某栋写字楼")
                .createTime(date)
                .createUser("austin")
                .build();
        return person;
    }

}
