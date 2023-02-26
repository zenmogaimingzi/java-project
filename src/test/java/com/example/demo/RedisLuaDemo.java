package com.example.demo;

import com.example.demo.constant.StringConstant;
import com.example.demo.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
public class RedisLuaDemo {


    @Test
    public void testUpdateJson() throws Exception {

        InputStream luaInputStream = RedisLuaDemo.class
                .getClassLoader()
                .getResourceAsStream("lua/updateJson.lua");

        String luaScript = new BufferedReader(new InputStreamReader(luaInputStream))
                .lines().collect(Collectors.joining("\n"));

        Jedis jedis = RedisUtils.getJedis();


        String user = "users:id:992452";

        jedis.set(user, "{\"name\": \"Tina\", \"sex\": \"female\", \"grade\": \"A\"}");

        String luaSHA = jedis.scriptLoad(luaScript);


        List<String> keys = Collections.singletonList(user);
        List<String> args = Collections.singletonList("{\"grade\": \"C\"}");

        System.out.println("脚本id:" + luaSHA);

        Object r = jedis.evalsha(luaSHA, keys, args);
        // 输出结果
        System.out.println("Result: " + r + "时间：" + System.currentTimeMillis());

        // 关闭 Redis 连接
        RedisUtils.closeJedis(jedis);
    }


    @Test
    public void testTimeWindow() throws Exception {
        // Load Lua script
        InputStream luaInputStream = RedisLuaDemo.class.getClassLoader().getResourceAsStream("lua/timeWindow.lua");
        String luaScript = new BufferedReader(new InputStreamReader(luaInputStream)).lines().collect(Collectors.joining("\n"));

        Jedis jedis = RedisUtils.getJedis();
        String luaSHA = jedis.scriptLoad(luaScript);

        //自定义前缀
        String key = "mycounter";
        // 限制次数
        int limit = 2;
        // 时间窗口时间 秒
        int windowSize = 1;

        for (int i = 0; i < 10; i++) {
            List<String> keys = Collections.singletonList(key);
            String[] args = {String.valueOf(windowSize), String.valueOf(limit)};

            Object result = jedis.evalsha(luaSHA, keys, Arrays.asList(args));
            Boolean flag = !StringConstant.ONE.equals(result.toString());
            System.out.println("Result: " + flag + ", Time: " + System.currentTimeMillis());
            Thread.sleep(100);
        }

        // Close Redis connection
        RedisUtils.closeJedis(jedis);
    }
}
