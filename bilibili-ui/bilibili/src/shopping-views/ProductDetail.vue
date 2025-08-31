<template>
  <div class="product-detail-container" v-if="product.id">
    <el-row :gutter="30">
      <el-col :span="10">
        <div class="gallery-section">
          <div class="main-image-wrapper">
            <el-image :src="product.mainImage" fit="contain" class="main-image"/>
          </div>
        </div>
      </el-col>

      <el-col :span="14">
        <div class="info-section">
          <h1 class="product-title">{{ product.name }}</h1>
          <p class="product-description">{{ product.description }}</p>

          <div class="price-box">
            <span class="label">价格</span>
            <span class="price">¥ {{ product.price.toFixed(2) }}</span>
          </div>

          <div class="quantity-box">
            <span class="label">数量</span>
            <el-input-number v-model="quantity" :min="1" :max="product.storeNum" @change="handleQuantityChange"/>
            <span class="stock-info">库存：{{ product.storeNum }}件</span>
          </div>

          <div class="publisher-box">
            <div class="publisher-info">
              <span class="label">发行商：</span>
              <span class="publisher-name tag" @click="goToPublisherPage(product.publisher_id)">
                {{ product.publisher }}
              </span>

            </div>
          </div>

          <div class="action-buttons">
            <el-button type="primary" size="large" @click="buyNow" color="#fb7299">立即购买</el-button>
            <el-button type="danger" size="large" @click="addToCart" plain>加入购物车</el-button>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-card class="specs-section" shadow="never">
      <template #header>
        <div class="card-header">
          <span>商品详情</span>
        </div>
      </template>
      <el-descriptions :column="2" border>
        <el-descriptions-item v-for="spec in product.specs" :key="spec.label">
          <template #label>{{ spec.label }}</template>
          {{ spec.value }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>

  </div>
  <el-empty v-else description="商品加载中..." />
</template>

<script>
import { ElMessage } from 'element-plus';
import {queryById} from "../api/product.js";
import {getUrl} from "../utils/url.js";
import {addToCart} from "../api/cart.js";

export default {
  name: 'ProductDetail',
  props: ['id'],
  data() {
    return {
      product: {}, // 商品信息
      quantity: 1, // 购买数量
    };
  },
  methods: {
    getUrl,
    async fetchProductDetails(id) {
      try {
        let {data:data} = await queryById(id)
        console.log(data)
          this.product = data.data
          // 设置主图片和图片画廊
          if (this.product.imgUrl) {
            this.product.mainImage = this.getUrl(this.product.imgUrl)
          }
          // 设置默认规格信息
          this.product.specs = [
            { label: '商品名称', value: this.product.name },
            { label: '商品描述', value: this.product.description },
            { label: '库存数量', value: this.product.storeNum || 0 },
            { label: '商品价格', value: `¥${this.product.price}` },
              { label: '商品发行商', value: this.product.publisher },
              { label: '商品种类', value: this.product.category },
          ]
      } catch (error) {
        console.error('获取商品详情失败:', error)
        ElMessage.error('获取商品详情失败')
      }
    },
    handleQuantityChange(value) {
      console.log('数量变为:', value);
    },
    async addToCart() {
      let {data:data} = await addToCart({productId:this.id,quantity:this.quantity})
      console.log(data)
      if(data.code===201){
        ElMessage.success('添加购物车成功')
      }else{
        ElMessage.error('添加购物车失败')
      }
    },
    buyNow() {
    }
  },
  mounted() {
    this.fetchProductDetails(this.id);
  }
}
</script>

<style scoped>
.publisher-info .label {
  color: #999;       /* 将颜色改为柔和的灰色 */
  font-size: 14px;    /* 确保字号统一 */
  margin-right: 15px; /* 调整与右边内容的间距 */
}
/* 标签样式 - 模仿 Element Plus 的 light 风格 */
.publisher-name.tag {
  display: inline-block; /* 让 span 支持内外边距 */
  padding: 4px 10px;
  font-size: 13px;
  border-radius: 6px;
  background-color: #fdeff2; /* 淡粉色背景 */
  color: #fb7299;       /* 主题粉色文字 */
  border: 1px solid #fce0e8;
}
.product-detail-container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
}

/* 左侧图片区 */
.gallery-section {
  display: flex;
  flex-direction: column;
}
.main-image-wrapper {
  border: 1px solid #e3e5e7;
  border-radius: 8px;
  padding: 10px;
  margin-bottom: 15px;
  height: 400px;
}
.main-image {
  width: 100%;
  height: 100%;
}
.thumbnail-wrapper {
  display: flex;
  gap: 10px;
}
.thumbnail-image {
  width: 80px;
  height: 80px;
  border: 2px solid #e3e5e7;
  border-radius: 6px;
  cursor: pointer;
  transition: border-color 0.3s;
}
.thumbnail-image:hover {
  border-color: #fb7299;
}

/* 右侧信息区 */
.info-section {
  padding: 10px;
}
.product-title {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 10px;
}
.product-description {
  color: #666;
  font-size: 14px;
  line-height: 1.6;
  margin-bottom: 20px;
}
.price-box, .quantity-box {
  background-color: #f5f7fa;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  display: flex;
  align-items: center;
}
.label {
  color: #999;
  font-size: 14px;
  margin-right: 30px;
}
.price {
  font-size: 28px;
  font-weight: bold;
  color: #fb7299;
}
.stock-info {
  margin-left: 20px;
  font-size: 14px;
  color: #999;
}

.action-buttons {
  margin-top: 30px;
}
.action-buttons .el-button {
  min-width: 160px;
  font-size: 16px;
}

/* 详情区域 */
.specs-section {
  margin-top: 30px;
  border: 1px solid #e3e5e7;
}
.card-header {
  font-weight: bold;
}
</style>