package com.gageshan.miaosha.controller;

import com.gageshan.miaosha.model.User;
import com.gageshan.miaosha.model.vo.GoodsDetailVO;
import com.gageshan.miaosha.model.vo.GoodsVO;
import com.gageshan.miaosha.redis.GoodsKey;
import com.gageshan.miaosha.redis.RedisService;
import com.gageshan.miaosha.result.Result;
import com.gageshan.miaosha.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Create by gageshan on 2020/5/15 18:51
 */
@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RedisService redisService;
    /**
     * 942qps/s
     *1306qps/s
     * @param request
     * @param response
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/to_list")
    @ResponseBody
    public String list(HttpServletRequest request,
                       HttpServletResponse response,
                       Model model,
                       User user) {
        model.addAttribute("user",user);
        String html = redisService.get(GoodsKey.getGoodsList,"",String.class);
        if(!StringUtils.isBlank(html)) {
            return html;
        }

        List<GoodsVO> goodsVOS = goodsService.listGoodsVO();
        model.addAttribute("goodsList",goodsVOS);
        IWebContext ctx = new WebContext(request,response,
                request.getServletContext(),
                request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
        if(!StringUtils.isBlank(html)) {
//            log.info("html: " + GoodsKey.getGoodsList.getPrefix() + html);
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        return html;
    }

    /**
     * 606qps
     * 1178qps
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @GetMapping("/to_detail2/{goodsId}")
    @ResponseBody
    public String detail2(HttpServletRequest request,
                         HttpServletResponse response,
                         Model model, User user,
                         @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user",user);

        String html = redisService.get(GoodsKey.getGoodsDetail,"" + goodsId,String.class);
        if(!StringUtils.isBlank(html)) {
            return html;
        }
        GoodsVO goods = goodsService.getGoodsVOById(goodsId);
        model.addAttribute("goods",goods);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds;

        if(now < startTime) {
            remainSeconds = (int)(startTime - now)/1000;
        } else if(now > endTime) {
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);


        IWebContext ctx = new WebContext(request,response,
                request.getServletContext(),
                request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",ctx);
        if(!StringUtils.isBlank(html)) {
            redisService.set(GoodsKey.getGoodsDetail,"" + goodsId,html);
        }
        return html;
    }


    @GetMapping("/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVO> detail(User user, @PathVariable("goodsId") long goodsId) {
        GoodsVO goods = goodsService.getGoodsVOById(goodsId);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds;

        if(now < startTime) {
            remainSeconds = (int)(startTime - now)/1000;
        } else if(now > endTime) {
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        goodsDetailVO.setGoods(goods);
        goodsDetailVO.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVO.setRemainSeconds(remainSeconds);
        goodsDetailVO.setUser(user);
        return Result.success(goodsDetailVO);
    }
}
