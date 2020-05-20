package com.gageshan.miaosha.mapper;

import com.gageshan.miaosha.model.Goods;
import com.gageshan.miaosha.model.MiaoshaGoods;
import com.gageshan.miaosha.model.vo.GoodsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create by gageshan on 2020/5/16 18:32
 */
@Mapper
public interface GoodsMapper {
    List<GoodsVO> listGoodsVO();
    List<Goods> listGoods();

    GoodsVO getGoodsVOById(long goodsId);

    int reduceStock(MiaoshaGoods goodsId);
}
