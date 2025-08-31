<template>
  <div class="shopping-cart-container">
    <el-card shadow="never" class="cart-card">
      <template #header>
        <div class="card-header">
          <span>我的购物车</span>
          <span class="header-tip">请及时确认您选购的商品</span>
        </div>
      </template>

      <el-table
          v-if="cartItems.length > 0"
          :data="cartItems"
          ref="tableRef"
          @selection-change="handleSelectionChange"
          class="cart-table"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column label="商品信息" min-width="300">
          <template #default="{ row }">
            <div class="product-info">
              <el-image :src="row.displayImgUrl" fit="cover" class="product-image" />
              <div class="product-details">
                <span class="product-name">{{ row.productName }}</span>
                <span class="product-desc">{{ row.description }}</span>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="单价" width="150" align="center">
          <template #default="{ row }">
            <span class="price">¥{{ row.productPrice.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="数量" width="180" align="center">
          <template #default="{ row }">
            <el-input-number v-model="row.buyNum" :min="1" :max="row.stock" size="small" />
          </template>
        </el-table-column>
        <el-table-column label="小计" width="150" align="center">
          <template #default="{ row }">
            <span class="price subtotal">¥{{ (row.productPrice * row.buyNum).toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" align="center">
          <template #default="{ $index }">
            <el-button type="danger" :icon="Delete" circle plain @click="removeItem($index)"/>
          </template>
        </el-table-column>
      </el-table>

      <el-empty v-else description="购物车还是空的，快去逛逛吧！">
        <el-button type="primary" color="#fb7299" @click="goToMall">前往商城</el-button>
      </el-empty>

    </el-card>

    <div v-if="cartItems.length > 0" class="checkout-bar">
      <div class="left-section">
        <el-checkbox v-model="isSelectAll" @change="handleSelectAll" label="全选" size="large" />
        <el-button type="danger" link @click="removeSelectedItems">删除选中</el-button>
      </div>
      <div class="right-section">
        <div class="total-summary">
          <span>已选择 <strong class="highlight">{{ totalSelectedItems }}</strong> 件商品</span>
          <span class="total-label">合计：</span>
          <span class="total-price">¥{{ totalPrice.toFixed(2) }}</span>
        </div>
        <el-button
            type="primary"
            color="#fb7299"
            size="large"
            class="checkout-btn"
            :disabled="totalSelectedItems === 0"
            @click="handleCheckout"
        >
          结 算
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete } from '@element-plus/icons-vue';
import { getOrder } from "../api/order.js"; // 假设的API请求方法
import { getUrl } from "../utils/url.js"; // 处理图片路径的工具函数

export default {
  name: 'ShoppingCart',
  // data() 函数返回这个组件实例的“成员变量”
  data: function() {
    return {
      Delete: Delete,
      cartItems: [], // 购物车商品列表，初始为空，等待从服务器加载
      selectedItems: [], // 存储表格中被选中的行
      isSelectAll: false, // 全选复选框的状态
    };
  },

  // methods 对象里定义所有可以调用的“公共方法”
  methods: {
    /**
     * 根据图片名称获取完整的图片URL。
     * @param {string} imgName - 图片的文件名，例如 'product-a.png'
     */
    getImageFullUrl: function(imgName) {
      return getUrl(imgName);
    },

    /**
     * 当表格的选中项发生变化时，Element Plus UI库会自动调用这个方法
     * @param {Array} selection - 当前所有被选中的商品对象组成的数组
     */
    handleSelectionChange: function(selection) {
      this.selectedItems = selection;
      // 手动更新“全选”复选框的状态
      this.isSelectAll = this.cartItems.length > 0 && this.selectedItems.length === this.cartItems.length;
    },

    /**
     * 点击“全选”复选框时触发
     * @param {boolean} isSelected - 复选框是否被选中
     */
    handleSelectAll: function(isSelected) {
      if (this.$refs.tableRef) {
        if (isSelected) {
          this.$refs.tableRef.toggleAllSelection();
        } else {
          this.$refs.tableRef.clearSelection();
        }
      }
    },

    /**
     * 从购物车移除单个商品
     * @param {number} index - 要移除的商品在 cartItems 数组中的索引
     */
    removeItem: function(index) {
      this.cartItems.splice(index, 1);
      ElMessage.success('商品已从购物车移除');
    },

    /**
     * 移除所有选中的商品
     */
    removeSelectedItems: function() {
      if (this.selectedItems.length === 0) {
        ElMessage.warning('请先选择要删除的商品');
        return;
      }

      ElMessageBox.confirm('确定要删除选中的商品吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        // 用户点击“确定”后执行
        var newCartItems = [];
        for (var i = 0; i < this.cartItems.length; i++) {
          var item = this.cartItems[i];
          if (!this.selectedItems.includes(item)) {
            newCartItems.push(item);
          }
        }
        this.cartItems = newCartItems;
        ElMessage.success('已删除选中商品');
      }).catch(() => {
        // 用户点击“取消”
      });
    },

    /**
     * 点击结算按钮
     */
    handleCheckout: function() {
      if (this.selectedItems.length === 0) {
        ElMessage.warning('请选择要结算的商品');
        return;
      }
      console.log('准备结算的商品:', this.selectedItems);
      ElMessage.success('正在前往结算页面...');
    },

    /**
     * 跳转到商城页面
     */
    goToMall: function() {
      this.$router.push('/mall');
    },

    /**
     * 从服务器查询所有购物车商品数据 (使用Promise.then的写法)
     */
    queryAll: function() {
      var self = this; // 保存 this 的引用

      getOrder().then(function(response) {
        // API成功返回数据后
        var originalData = response.data.data;

        // 使用 for 循环处理数据，添加可直接显示的图片URL
        var processedItems = [];
        for (var i = 0; i < originalData.length; i++) {
          var item = originalData[i];
          // 添加一个新的 displayImgUrl 属性
          item.displayImgUrl = self.getImageFullUrl(item.imgUrl);
          processedItems.push(item);
        }

        self.cartItems = processedItems;

        // 数据加载完成后，默认全选所有商品
        self.$nextTick(function() {
          if (self.$refs.tableRef) {
            self.$refs.tableRef.toggleAllSelection();
          }
        });

      }).catch(function(error) {
        // API请求失败
        console.error("加载购物车数据失败:", error);
        ElMessage.error("加载购物车数据失败！");
      });
    }
  },

  /**
   * Vue的“生命周期钩子”，当组件挂载到页面上时，这个函数会自动执行
   */
  mounted: function() {
    this.queryAll();
  },

  /**
   * computed (计算属性), 可以理解为带缓存的get方法，性能更好
   */
  computed: {
    // 计算已选商品的总数
    totalSelectedItems() {
      return this.selectedItems.length;
    },
    // 计算已选商品的总价
    totalPrice() {
      var total = 0;
      for (var i = 0; i < this.selectedItems.length; i++) {
        var item = this.selectedItems[i];
        total += item.productPrice * item.buyNum;
      }
      return total;
    },
  }
}
</script>

<style scoped>
.shopping-cart-container {
  max-width: 1200px;
  margin: 20px auto;
}
.cart-card {
  border: none;
}
.card-header {
  display: flex;
  align-items: center;
  font-size: 18px;
  font-weight: 600;
}
.header-tip {
  font-size: 13px;
  color: #999;
  margin-left: 15px;
  font-weight: normal;
}

/* 商品信息列 */
.product-info {
  display: flex;
  align-items: center;
}
.product-image {
  width: 80px;
  height: 80px;
  margin-right: 15px;
  border-radius: 6px;
  flex-shrink: 0;
}
.product-details {
  display: flex;
  flex-direction: column;
}
.product-name {
  font-weight: 500;
  color: #333;
  margin-bottom: 5px;
}
.product-desc {
  font-size: 12px;
  color: #999;
}
.price {
  font-size: 16px;
  color: #666;
}
.price.subtotal {
  font-weight: bold;
  color: #333;
}

/* 结算栏 */
.checkout-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
  padding: 15px 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}
.left-section, .right-section {
  display: flex;
  align-items: center;
  gap: 20px;
}
.total-summary {
  display: flex;
  align-items: baseline;
  gap: 10px;
}
.total-label {
  font-size: 16px;
}
.total-price {
  font-size: 24px;
  font-weight: bold;
  color: #fb7299;
}
.highlight {
  color: #fb7299;
  font-size: 18px;
}
.checkout-btn {
  width: 180px;
  height: 50px;
  font-size: 18px;
  letter-spacing: 2px;
}
:deep(.el-table__header-wrapper th) {
  background-color: #f5f7fa;
  color: #666;
}
</style>