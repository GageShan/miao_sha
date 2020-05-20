package com.gageshan.miaosha.service;

import com.gageshan.miaosha.mapper.GoodsMapper;
import com.gageshan.miaosha.model.Goods;
import com.gageshan.miaosha.model.MiaoshaGoods;
import com.gageshan.miaosha.model.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by gageshan on 2020/5/16 18:31
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public List<GoodsVO> listGoodsVO() {
        return goodsMapper.listGoodsVO();
    }

    public List<Goods> listGoods() {
        return goodsMapper.listGoods();
    }

    public GoodsVO getGoodsVOById(long goodsId) {

        return goodsMapper.getGoodsVOById(goodsId);
    }

//    @Transactional
    public boolean reduceStock(GoodsVO goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        int ret = goodsMapper.reduceStock(g);
        return ret > 0;
    }
}
