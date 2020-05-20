CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `delivery_addr_id` bigint(20) DEFAULT NULL COMMENT '收获地址ID',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `order_channel` tinyint(4) DEFAULT '0' COMMENT '1pc，2android，3ios',
  `status` tinyint(4) DEFAULT '0' COMMENT '订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成',
  `create_date` datetime DEFAULT NULL COMMENT '订单的创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1565 DEFAULT CHARSET=utf8mb4;
INSERT INTO `order_info` VALUES (1561,18912341234,1,NULL,'iphoneX',1,0.01,1,0,'2017-12-14 22:49:10',NULL),(1562,18912341234,2,NULL,'华为Meta9',1,0.01,1,0,'2017-12-14 22:55:42',NULL),(1563,18912341234,4,NULL,'小米6',1,0.01,1,0,'2017-12-16 16:19:23',NULL),(1564,18912341234,3,NULL,'iphone8',1,0.01,1,0,'2017-12-16 16:35:20',NULL);
