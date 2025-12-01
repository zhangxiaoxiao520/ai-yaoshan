# 数据库结构报告

## 1. 数据库配置

| 配置项 | 值 |
| --- | --- |
| 数据库类型 | MySQL |
| 数据库名称 | yaoshan |
| 数据库地址 | localhost:3306 |
| 用户名 | root |
| 密码 |  |
| 驱动类 | com.mysql.cj.jdbc.Driver |
| 连接URL | jdbc:mysql://localhost:3306/yaoshan?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai |

## 2. 表结构

### 2.1 用户表 (user)

| 字段名 | 数据类型 | 长度 | 约束 | 默认值 | 描述 |
| --- | --- | --- | --- | --- | --- |
| id | bigint | - | PRIMARY KEY, AUTO_INCREMENT | - | 用户ID |
| openid | varchar | 64 | NOT NULL, UNIQUE | - | 微信openid |
| nickname | varchar | 64 | - | NULL | 昵称 |
| avatar | varchar | 255 | - | NULL | 头像URL |
| gender | tinyint | - | - | 0 | 性别 0:未知 1:男 2:女 |
| phone | varchar | 20 | - | NULL | 手机号 |
| address | varchar | 255 | - | NULL | 地址 |
| age | int | - | - | NULL | 年龄 |
| health_info | varchar | 500 | - | NULL | 健康信息 |
| create_time | datetime | - | NOT NULL | - | 创建时间 |
| update_time | datetime | - | NOT NULL | - | 更新时间 |

### 2.2 商品分类表 (product_category)

| 字段名 | 数据类型 | 长度 | 约束 | 默认值 | 描述 |
| --- | --- | --- | --- | --- | --- |
| id | int | 11 | PRIMARY KEY, AUTO_INCREMENT | - | 分类ID |
| name | varchar | 50 | NOT NULL, UNIQUE | - | 分类名称 |
| icon | varchar | 255 | - | NULL | 分类图标 |
| sort | int | 11 | - | 0 | 排序号 |
| status | tinyint | 4 | - | 1 | 状态 0:禁用 1:启用 |
| create_time | datetime | - | - | CURRENT_TIMESTAMP | 创建时间 |
| update_time | datetime | - | - | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 2.3 商品表 (product)

| 字段名 | 数据类型 | 长度 | 约束 | 默认值 | 描述 |
| --- | --- | --- | --- | --- | --- |
| id | bigint | 20 | PRIMARY KEY, AUTO_INCREMENT | - | 商品ID |
| category_id | int | 11 | NOT NULL, FOREIGN KEY | - | 分类ID |
| name | varchar | 100 | NOT NULL | - | 商品名称 |
| description | text | - | - | NULL | 商品描述 |
| price | decimal | 10,2 | NOT NULL | - | 商品价格 |
| original_price | decimal | 10,2 | - | NULL | 原价 |
| stock | int | 11 | - | 0 | 库存 |
| sales | int | 11 | - | 0 | 销量 |
| main_image | varchar | 255 | - | NULL | 主图 |
| detail_images | text | - | - | NULL | 详情图 |
| specs | text | - | - | NULL | 规格信息 |
| status | tinyint | 4 | - | 1 | 状态 0:下架 1:上架 |
| create_time | datetime | - | - | CURRENT_TIMESTAMP | 创建时间 |
| update_time | datetime | - | - | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 2.4 社区帖子表 (post)

| 字段名 | 数据类型 | 长度 | 约束 | 默认值 | 描述 |
| --- | --- | --- | --- | --- | --- |
| id | bigint | - | PRIMARY KEY, AUTO_INCREMENT | - | 帖子ID |
| openid | varchar | 64 | NOT NULL, FOREIGN KEY | - | 用户openid |
| title | varchar | 100 | NOT NULL | - | 帖子标题 |
| content | text | - | NOT NULL | - | 帖子内容 |
| images | text | - | - | NULL | 图片URL，逗号分隔 |
| view_count | int | - | - | 0 | 浏览量 |
| like_count | int | - | - | 0 | 点赞数 |
| comment_count | int | - | - | 0 | 评论数 |
| status | int | - | - | 0 | 状态：0-正常 1-置顶 2-精华 3-删除 |
| create_time | datetime | - | - | CURRENT_TIMESTAMP | 创建时间 |
| update_time | datetime | - | - | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 2.5 订单表 (order)

