# 养生项目API接口文档

## 1. 文档说明

本文档详细描述了养生项目的所有API接口，包括用户管理、商品管理、订单管理、社区互动和AI养生咨询等模块。

### 1.1 文档版本

| 版本 | 日期 | 修改说明 |
|-----|-----|--------|
| 1.0 | 2023-XX-XX | 初始版本 |

### 1.2 接口规范

- **请求协议**：HTTP/HTTPS
- **请求方式**：GET、POST、PUT、DELETE
- **响应格式**：JSON
- **编码格式**：UTF-8

## 2. 基础信息

### 2.1 响应格式

所有接口的响应格式统一使用Result对象，包含以下字段：

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

| 字段名 | 类型 | 说明 |
|-------|------|------|
| code | Integer | 响应状态码，200表示成功，非200表示失败 |
| message | String | 响应消息，成功时为"success"，失败时为错误信息 |
| data | Object | 响应数据，根据不同接口返回不同的数据结构 |

### 2.2 状态码说明

| 状态码 | 说明 |
|-------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权，请先登录 |
| 403 | 权限不足 |
| 404 | 请求的资源不存在 |
| 500 | 服务器内部错误 |

### 2.3 认证方式

项目使用JWT进行认证，登录成功后获取token，之后每次请求需要在请求头中添加：

```
Authorization: Bearer {token}
```

## 3. 用户管理模块

### 3.1 微信登录

**接口地址**：`/api/user/login`
**请求方式**：POST
**权限要求**：无

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| code | String | 是 | 微信登录code |

**请求示例**：
```json
{
  "code": "wx_code"
}
```

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3.2 获取用户信息

**接口地址**：`/api/user/info`
**请求方式**：GET
**权限要求**：需登录

**请求头**：
```
Authorization: Bearer {token}
```

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "openid": "oX57u4u8X57u4u8X57u4u8X57u4",
    "nickname": "用户昵称",
    "avatar": "https://thirdwx.qlogo.cn/mmopen/...",
    "gender": 1,
    "phone": "13800138000",
    "createdAt": "2023-01-01 00:00:00"
  }
}
```

### 3.3 更新用户信息

**接口地址**：`/api/user/update`
**请求方式**：PUT
**权限要求**：需登录

**请求头**：
```
Authorization: Bearer {token}
```

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| nickname | String | 否 | 用户昵称 |
| avatar | String | 否 | 头像URL |
| gender | Integer | 否 | 性别(1-男，2-女) |
| phone | String | 否 | 手机号码 |

**请求示例**：
```json
{
  "nickname": "新昵称",
  "phone": "13800138000"
}
```

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": true
}
```

## 4. 商品管理模块

### 4.1 分页查询商品列表

**接口地址**：`/api/product/list`
**请求方式**：POST
**权限要求**：无

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| page | Integer | 否 | 页码，默认1 |
| size | Integer | 否 | 每页数量，默认10 |
| categoryId | Integer | 否 | 分类ID |
| keyword | String | 否 | 搜索关键词 |
| sortType | String | 否 | 排序类型(sales-销量，price_asc-价格升序，price_desc-价格降序) |

**请求示例**：
```json
{
  "page": 1,
  "size": 10,
  "categoryId": 1,
  "keyword": "养生茶",
  "sortType": "sales"
}
```

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "name": "枸杞养生茶",
        "price": 98.00,
        "originalPrice": 128.00,
        "image": "https://example.com/images/gouqi.jpg",
        "description": "天然枸杞，养肝明目",
        "sales": 1000,
        "categoryId": 1,
        "categoryName": "养生茶"
      }
    ],
    "total": 100,
    "size": 10,
    "current": 1,
    "pages": 10
  }
}
```

### 4.2 根据分类ID查询商品列表

**接口地址**：`/api/product/category/{categoryId}`
**请求方式**：GET
**权限要求**：无

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| categoryId | Integer | 是 | 分类ID |

**查询参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| page | Integer | 否 | 页码，默认1 |
| size | Integer | 否 | 每页数量，默认10 |

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "name": "枸杞养生茶",
        "price": 98.00,
        "originalPrice": 128.00,
        "image": "https://example.com/images/gouqi.jpg",
        "description": "天然枸杞，养肝明目",
        "sales": 1000,
        "categoryId": 1,
        "categoryName": "养生茶"
      }
    ],
    "total": 50,
    "size": 10,
    "current": 1,
    "pages": 5
  }
}
```

