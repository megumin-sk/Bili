<template>
  <div class="mall-container">
    <div class="banner-section">
      <el-carousel height="300px" motion-blur>
        <el-carousel-item v-for="item in bannerItems" :key="item.id">
          <img :src="item.imageUrl" class="banner-image" alt="Promotion Banner"/>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="product-grid">
      <el-card v-for="product in products" :key="product.id" class="product-card" shadow="hover">
        <div class="product-image-container">
          <img :src="getUrl(product.imgUrl)" class="product-image" alt="product.name"/>
        </div>
        <div class="product-info">
          <h3 class="product-name">{{ product.name }}</h3>
          <p class="product-description">{{ product.description }}</p>
          <div class="product-footer">
            <span class="product-price">¥ {{ product.price.toFixed(2) }}</span>
            <el-button type="primary" :color="'#fb7299'" @click="checkDetail(product.id)">预览</el-button>
            <el-button type="primary" :color="'#fb7299'" @click="lookDetail(product.id)">查看详情</el-button>
          </div>
        </div>
      </el-card>
    </div>
    <ProductDetail ref="detailModal" />
  </div>
</template>

<script>
// 引入需要的图标
import { CaretTop, CaretBottom } from '@element-plus/icons-vue';
import renImg from '../static-resources/ren.png'
import rockImg from '../static-resources/rock.png'
import megumin from '../static-resources/megumin2.1.jpeg'
import {queryAllProduct} from "../api/product.js";
import {getUrl} from "../utils/url.js";
import ProductDetail from "../components/ProductExpress.vue";
export default {
  name: 'Mall',
  // 注册需要用到的组件，比如图标
  components: {
    CaretTop,
    CaretBottom,
    ProductDetail
  },
  // data 函数返回组件的响应式状态
  data() {
    return {
      //控制弹窗所需的数据
      isModalVisible: false,
      selectedProductId: null,
      // Banner 的模拟数据
      bannerItems: [
        { id: 1, imageUrl:renImg },
        { id: 2, imageUrl:rockImg },
      ],

      // 商城的模拟商品数据
      products: [
        { id: 2, name: 'megumin Q版手办', description: '官方正品，Q萌可爱', price: 128.00, imgUrl: megumin },
      ],
    };
  },
  methods: {
    getUrl,
    async queryAll(){
      let {data:data} = await queryAllProduct()
      // console.log(data)
      this.products = data.data
    }
    , checkDetail(id){
        // this.$refs.detailModal 就是我们在模板里设置的 ref="detailModal"
        // .open(productId) 就是调用子组件里我们自己写的 open 方法，并把商品ID传过去
        this.$refs.detailModal.open(id);
    }
    ,lookDetail(id){
      this.$router.push(`/mall/productDetail/${id}`)
    }
  },mounted() {
    this.queryAll()
  }
}
</script>

<style scoped>
.mall-container {
  padding: 20px;
  background-color: #f5f7fa;
}

/* Banner 样式 */
.banner-section {
  margin-bottom: 30px;
  border-radius: 12px;
  overflow: hidden;
}
.banner-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 控制区样式 */
.controls-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #fff;
  border-radius: 8px;
}
.price-sort {
  display: flex;
  align-items: center;
  cursor: pointer;
  color: #666;
}
.price-sort span {
  margin-right: 5px;
}
.price-sort .el-icon {
  margin-left: -5px;
}

/* 商品网格样式 */
.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

/* 商品卡片样式 */
.product-card {
  border-radius: 12px;
  transition: all 0.3s ease;
}
.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0,0,0,0.12);
}

.product-image-container {
  width: 100%;
  aspect-ratio: 1 / 1; /* 保持图片为正方形 */
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.product-info {
  padding: 15px;
}
.product-name {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  margin: 0 0 8px 0;
  /* 限制标题最多显示一行 */
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.product-description {
  font-size: 13px;
  color: #999;
  margin: 0 0 15px 0;
  height: 38px;
  /* 限制描述最多显示两行 */
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
.product-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.product-price {
  font-size: 18px;
  font-weight: bold;
  color: #fb7299;
}

:deep(.el-card__body) {
  padding: 0;
}
</style>