package com.gageshan.miaosha;

import com.gageshan.miaosha.mapper.UserMapper;
import com.gageshan.miaosha.model.Goods;
import com.gageshan.miaosha.model.User;
import com.gageshan.miaosha.model.vo.GoodsVO;
import com.gageshan.miaosha.redis.RedisService;
import com.gageshan.miaosha.redis.UserKey;
import com.gageshan.miaosha.service.GoodsService;
import com.gageshan.miaosha.utils.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MiaoshaApplicationTests {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GoodsService goodsService;

    @Test
    void contextLoads() {
    }

    @Test
    void testSet() {
        redisService.set(UserKey.getById,"1",100);
    }

    @Test
    void testGet() {
        Integer integer = redisService.get(UserKey.getById, "1", int.class);
        System.out.println(integer);
    }

    @Test
    void testIncr() {
        redisService.incr(UserKey.getById,"1");
        Integer integer = redisService.get(UserKey.getById, "1", int.class);
        System.out.println(integer);
    }
    @Test
    void testDecr() {
        redisService.decr(UserKey.getById,"1");
        Integer integer = redisService.get(UserKey.getById, "1", int.class);
        System.out.println(integer);
    }

    @Test
    void testSelectUser() {
        long monile = 12345678901L;
//        User user = userMapper.getById(monile);
//        System.out.println(user.to
//        String());
    }

    @Test
    void testMd5() {
        String inputToDBpass = MD5Util.inputPassToDBPass("123456","1a2b3c4d");
        String formToDBpass = MD5Util.formPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9", "1a2b3c4d");
        String inputToForm = MD5Util.inputPassToFormPass("123456");
        System.out.println(inputToDBpass);
        System.out.println(formToDBpass);
        System.out.println(inputToForm);
    }

    @Test
    void testGoodsService() {
        List<GoodsVO> goodsVOS = goodsService.listGoodsVO();
        for (GoodsVO goodsVO : goodsVOS) {

            System.out.println(goodsVO.toString());
        }
//        System.out.println(user.toString());
    }

    @Test
    void testListGoodsService() {
        List<Goods> goods = goodsService.listGoods();
        for (Goods good : goods) {
            System.out.println(good);
        }
    }

    @Test
    void testgetUserById() {
        long mobile = 17369284502L;
        userMapper.delectUser(mobile);
//        System.out.println(user);
//        int i = userMapper.countUser();
//        System.out.println(i);
    }
}