### 4.3 根据ID查询商品详情

**接口地址**：`/api/product/{id}`
**请求方式**：GET
**权限要求**：无

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|
| id | Long | 是 | 商品ID |

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "枸杞养生茶",
    "price": 98.00,
    "originalPrice": 128.00,
    "image": "https://example.com/images/gouqi.jpg",
    "detailImages": ["https://example.com/images/gouqi1.jpg", "https://example.com/images/gouqi2.jpg"],
    "description": "天然枸杞，养肝明目",
    "specifications": "每盒30包，每包5g",
    "ingredients": "枸杞、菊花、红枣",
    "usage": "每日一包，用温水冲泡",
    "sales": 1000,
    "stock": 500,
    "categoryId": 1,
    "categoryName": "养生茶",
    "createdAt": "2023-01-01 00:00:00"
  }
}
```

### 4.4 获取所有商品分类

**接口地址**：`/api/product/categories`
**请求方式**：GET
**权限要求**：无

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "养生茶",
      "icon": "https://example.com/icons/tea.png",
      "parentId": 0,
      "sort": 1
    },
    {
      "id": 2,
      "name": "养生食材",
      "icon": "https://example.com/icons/food.png",
      "parentId": 0,
      "sort": 2
    }
  ]
}
```

## 5. 订单管理模块

### 5.1 创建订单

**接口地址**：`/api/order/create`
**请求方式**：POST
**权限要求**：需登录

**请求头**：
```
Authorization: Bearer {token}
```

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| address | String | 是 | 收货地址 |
| phone | String | 是 | 联系电话 |
| receiverName | String | 是 | 收货人姓名 |
| remark | String | 否 | 订单备注 |
| products | Array | 是 | 订单商品列表 |

**products字段说明**：
| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| productId | Long | 是 | 商品ID |
| quantity | Integer | 是 | 购买数量 |

**请求示例**：
```json
{
  "address": "北京市朝阳区XX街道XX小区XX号楼XX单元XX室",
  "phone": "13800138000",
  "receiverName": "张三",
  "remark": "请尽快发货",
  "products": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "orderNo": "2023010112345678",
    "totalAmount": 296.00,
    "address": "北京市朝阳区XX街道XX小区XX号楼XX单元XX室",
    "phone": "13800138000",
    "receiverName": "张三",
    "remark": "请尽快发货",
    "status": 1,
    "statusText": "待付款",
    "createdAt": "2023-01-01 12:34:56",
    "products": [
      {
        "productId": 1,
        "productName": "枸杞养生茶",
        "price": 98.00,
        "quantity": 2,
        "image": "https://example.com/images/gouqi.jpg"
      },
      {
        "productId": 2,
        "productName": "红枣养生茶",
        "price": 100.00,
        "quantity": 1,
        "image": "https://example.com/images/reddate.jpg"
      }
    ]
  }
}
```

### 5.2 获取订单详情

**接口地址**：`/api/order/detail/{orderNo}`
**请求方式**：GET
**权限要求**：需登录

