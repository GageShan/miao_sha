package com.gageshan.miaosha.controller;

import com.gageshan.miaosha.model.OrderInfo;
import com.gageshan.miaosha.model.User;
import com.gageshan.miaosha.model.vo.GoodsVO;
import com.gageshan.miaosha.model.vo.OrderDetailVO;
import com.gageshan.miaosha.result.CodeMsg;
import com.gageshan.miaosha.result.Result;
import com.gageshan.miaosha.service.GoodsService;
import com.gageshan.miaosha.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create by gageshan on 2020/5/19 16:10
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @GetMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVO> detail(User user, @RequestParam("orderId")long orderId) {
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        OrderInfo orderInfo = orderService.getOrderById(orderId);
        if(orderInfo == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        long goodsId = orderInfo.getGoodsId();
        GoodsVO goodsVO = goodsService.getGoodsVOById(goodsId);

        OrderDetailVO vo = new OrderDetailVO();
        vo.setGoods(goodsVO);
        vo.setOrderInfo(orderInfo);
        return Result.success(vo);
    }
}