| 字段名 | 数据类型 | 长度 | 约束 | 默认值 | 描述 |
| --- | --- | --- | --- | --- | --- |
| id | bigint | - | PRIMARY KEY, AUTO_INCREMENT | - | 订单ID |
| order_no | varchar | 64 | NOT NULL, UNIQUE | - | 订单号 |
| openid | varchar | 64 | NOT NULL, FOREIGN KEY | - | 用户openid |
| total_amount | decimal | 10,2 | NOT NULL | - | 订单总金额 |
| actual_amount | decimal | 10,2 | NOT NULL | - | 实际支付金额 |
| status | int | - | - | 0 | 订单状态：0-待支付 1-待发货 2-待收货 3-已完成 4-已取消 |
| address | varchar | 255 | NOT NULL | - | 收货地址 |
| phone | varchar | 20 | NOT NULL | - | 联系电话 |
| receiver_name | varchar | 64 | NOT NULL | - | 收货人姓名 |
| remark | varchar | 255 | - | NULL | 订单备注 |
| payment_time | datetime | - | - | NULL | 支付时间 |
| delivery_time | datetime | - | - | NULL | 发货时间 |
| receive_time | datetime | - | - | NULL | 收货时间 |
| cancel_time | datetime | - | - | NULL | 取消时间 |
| payment_method | varchar | 32 | - | NULL | 支付方式 |
| create_time | datetime | - | - | CURRENT_TIMESTAMP | 创建时间 |
| update_time | datetime | - | - | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 2.6 订单项表 (order_item)

| 字段名 | 数据类型 | 长度 | 约束 | 默认值 | 描述 |
| --- | --- | --- | --- | --- | --- |
| id | bigint | - | PRIMARY KEY, AUTO_INCREMENT | - | 订单项ID |
| order_no | varchar | 64 | NOT NULL, FOREIGN KEY | - | 订单号 |
| product_id | bigint | - | NOT NULL, FOREIGN KEY | - | 商品ID |
| product_name | varchar | 100 | NOT NULL | - | 商品名称 |
| main_image | varchar | 255 | - | NULL | 商品图片 |
| price | decimal | 10,2 | NOT NULL | - | 商品单价 |
| quantity | int | - | NOT NULL | 1 | 购买数量 |
| total_price | decimal | 10,2 | NOT NULL | - | 小计金额 |
| create_time | datetime | - | - | CURRENT_TIMESTAMP | 创建时间 |
| update_time | datetime | - | - | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 2.7 点赞表 (likes)

| 字段名 | 数据类型 | 长度 | 约束 | 默认值 | 描述 |
| --- | --- | --- | --- | --- | --- |
| id | bigint | - | PRIMARY KEY, AUTO_INCREMENT | - | 点赞ID |
| openid | varchar | 64 | NOT NULL, FOREIGN KEY | - | 用户openid |
| target_id | bigint | - | NOT NULL | - | 目标ID（帖子ID或评论ID） |
| type | int | - | NOT NULL | 1 | 类型：1-帖子点赞 2-评论点赞 |
| create_time | datetime | - | - | CURRENT_TIMESTAMP | 创建时间 |

## 3. 表关系