**请求头**：
```
Authorization: Bearer {token}
```

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| orderNo | String | 是 | 订单号 |

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "orderNo": "2023010112345678",
    "totalAmount": 296.00,
    "address": "北京市朝阳区XX街道XX小区XX号楼XX单元XX室",
    "phone": "13800138000",
    "receiverName": "张三",
    "remark": "请尽快发货",
    "status": 1,
    "statusText": "待付款",
    "createdAt": "2023-01-01 12:34:56",
    "products": [
      {
        "productId": 1,
        "productName": "枸杞养生茶",
        "price": 98.00,
        "quantity": 2,
        "image": "https://example.com/images/gouqi.jpg"
      },
      {
        "productId": 2,
        "productName": "红枣养生茶",
        "price": 100.00,
        "quantity": 1,
        "image": "https://example.com/images/reddate.jpg"
      }
    ]
  }
}
```

### 5.3 查询订单列表

**接口地址**：`/api/order/list`
**请求方式**：GET
**权限要求**：需登录

**请求头**：
```
Authorization: Bearer {token}
```

**查询参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| page | Integer | 否 | 页码，默认1 |
| size | Integer | 否 | 每页数量，默认10 |
| status | Integer | 否 | 订单状态(1-待付款，2-待发货，3-待收货，4-已完成，5-已取消) |
| startTime | String | 否 | 开始时间，格式：yyyy-MM-dd HH:mm:ss |
| endTime | String | 否 | 结束时间，格式：yyyy-MM-dd HH:mm:ss |

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "orderNo": "2023010112345678",
        "totalAmount": 296.00,
        "status": 1,
        "statusText": "待付款",
        "createdAt": "2023-01-01 12:34:56",
        "productCount": 3,
        "products": [
          {
            "productId": 1,
            "productName": "枸杞养生茶",
            "price": 98.00,
            "quantity": 2,
            "image": "https://example.com/images/gouqi.jpg"
          },
          {
            "productId": 2,
            "productName": "红枣养生茶",
            "price": 100.00,
            "quantity": 1,
            "image": "https://example.com/images/reddate.jpg"
          }
        ]
      }
    ],
    "total": 10,
    "size": 10,
    "current": 1,
    "pages": 1
  }
}
```

## 6. 社区模块

### 6.1 帖子相关接口

#### 6.1.1 创建帖子

**接口地址**：`/api/community/post`
**请求方式**：POST
**权限要求**：需登录

**请求头**：
```
Authorization: Bearer {token}
```

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| title | String | 是 | 帖子标题 |
| content | String | 是 | 帖子内容 |
| images | Array | 否 | 图片列表 |
| tags | Array | 否 | 标签列表 |

**请求示例**：
```json
{
  "title": "分享我的养生经验",
  "content": "大家好，今天我想分享一下我的养生心得...",
  "images": ["https://example.com/images/exp1.jpg", "https://example.com/images/exp2.jpg"],
  "tags": ["养生茶", "健康生活"]
}
```

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "title": "分享我的养生经验",
    "content": "大家好，今天我想分享一下我的养生心得...",
    "images": ["https://example.com/images/exp1.jpg", "https://example.com/images/exp2.jpg"],
    "tags": ["养生茶", "健康生活"],
    "userId": 1,
    "nickname": "养生达人",
    "avatar": "https://thirdwx.qlogo.cn/mmopen/...",
    "likeCount": 0,
    "commentCount": 0,
    "viewCount": 0,
    "isTop": false,
    "isLiked": false,
    "createdAt": "2023-01-01 12:34:56"
  }
}
```

#### 6.1.2 获取帖子详情

**接口地址**：`/api/community/post/{postId}`
**请求方式**：GET
**权限要求**：无

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| postId | Long | 是 | 帖子ID |

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "title": "分享我的养生经验",
    "content": "大家好，今天我想分享一下我的养生心得...",
    "images": ["https://example.com/images/exp1.jpg", "https://example.com/images/exp2.jpg"],
    "tags": ["养生茶", "健康生活"],
    "userId": 1,
    "nickname": "养生达人",
    "avatar": "https://thirdwx.qlogo.cn/mmopen/...",
    "likeCount": 100,
    "commentCount": 20,
    "viewCount": 500,
    "isTop": false,
    "isLiked": true,
    "createdAt": "2023-01-01 12:34:56"
  }
}
```

