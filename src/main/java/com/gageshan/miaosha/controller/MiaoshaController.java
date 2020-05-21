package com.gageshan.miaosha.controller;

import com.gageshan.miaosha.access.AccessLimit;
import com.gageshan.miaosha.model.MiaoshaOrder;
import com.gageshan.miaosha.model.User;
import com.gageshan.miaosha.model.vo.GoodsVO;
import com.gageshan.miaosha.rabbitmq.MQSender;
import com.gageshan.miaosha.rabbitmq.MiaoshaMessage;
import com.gageshan.miaosha.redis.*;
import com.gageshan.miaosha.result.CodeMsg;
import com.gageshan.miaosha.result.Result;
import com.gageshan.miaosha.service.GoodsService;
import com.gageshan.miaosha.service.MiaoshaService;
import com.gageshan.miaosha.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by gageshan on 2020/5/17 16:57
 */
@Controller
@Slf4j
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;


    //内存标记，减少redis访问量
    private Map<Long, Boolean> map = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> goodsVOList = goodsService.listGoodsVO();
        for (GoodsVO goodsVO : goodsVOList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goodsVO.getId(), goodsVO.getStockCount());
            map.put(goodsVO.getId(),false);
        }
    }

    @PostMapping("/{path}/do_miaosha")
    @ResponseBody
    public Result<Integer> miaosha(Model model,
                                   User user,
                                   @RequestParam("goodsId") long goodsId,
                                    @PathVariable("path") String path) {
        model.addAttribute("user",user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        boolean check = miaoshaService.checkPath(user,goodsId,path);
//        log.info("do_miaosha====" + check + "");
        if(!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        Boolean isOver = map.get(goodsId);
        if(isOver) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if(stock < 0) {
            map.put(goodsId,true);
            log.info(goodsId + " is over");
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        MiaoshaOrder miaoshaOrder = orderService.getOrderByGoodsIdUserId(user.getId(),goodsId);
        if(miaoshaOrder != null) {
            log.info(user.getId() + " repeate miaosha" + " " + goodsId);
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //入rabbitmq
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setUser(user);
        miaoshaMessage.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessage(miaoshaMessage);

        return Result.success(0);
    }


    /**
     *
     * @param model
     * @param user
     * @param goodsId
     * @return  orderId： 成功
     *          -1： 秒杀失败
     *           0： 排队中
     */
    @GetMapping("/result")
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, User user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        long result = miaoshaService.getMiaoshaResult(user.getId(),goodsId);
        return Result.success(result);
    }

    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @GetMapping("/path")
    @ResponseBody
    public Result<String> getMiaoshaPath(HttpServletRequest request,
                                         User user,
                                         @RequestParam("goodsId") long goodsId,
                                         @RequestParam(value = "verifyCode")int verifyCode) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }


        boolean checkVerifyCode = miaoshaService.checkVerifyCode(user, goodsId, verifyCode);
//        log.info("path====" + checkVerifyCode + "");
        if(!checkVerifyCode) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        String md5 = miaoshaService.createPath(user,goodsId);
        return Result.success(md5);
    }

    @GetMapping("/verifyCode")
    @ResponseBody
    public Result<String> getMiaoshaVerifyCode(HttpServletResponse response, User user, @RequestParam("goodsId") long goodsId) {

        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        BufferedImage image = miaoshaService.createVerifyCode(user,goodsId);

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"JPEG",outputStream);
            outputStream.flush();
            outputStream.close();
            return null;
        } catch (Exception e) {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