```
+----------+     +-------------+     +---------+
|  user    |     | product_cat |     | product |
+----------+     +-------------+     +---------+
| id       |1:N  | id          |1:N  | id      |
| openid   |-----| name        |-----| name    |
| nickname |     | icon        |     | price   |
| avatar   |     | sort        |     | stock   |
| gender   |     | status      |     | sales   |
| phone    |     | create_time |     | status  |
| address  |     | update_time |     | create_ |
| age      |     +-------------+     | time    |
| health_i |                         | update_ |
| create_t |                         | time    |
| update_t |                         +---------+
+----------+                              |
    |1:N                                   |N:1
    |                                      |
+----------+     +---------+     +---------+
|   post   |     |  order  |     |order_itm|
+----------+     +---------+     +---------+
| id       |     | id      |1:N  | id      |
| openid   |-----| order_no|-----| order_n |
| title    |     | openid  |     | product |
| content  |     | total_a |     | _id     |
| images   |     | actual_ |     | price   |
| view_co  |     | amount  |     | quantit |
| like_co  |     | status  |     | y       |
| comment_ |     | address |     | total_p |
| status   |     | phone   |     | rice    |
| create_t |     | receiver|     | create_ |
| update_t |     | _name   |     | time    |
+----------+     | remark  |     | update_ |
    |1:N         | payment |     | time    |
    |            | _time   |     +---------+
    |            | delivery|          |
    |            | _time   |          |
+----------+     | receive |          |
|  likes   |     | _time   |          |
+----------+     | cancel_ |          |
| id       |     | time    |          |
| openid   |-----| payment |          |
| target_i |     | _method |          |
| type     |     | create_ |          |
| create_t |     | time    |          |
+----------+     | update_ |          |
                 | time    |          |
                 +---------+          |
                                      |
+----------+     +---------+          |
| comment  |     | category|          |
+----------+     +---------+          |
| id       |     | id      |          |
| openid   |-----| name    |<---------+
| post_id  |     | icon    |
| content  |     | sort    |
| like_co  |     | create_ |
| status   |     | time    |
| create_t |     | update_ |
| update_t |     | time    |
+----------+     +---------+
```

## 4. 索引信息

| 表名 | 索引名 | 字段 | 类型 |
| --- | --- | --- | --- |
| user | PRIMARY | id | 主键索引 |
| user | idx_openid | openid | 唯一索引 |
| product_category | PRIMARY | id | 主键索引 |
| product_category | uk_name | name | 唯一索引 |
| product | PRIMARY | id | 主键索引 |
| product | idx_category_id | category_id | 普通索引 |
| product | idx_status | status | 普通索引 |
| product | idx_sales | sales | 普通索引 |
| post | PRIMARY | id | 主键索引 |
| post | idx_openid | openid | 普通索引 |
| order | PRIMARY | id | 主键索引 |
| order | idx_order_no | order_no | 唯一索引 |
| order | idx_openid | openid | 普通索引 |
| order_item | PRIMARY | id | 主键索引 |
| order_item | idx_order_no | order_no | 普通索引 |
| order_item | idx_product_id | product_id | 普通索引 |
| likes | PRIMARY | id | 主键索引 |
| likes | idx_openid | openid | 普通索引 |
| likes | idx_target_id | target_id | 普通索引 |

## 5. 数据库初始化建议

### 5.1 创建数据库

```sql
CREATE DATABASE IF NOT EXISTS yaoshan DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 5.2 初始化数据

#### 5.2.1 商品分类数据

```sql
INSERT INTO product_category (name, icon, sort, status) VALUES
('养生食品', 'food.png', 1, 1),
('养生用品', 'goods.png', 2, 1),
('养生服务', 'service.png', 3, 1),
('养生知识', 'knowledge.png', 4, 1);
```

#### 5.2.2 商品数据示例

```sql
INSERT INTO product (category_id, name, description, price, original_price, stock, sales, main_image, status) VALUES
(1, '有机蜂蜜', '纯天然有机蜂蜜，富含多种营养成分', 68.00, 88.00, 100, 50, 'honey.jpg', 1),
(1, '核桃粉', '精选优质核桃，研磨成粉，营养丰富', 45.00, 55.00, 200, 80, 'walnut.jpg', 1),
(2, '按摩器', '多功能按摩器，缓解疲劳', 199.00, 299.00, 50, 20, 'massager.jpg', 1),
(2, '艾灸盒', '传统艾灸盒，方便易用', 35.00, 45.00, 150, 60, 'moxa.jpg', 1),
(3, '中医推拿', '专业中医推拿服务，缓解身体不适', 128.00, 168.00, 100, 30, 'tuina.jpg', 1),
(4, '养生书籍', '《黄帝内经》解读，养生知识大全', 58.00, 78.00, 80, 40, 'book.jpg', 1);
```

## 6. 数据库操作建议

1. **索引优化**：根据查询需求，为频繁查询的字段添加索引，如订单表的openid字段，商品表的category_id字段等。

2. **事务管理**：在进行订单创建、库存更新等关键操作时，使用事务管理确保数据一致性。

3. **分表分库**：随着数据量的增加，可以考虑对订单表、日志表等进行分表分库操作。

4. **数据备份**：定期对数据库进行备份，确保数据安全。

5. **性能监控**：使用数据库监控工具，监控数据库性能，及时发现并解决问题。