#### 6.1.3 查询帖子列表

**接口地址**：`/api/community/post`
**请求方式**：GET
**权限要求**：无

**查询参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| page | Integer | 否 | 页码，默认1 |
| size | Integer | 否 | 每页数量，默认10 |
| userId | Long | 否 | 用户ID，用于查询指定用户的帖子 |
| tag | String | 否 | 标签，用于按标签筛选帖子 |
| sortType | String | 否 | 排序类型(time-最新，hot-最热，like-点赞数) |
| keyword | String | 否 | 搜索关键词 |

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "title": "分享我的养生经验",
        "content": "大家好，今天我想分享一下我的养生心得...",
        "images": ["https://example.com/images/exp1.jpg"],
        "tags": ["养生茶", "健康生活"],
        "userId": 1,
        "nickname": "养生达人",
        "avatar": "https://thirdwx.qlogo.cn/mmopen/...",
        "likeCount": 100,
        "commentCount": 20,
        "viewCount": 500,
        "isTop": false,
        "isLiked": true,
        "createdAt": "2023-01-01 12:34:56"
      }
    ],
    "total": 100,
    "size": 10,
    "current": 1,
    "pages": 10
  }
}
```

### 6.2 评论相关接口

#### 6.2.1 创建评论

**接口地址**：`/api/community/comment`
**请求方式**：POST
**权限要求**：需登录

**请求头**：
```
Authorization: Bearer {token}
```

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| postId | Long | 是 | 帖子ID |
| parentId | Long | 否 | 父评论ID，用于回复评论 |
| content | String | 是 | 评论内容 |

**请求示例**：
```json
{
  "postId": 1,
  "parentId": null,
  "content": "非常实用的分享，感谢！"
}
```

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "postId": 1,
    "parentId": null,
    "content": "非常实用的分享，感谢！",
    "userId": 2,
    "nickname": "用户2",
    "avatar": "https://thirdwx.qlogo.cn/mmopen/...",
    "likeCount": 0,
    "isLiked": false,
    "createdAt": "2023-01-01 12:40:00",
    "replies": []
  }
}
```

#### 6.2.2 获取帖子的评论列表

**接口地址**：`/api/community/comment/post/{postId}`
**请求方式**：GET
**权限要求**：无

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| postId | Long | 是 | 帖子ID |

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "postId": 1,
      "parentId": null,
      "content": "非常实用的分享，感谢！",
      "userId": 2,
      "nickname": "用户2",
      "avatar": "https://thirdwx.qlogo.cn/mmopen/...",
      "likeCount": 10,
      "isLiked": true,
      "createdAt": "2023-01-01 12:40:00",
      "replies": [
        {
          "id": 2,
          "postId": 1,
          "parentId": 1,
          "content": "不客气，很高兴对你有帮助！",
          "userId": 1,
          "nickname": "养生达人",
          "avatar": "https://thirdwx.qlogo.cn/mmopen/...",
          "likeCount": 5,
          "isLiked": false,
          "createdAt": "2023-01-01 12:45:00",
          "replies": []
        }
      ]
    }
  ]
}
```

#### 6.2.3 统计用户评论数

**接口地址**：`/api/community/comment/count`
**请求方式**：GET
**权限要求**：需登录

**请求头**：
```
Authorization: Bearer {token}
```

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": 50
}
```

## 7. AI养生咨询模块

### 7.1 进行AI养生咨询

**接口地址**：`/api/ai/health/consult`
**请求方式**：POST
**权限要求**：无

