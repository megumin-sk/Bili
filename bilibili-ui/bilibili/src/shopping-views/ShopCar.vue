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
              <el-image :src="row.imageUrl" fit="cover" class="product-image" />
              <div class="product-details">
                <span class="product-name">{{ row.name }}</span>
                <span class="product-desc">{{ row.description }}</span>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="单价" width="150" align="center">
          <template #default="{ row }">
            <span class="price">¥{{ row.price.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="数量" width="180" align="center">
          <template #default="{ row }">
            <el-input-number v-model="row.quantity" :min="1" :max="row.stock" size="small" />
          </template>
        </el-table-column>
        <el-table-column label="小计" width="150" align="center">
          <template #default="{ row }">
            <span class="price subtotal">¥{{ (row.price * row.quantity).toFixed(2) }}</span>
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
import jojo1 from '../static-resources/jojo-空条承太郎.png';
import jojo2 from '../static-resources/rock.png';

export default {
  name: 'ShoppingCart',
  data() {
    return {
      Delete: Delete, // 将图标暴露给模板
      cartItems: [
        { id: 1, name: 'B站小电视抱枕', description: '超柔短绒毛，陪伴你的每一个夜晚', price: 88.00, quantity: 1, stock: 10, imageUrl: jojo2 },
        { id: 2, name: '空条承太郎 精品手办', description: 'jojo官方正版，精品手办', price: 128.00, quantity: 2, stock: 5, imageUrl: jojo1 },
      ],
      selectedItems: [], // 存储表格中被选中的行
    };
  },
  computed: {
    // 计算已选商品的总数
    totalSelectedItems() {
      return this.selectedItems.length;
    },
    // 计算已选商品的总价
    totalPrice() {
      return this.selectedItems.reduce((sum, item) => {
        return sum + item.price * item.quantity;
      }, 0);
    },
    // 判断是否全选
    isSelectAll: {
      get() {
        return this.cartItems.length > 0 && this.selectedItems.length === this.cartItems.length;
      },
      set(value) {
        this.handleSelectAll(value);
      }
    }
  },
  methods: {
    // 表格选中项变化时触发
    handleSelectionChange(selection) {
      this.selectedItems = selection;
    },
    // 全选/取消全选
    handleSelectAll(isSelected) {
      if (isSelected) {
        this.$refs.tableRef.toggleAllSelection();
      } else {
        this.$refs.tableRef.clearSelection();
      }
    },
    // 移除单个商品
    removeItem(index) {
      this.cartItems.splice(index, 1);
      ElMessage.success('商品已从购物车移除');
    },
    // 移除所有选中的商品
    removeSelectedItems() {
      if (this.totalSelectedItems === 0) {
        ElMessage.warning('请先选择要删除的商品');
        return;
      }
      ElMessageBox.confirm('确定要删除选中的商品吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.cartItems = this.cartItems.filter(item => !this.selectedItems.includes(item));
        ElMessage.success('已删除选中商品');
      }).catch(() => { /* 用户取消操作 */ });
    },
    // 结算
    handleCheckout() {
      if (this.totalSelectedItems === 0) {
        ElMessage.warning('请选择要结算的商品');
        return;
      }
      // 在这里处理结算逻辑，例如跳转到订单确认页，并带上 selectedItems
      console.log('准备结算的商品:', this.selectedItems);
      ElMessage.success('正在前往结算页面...');
      // this.$router.push({ name: 'ConfirmOrder', state: { items: this.selectedItems } });
    },
    // 前往商城
    goToMall() {
      this.$router.push('/mall');
    }
  },
  mounted() {
    // 默认全选所有商品
    this.$nextTick(() => {
      if (this.$refs.tableRef) {
        this.$refs.tableRef.toggleAllSelection();
      }
    });
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