package com.example.demo;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.LinkedMultiValueMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
public class DemoTest {


    /*
    http://blog.csdn.net/sunxianghuang/article/details/52221913（ConcurrentSkipListMap）
    http://www.educity.cn/java/498061.html
    阅读concurrentskiplistmap

    100个线程，每个线程装10000个数据往map里，每个线程指向完门闩-1，主线程等着，所有线程执行完之后，计算时间

    并发容器
    Map<String, String> map = new Hashtable<>();  1147  //锁定整个容器
    Map<String, String> map = new ConcurrentHashMap<>();//高并发   1039 （多线程情况下效率比Hashtable高一些）
    //容器分别16段，只锁定1段

    Map<String, String> map = new ConcurrentSkipListMap<>(); //高并发并且排序  调表数据结构
    Map<String, String> map = new HashMap<>(); //Collections.synchronizedXXX  带锁的实现
    TreeMap //默认排好顺序
    */
    @Test
    public void test1() {

        Map<String, String> map = new ConcurrentHashMap<>();//高并发
        Random r = new Random();
        Thread[] ths = new Thread[100];
        CountDownLatch latch = new CountDownLatch(ths.length);//门闩  数值为100
        long start = System.currentTimeMillis();
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++)
                    map.put("a" + r.nextInt(100000), "a" + r.nextInt(100000));
                latch.countDown();
            });
        }
        Arrays.asList(ths).forEach(t -> t.start());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    public volatile Boolean boo = false;

    //public static void main(String[] args) {
    //    DemoTest test = new DemoTest();
    //    for (int i = 0; i < 10; i++) {
    //        //创建十个线程，对inc进行自增操作
    //        Thread thread = new Thread(new Runnable() {
    //            @Override
    //            public void run() {
    //                for (int j = 0; j < 10000; j++) {
    //                    /*synchronized (test){
    //                        test.inc++;
    //                    }*/
    //                    //test.inc++;
    //                    if (test.boo){
    //                        System.out.println();
    //                    }
    //                }
    //            }
    //        });
    //        thread.start();
    //    }
    //    while (Thread.activeCount() > 1) {
    //        Thread.yield();//如果存在还没有结束的线程，就需要尽量让出CPU供它们运行
    //    }
    //    //System.out.println(test.inc);
    //}

    @Test
    public void test2() {

        List<String> list1 = Arrays.asList("111", "222", "333", "444", "555");
        List<String> list2 = Arrays.asList("123", "444", "555");
        List<String> collect3 = new ArrayList<>();
        //使用流去两个list的交集
        List<String> collect = list1.stream().filter(str -> list2.stream().anyMatch(str2 -> ObjectUtils.equals(str, str2))).collect(Collectors.toList());
        System.out.println(collect);
        collect3.addAll(list1);
        collect3.addAll(list2);
        //使用流去两个list的差集
        List<String> collect1 = collect3.stream().filter(str -> collect.stream().noneMatch(str2 -> str.equals(str2))).collect(Collectors.toList());
        System.out.println(collect1);
        //使用流去两个list的差集
        List<String> allDifferenceSet2 = list1.stream().filter(item -> !list2.stream().collect(Collectors.toList()).contains(item)).collect(Collectors.toList());
        System.out.println(allDifferenceSet2);

        List<Date> value = new ArrayList<>();
        Date date1 = DateUtil.parse("2021-04-13 23:59:59.999");
        Date date2 = DateUtil.parse("2021-04-13 23:59:10");
        Date date3 = DateUtil.parse("2021-04-11 23:59:10");
        Date date4 = DateUtil.parse("2021-04-01 23:59:10");
        value.add(date1);
        value.add(date2);
        value.add(date3);
        value.add(date4);
        //List<Date> dateCollect4 = value.stream().sorted(Comparator.comparing(date -> date).reversed()).collect(Collectors.toList());

        List<Date> dateCollect3 = value.stream().sorted(Comparator.comparing(date -> date)).collect(Collectors.toList());

        //最早时间
        Date dateCollect = value.stream().min(Comparator.comparing(date -> date)).get();
        //最晚时间
        Date dateCollect2 = value.stream().max(Comparator.comparing(date -> date)).get();

        System.out.println(dateCollect);
        System.out.println(dateCollect2);


    }


    /**
     * 取连续递增最长的list集合
     */
    @Test
    public void test3() {
        List<Integer> listNumber = Arrays.asList(1, 2, 4, 5, 6, 8, 9, 10, 11);
        //Map<Integer, List<Integer>> listMap = new HashMap<>();
        LinkedMultiValueMap<Integer, List<Integer>> map2 = new LinkedMultiValueMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> listT = null;
        for (int i = 0; i < listNumber.size(); i++) {
            if (ObjectUtil.isNull(listT)) {
                listT = new ArrayList<>();
                listT.add(listNumber.get(i));
            } else {
                if (listT.get(listT.size() - 1) + 1 == listNumber.get(i)) {
                    listT.add(listNumber.get(i));
                    if (listNumber.size() - 1 == i) {
                        list.add(listT.size());
                        //listMap.put(listT.size(),listT);
                        map2.add(listT.size(), listT);
                    }
                } else {
                    //listMap.put(listT.size(),listT);
                    map2.add(listT.size(), listT);
                    list.add(listT.size());
                    listT = new ArrayList<>();
                    listT.add(listNumber.get(i));
                }
            }
        }
        Integer integer = list.stream().max(Comparator.comparing(obj -> obj)).get();
        List<List<Integer>> lists = map2.get(integer);
        List<Integer> list1 = lists.get(0);
        System.out.println("参数::" + JSON.toJSONString(map2.get(integer)));

    }


    @Test
    public void test4() {
        ArrayList<BigDecimal> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new BigDecimal("10"));
        }
        BigDecimal reduce = list.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(reduce);
    }

    @Test
    public void test5() throws Exception {
        System.out.println("main函数开始执行");
        Future future = longtime2();
        System.out.println("main函数执行结束");
        System.out.println("异步执行结果：" + future.get());
    }

    @Async
    public Future longtime2() {
        System.out.println("我在执行一项耗时任务");

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("完成");
        return new AsyncResult<>(3);
    }

    @Test
    public void test6() {
        List<String> list = Arrays.asList("node", "java", "c++", "react", "javascript");
        String result = list.stream().reduce((first, second) -> first).orElse("no last element");
        System.out.println(result);
    }

    @Async
    public void longtime() {
        System.out.println("我在执行一项耗时任务");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("完成");

    }


    /***
     * 前提 每天工作时长 8 小时 例子：8点上班 18点下班 午休2小时
     * 计算员工工作时长
     * 实例数据 : 当天 上班时间是 10点 18点下班  上班6小时
     * 如果横跨一周：周一上班 10点 周二到周四默认认为 上班8小时  周五 15点下班
     */
    @Test
    public void workTimeTest() {
        String startTimeStr = "2021-03-01 10:34:23";
        String endTimeStr = "2021-03-25 17:34:23";


        Date startTime = DateUtil.parse(startTimeStr);
        Date endTime = DateUtil.parse(endTimeStr);

        DateTime parse = DateUtil.parse(startTimeStr, DatePattern.NORM_DATE_PATTERN);
        DateTime parse1 = DateUtil.parse(endTimeStr, DatePattern.NORM_DATE_PATTERN);

        String s2s = DateUtil.formatBetween(DateUtil.offsetDay(parse, +1), DateUtil.offsetDay(parse1, -1), BetweenFormatter.Level.SECOND);


        String dayEndTimeStr = DateUtil.formatDate(startTime);
        DateTime dayEndTime = DateUtil.parse(dayEndTimeStr + " 17:59:59", DatePattern.NORM_DATETIME_PATTERN);
        //String s3 = DateUtil.formatBetween(startTime, dayEndTime, BetweenFormatter.Level.SECOND);

        String endDayTimeStr = DateUtil.formatDate(endTime);
        DateTime endDayTime = DateUtil.parse(endDayTimeStr + " 08:00:00", DatePattern.NORM_DATETIME_PATTERN);
        //String s1 = DateUtil.formatBetween(endTime, endDayTime, BetweenFormatter.Level.SECOND);

        String s = workTime(startTime, dayEndTime);
        String s2 = workTime(endTime, endDayTime);
        strIntegrate(s, s2);
        System.out.println("");

    }

    private String strIntegrate(String s, String s2) {
        String second = s.substring(s.indexOf("分") + 1, s.indexOf("秒"));
        String minute = s.substring(s.indexOf("小时") + 2, s.indexOf("分"));
        String hour = s.substring(0, s.indexOf("小时"));

        String second2 = s2.substring(s.indexOf("分") + 1, s.indexOf("秒"));
        String minute2 = s2.substring(s.indexOf("小时") + 2, s.indexOf("分"));
        String hour2 = s2.substring(0, s.indexOf("小时"));


        return null;
    }

    private String strIntegrate2(String s, String s2, BetweenFormatter.Level level) {
        Integer sd = Integer.valueOf(s) + Integer.valueOf(s2);
        int i = sd / 60;
        int i2 = sd % 60;
        return i + "分" + i2 + "秒";
    }

    public String workTime(Date minTime, Date maxTime) {
        if (minTime.before(maxTime)) {
            if (isMorning(minTime)) {
                minTime = DateUtil.offsetHour(minTime, +2);
            }
        } else {
            if (isAfternoon(minTime)) {
                minTime = DateUtil.offsetHour(minTime, -2);
            }
        }
        return DateUtil.formatBetween(minTime, maxTime, BetweenFormatter.Level.SECOND);
    }

    /***
     * 是否是上午
     * @param time
     * @return
     */
    public Boolean isMorning(Date time) {
        String s = DateUtil.formatDate(time);
        DateTime morning = DateUtil.parse(s + " 12:00:00", DatePattern.NORM_DATETIME_PATTERN);
        if (time.before(morning)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    /***
     * 是否是下午
     * @param time
     * @return
     */
    public Boolean isAfternoon(Date time) {
        String s = DateUtil.formatDate(time);
        DateTime afternoon = DateUtil.parse(s + " 18:00:00", DatePattern.NORM_DATETIME_PATTERN);
        if (time.before(afternoon)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Test
    public void listToMap1() {
        List<String> list1 = Arrays.asList("111", "111", "111", "111", "111");

        // list数据整合到一个 string list<string>
        Map<String, List<String>> mapList = list1.stream().collect(Collectors.toMap(a -> a,
                s -> {
                    List<String> ages = new ArrayList<>();
                    ages.add(s);
                    return ages;
                },
                (List<String> v1, List<String> v2) -> {
                    v1.addAll(v2);
                    return v1;
                }));
        System.out.println(mapList);
    }

    @Test
    public void listToMap2() {
        List<String> list1 = Arrays.asList("111", "111", "111", "111", "111");

        // Function.identity()返回一个输出跟输入一样的Lambda表达式对象，等价于形如t -> t形式的Lambda表达式。
        LinkedHashMap<String, String> map = list1.stream().collect(Collectors.toMap(Function.identity(), Function.identity(), (a, b) -> a, LinkedHashMap::new));

        System.out.println(map);
    }

    static volatile int a = 0;

    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int count = 0;
        for (int i = 0; i < 5; i++) {
            if (isInterested(random.nextInt(10))) {
                count++;
            }
        }
        System.out.printf("Found %d interested values%n", count);
    }

    private static boolean isInterested(int i) {
        return i % 2 == 0;
    }

    /**
     * 基本数组使用list.asList会生成一个二维数组 一个元素是基本数据数组
     * 包装类型就可以将数组直接转换成为一个集合
     */
    @Test
    public void test11() {
        int[] asd = new int[]{1, 2, 3};
        String[] strings = {"1", "2"};
        List<int[]> ints = Arrays.asList(asd);
        List<String> strings1 = Arrays.asList(strings);
        System.out.println(ints);
    }


}