**请求参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| question | String | 是 | 用户问题 |
| symptoms | String | 否 | 用户症状描述 |
| age | Integer | 否 | 用户年龄 |
| gender | Integer | 否 | 用户性别（1-男，2-女） |
| constitution | String | 否 | 用户体质类型（如：湿热质、阴虚质等） |
| dietPreference | String | 否 | 用户饮食偏好 |
| healthIssues | Array | 否 | 用户健康问题列表 |
| expectMethod | String | 否 | 用户期望的养生方式（食疗、运动、按摩等） |

**请求示例**：
```json
{
  "question": "如何改善睡眠质量？",
  "age": 30,
  "gender": 1,
  "constitution": "湿热质",
  "symptoms": "最近入睡困难，容易惊醒",
  "healthIssues": ["失眠", "焦虑"],
  "expectMethod": "食疗"
}
```

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "question": "如何改善睡眠质量？",
    "answer": "根据您的情况，建议您通过以下方式改善睡眠质量...",
    "solutions": [
      {
        "title": "食疗改善方案",
        "description": "通过饮食调节改善睡眠",
        "steps": ["睡前饮用温热牛奶", "避免辛辣刺激性食物", "晚餐适量食用小米粥"],
        "recommendLevel": 5
      },
      {
        "title": "生活习惯调整",
        "description": "调整作息和生活习惯",
        "steps": ["保持规律作息", "睡前避免使用电子设备", "适当运动但避免睡前剧烈运动"],
        "recommendLevel": 4
      }
    ],
    "recommendedFoods": [
      {
        "name": "酸枣仁",
        "effect": "养心安神，改善睡眠",
        "suggestion": "可与大米煮粥食用",
        "taboos": "脾胃虚寒者慎用"
      },
      {
        "name": "百合",
        "effect": "清心安神，润肺止咳",
        "suggestion": "可与莲子、红枣煮糖水",
        "taboos": "风寒咳嗽者忌用"
      }
    ],
    "notices": [
      "保持良好的心态，避免过度焦虑",
      "睡前避免饮用咖啡、茶等刺激性饮料",
      "如果长期失眠严重，建议咨询专业医生"
    ],
    "consultTime": "2023-01-01 12:34:56",
    "summary": "针对您的湿热体质和睡眠问题，建议通过食疗和生活习惯调整来改善睡眠质量。"
  }
}
```

### 7.2 获取季节性养生建议

**接口地址**：`/api/ai/health/season/{season}`
**请求方式**：GET
**权限要求**：无

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| season | String | 是 | 季节（春季、夏季、秋季、冬季） |

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "question": "春季养生建议",
    "answer": "春季是万物复苏的季节，养生重点在于调养肝气、保护阳气...",
    "solutions": [
      {
        "title": "春季饮食养生",
        "description": "春季饮食宜温补",
        "steps": ["适量食用春笋、荠菜等时令蔬菜", "减少生冷食物摄入", "可适当食用葱姜蒜等发散食物"],
        "recommendLevel": 5
      }
    ],
    "recommendedFoods": [
      {
        "name": "春笋",
        "effect": "清热化痰，利膈爽胃",
        "suggestion": "可炒食或煮汤",
        "taboos": "肠胃功能弱者不宜多食"
      }
    ],
    "notices": [
      "春季气候变化较大，注意增减衣物",
      "保持心情舒畅，避免肝气郁结",
      "适当增加户外活动，呼吸新鲜空气"
    ],
    "consultTime": "2023-01-01 12:34:56",
    "summary": "春季养生重点在于调养肝气、保护阳气，注意饮食调理和适当运动。"
  }
}
```

### 7.3 获取饮食养生建议

**接口地址**：`/api/ai/health/food/{foodType}`
**请求方式**：GET
**权限要求**：无

