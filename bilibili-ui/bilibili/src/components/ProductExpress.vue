<template>
  <el-dialog
      v-model="dialogVisible"
      title="商品详情"
      width="800px"
  >
    <div v-if="product.id" class="dialog-content">
      <el-row :gutter="30">
        <el-col :span="12">
          <div class="main-image-wrapper">
            <el-image :src="product.mainImage" fit="contain" class="main-image"/>
          </div>
        </el-col>

        <el-col :span="12">
          <div class="info-section">
            <h2 class="product-title">{{ product.name }}</h2>
            <p class="product-tagline">{{ product.description }}</p>

            <div class="price-box">
              <span class="label">价格</span>
              <span class="price">¥ {{ product.price.toFixed(2) }}</span>
            </div>

            <div class="quantity-box">
              <span class="label">数量</span>
              <el-input-number v-model="quantity" :min="1" :max="product.storeNum"/>
              <span class="stock-info">库存：{{ product.storeNum }}件</span>
            </div>

            <div class="action-buttons">
              <el-button type="primary" size="large" @click="buyNow" color="#fb7299">立即购买</el-button>
              <el-button type="danger" size="large" @click="addToCart" plain>加入购物车</el-button>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-empty v-else description="商品信息加载中..." />
  </el-dialog>
</template>

<script>
import { ElMessage } from 'element-plus';
import { queryById } from "../api/product.js";
import { getUrl } from "../utils/url.js";

export default {
  name: 'ProductDetailModal',
  props:['id'],
  data() {
    return {
      // --- 核心数据 ---
      dialogVisible: false, // 控制弹窗是否显示，默认为 false (隐藏)
      product: {},          // 存储从后端获取的商品信息
      quantity: 1,          // 购买数量
    };
  },
  methods: {
    // --- 核心方法 ---

    // 1. 打开弹窗 (这个方法将会从父组件Mall.vue中调用)
    open(productId) {
      this.dialogVisible = true; // 直接把弹窗打开
      this.fetchProductDetails(productId); // 打开后，立即去请求商品数据
    },

    // 2. 请求商品数据 (逻辑和你原来写的几乎一样)
    async fetchProductDetails(id) {
      try {
        let { data: res } = await queryById(id);
        if (res.code === 200) {
          this.product = res.data;
          if (this.product.imgUrl) {
            this.product.mainImage = getUrl(this.product.imgUrl);
          }
        } else {
          ElMessage.error(res.msg || '获取商品详情失败');
          this.dialogVisible = false; // 如果失败，就把弹窗关掉
        }
      } catch (error) {
        console.error('获取商品详情失败:', error);
        ElMessage.error('网络错误，获取商品详情失败');
        this.dialogVisible = false;
      }
    },

    // 3. 按钮点击事件
    addToCart() {
      ElMessage.success(`已将 ${this.quantity} 件 "${this.product.name}" 加入购物车！`);
    },
    buyNow() {
      ElMessage.info('正在跳转到订单确认页面...');
      this.dialogVisible = false; // 点击后关闭弹窗
    }
  }
}
</script>

<style scoped>
/* 样式和之前一样，保持不变 */
.dialog-content { padding: 0 20px 20px 20px; }
.main-image-wrapper { border: 1px solid #e3e5e7; border-radius: 8px; height: 350px; display: flex; align-items: center; justify-content: center; }
.main-image { max-width: 100%; max-height: 100%; }
.info-section { height: 100%; display: flex; flex-direction: column; }
.product-title { font-size: 22px; font-weight: 600; margin: 0 0 5px 0; }
.product-tagline { color: #999; font-size: 14px; margin-bottom: 20px; }
.price-box { background-color: #f5f7fa; padding: 15px; border-radius: 8px; margin-bottom: 15px; }
.label { color: #999; font-size: 14px; margin-right: 20px; }
.price { font-size: 26px; font-weight: bold; color: #fb7299; }
.quantity-box { display: flex; align-items: center; margin-bottom: 30px; background-color: #f5f7fa; padding: 15px; border-radius: 8px; }
.stock-info { margin-left: 15px; font-size: 13px; color: #999; }
.action-buttons { margin-top: auto; display: flex; gap: 10px; }
.action-buttons .el-button { flex: 1; }
:deep(.el-dialog__body) { padding-top: 10px; }
</style>