**路径参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| foodType | String | 是 | 饮食类型（如：养生茶、素食、滋补等） |

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "question": "养生茶推荐",
    "answer": "养生茶是一种简便易行的养生方式，不同的养生茶有不同的功效...",
    "solutions": [
      {
        "title": "春季养肝茶",
        "description": "春季宜养肝",
        "steps": ["菊花3g，枸杞5g，决明子3g，用开水冲泡", "每日饮用1-2次", "可根据个人口味适量添加蜂蜜"],
        "recommendLevel": 4
      }
    ],
    "recommendedFoods": [
      {
        "name": "菊花",
        "effect": "清肝明目，清热解毒",
        "suggestion": "与枸杞搭配效果更佳",
        "taboos": "脾胃虚寒者不宜过量"
      }
    ],
    "notices": [
      "养生茶不宜过浓",
      "根据个人体质选择适合的茶叶",
      "不宜用茶水服药"
    ],
    "consultTime": "2023-01-01 12:34:56",
    "summary": "养生茶是一种简便易行的养生方式，可根据季节和个人体质选择适合的配方。"
  }
}
```

### 7.4 获取运动养生建议

**接口地址**：`/api/ai/health/exercise`
**请求方式**：GET
**权限要求**：无

**查询参数**：

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| age | Integer | 是 | 用户年龄 |
| healthCondition | String | 是 | 健康状况描述 |

**响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "question": "适合30岁，有轻度颈椎病的运动建议",
    "answer": "根据您的年龄和健康状况，建议进行以下运动来改善颈椎问题...",
    "solutions": [
      {
        "title": "颈椎康复运动",
        "description": "缓解颈椎压力，增强颈部肌肉",
        "steps": ["缓慢做头部前后左右运动，每个方向停留5秒", "颈部画圈运动，顺时针和逆时针各10次", "双手交叉抱头，头部向后用力，双手向前抵抗"],
        "recommendLevel": 5
      }
    ],
    "recommendedFoods": [],
    "notices": [
      "运动时动作要缓慢，避免剧烈运动",
      "保持正确的坐姿和站姿",
      "避免长时间低头看手机或电脑",
      "如果疼痛加重，应立即停止并咨询医生"
    ],
    "consultTime": "2023-01-01 12:34:56",
    "summary": "针对轻度颈椎病，建议进行温和的颈部运动，注意保持正确姿势，避免长时间固定一个姿势。"
  }
}
```

## 8. 附录

### 8.1 订单状态说明

| 状态码 | 状态文本 | 说明 |
|-------|---------|------|
| 1 | 待付款 | 订单已创建，等待用户支付 |
| 2 | 待发货 | 用户已支付，等待商家发货 |
| 3 | 待收货 | 商家已发货，等待用户收货 |
| 4 | 已完成 | 用户已确认收货，订单完成 |
| 5 | 已取消 | 订单已取消 |

### 8.2 体质类型说明

| 体质类型 | 说明 |
|---------|------|
| 平和质 | 健康的体质状态 |
| 气虚质 | 元气不足，容易疲劳 |
| 阳虚质 | 阳气不足，怕冷 |
| 阴虚质 | 阴液不足，容易上火 |
| 痰湿质 | 痰湿凝聚，体型偏胖 |
| 湿热质 | 湿热内蕴，容易长痘 |
| 血瘀质 | 血行不畅，容易淤青 |
| 气郁质 | 气机郁滞，情绪低落 |
| 特禀质 | 过敏体质 |

### 8.3 常见错误码

| 错误码 | 错误消息 | 说明 |
|-------|---------|------|
| 400 | 参数错误 | 请求参数格式不正确或缺失必要参数 |
| 401 | 未授权 | 未提供认证信息或认证信息无效 |
| 403 | 权限不足 | 用户没有权限执行该操作 |
| 404 | 资源不存在 | 请求的资源不存在 |
| 500 | 服务器内部错误 | 服务器处理请求时发生错误 |
| 5001 | 商品不存在 | 请求的商品不存在 |
| 5002 | 库存不足 | 商品库存不足 |
| 5003 | 订单不存在 | 请求的订单不存在 |
| 5004 | 订单状态错误 | 当前订单状态不允许执行该操作 |
| 5005 | 支付失败 | 支付过程中发生错误 |