<template>
  <div class="shopping-cart-container">

    <el-card shadow="never" class="main-card">

      <div class="section-title">收货地址</div>
      <div v-if="addresses.length > 0" class="address-list">
        <div
            v-for="(address, index) in addresses"
            :key="address.addressId"
            class="address-item"
            :class="{ 'selected': selectedAddressIndex === index }"
            @click="selectAddress(index)"
        >
          <div class="address-details">
            <div class="address-line-one">
              <span class="address-nickname">
                <el-icon><User /></el-icon> {{ address.nickName }}
              </span>
              <el-tag effect="plain" size="small">{{ address.comment }}</el-tag>
            </div>
            <div class="address-line-two">
              <el-icon><Location /></el-icon>
              <span class="address-name">{{ address.addressName }}</span>
            </div>
          </div>

          <div class="address-actions">
            <el-button type="primary" link @click.stop="editAddress(address)">编辑</el-button>
            <el-button type="danger" link @click.stop="deleteAddress(index)">删除</el-button>
          </div>

          <div v-if="selectedAddressIndex === index" class="selected-corner-badge"></div>
        </div>
      </div>
      <el-empty v-else description="还没有收货地址">
        <el-button type="primary" color="#fb7299" @click="addNewAddress">立即添加</el-button>
      </el-empty>

      <el-divider />

      <div class="section-title">商品列表</div>
      <el-table
          v-if="cartItems.length > 0"
          :data="cartItems"
          ref="tableRef"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column label="商品信息" min-width="300">
          <template #default="{ row }">
            <div class="product-info">
              <el-image :src="row.displayImgUrl" fit="cover" class="product-image" />
              <div class="product-details">
                <span class="product-name">{{ row.productName }}</span>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="单价" width="150" align="center">
          <template #default="{ row }">
            <span class="price">¥{{ row.unitPrice.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="数量" width="180" align="center">
          <template #default="{ row }">
            <el-input-number v-model="row.quantity" :min="1" size="small" />
          </template>
        </el-table-column>
        <el-table-column label="小计" width="150" align="center">
          <template #default="{ row }">
            <span class="price subtotal">¥{{ (row.unitPrice * row.quantity).toFixed(2) }}</span>
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
          <span>已选 <strong class="highlight">{{ totalSelectedItems }}</strong> 件</span>
          <span class="total-label">合计：</span>
          <span class="total-price">¥{{ totalPrice.toFixed(2) }}</span>
        </div>
        <el-button
            type="primary"
            color="#fb7299"
            size="large"
            class="checkout-btn"
            :disabled="totalSelectedItems === 0 || selectedAddressIndex === -1"
            @click="handleCheckout"
        >
          结 算
        </el-button>
      </div>
    </div>

  </div>
</template>

<script>
// 从 element-plus 库导入需要的组件和图标
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Select, User, Location } from '@element-plus/icons-vue';

// 导入我们自己写的API请求函数
import {getCartItems, queryAddress} from "../api/cart.js";
import { getUrl } from "../utils/url.js";

// Vue组件的核心：export default
export default {
  name: 'ShoppingCart',
  components: {
    // 注册图标，以便在<template>中使用
    Select,
    User,
    Location,
  },
  /**
   * data: 用来存放组件内部需要用到的所有“变量”。
   * 对于Java开发者来说，可以理解为这个组件类的“成员变量”。
   */
  data() {
    return {
      // 把图标组件保存到data中，方便在<template>里使用
      Delete: Delete,

      // 购物车商品列表，初始是个空数组，等待从后端API加载
      cartItems: [],
      // 当前表格中被勾选的商品
      selectedItems: [],
      // "全选"复选框的状态 (true / false)
      isSelectAll: false,

      // 地址列表，同样等待从后端加载
      addresses: [],
      // 当前选中的是第几个地址 (我们用数组的索引来表示)
      // -1 表示还没选择任何地址
      selectedAddressIndex: -1,
    };
  },

  /**
   * methods: 用来存放所有可以被调用的“方法”。
   * 对应Java类里的“公共方法”。
   * 这里主要放事件处理函数，比如点击按钮、调用API等。
   */
  methods: {
    // 根据图片名字，拼接出完整的图片URL
    getImageFullUrl(imgName) {
      return getUrl(imgName);
    },

    // --- 地址相关方法 ---
    // 加载用户的所有收货地址
    async loadAddresses() {
      // TODO: 这里将来要替换成真实的API调用
      // 现在先用假数据模拟，这些字段名要和你的后端数据库/实体类对应
      let {data:data} = await queryAddress()
      console.log(data)
      this.addresses = data.data
      // 如果地址列表不为空，默认选中第一个地址
      if (this.addresses.length > 0) {
        this.selectedAddressIndex = 0;
      }
    },
    // 当用户点击某个地址时，记录下它的索引
    selectAddress(index) {
      this.selectedAddressIndex = index;
    },
    // 添加新地址（功能暂未实现）
    addNewAddress() {
      ElMessage.info('添加新地址的功能正在开发中...');
      // 真实场景：这里会弹出一个对话框，填写表单，然后调用后端API保存
    },
    // 编辑地址（功能暂未实现）
    editAddress(address) {
      ElMessage.info('编辑地址功能正在开发中...');
    },
    // 删除地址（功能暂未实现）
    deleteAddress(index) {
      ElMessage.info('删除地址功能正在开发中...');
      // 实际开发中会弹出确认框，然后调用API
    },


    // --- 购物车相关方法 ---
    // 从后端API获取购物车里的所有商品
    queryAllCartItems() {
      getCartItems().then(response => {
        // 请求成功后，拿到后端返回的数据
        const originalData = response.data.data;

        // 遍历后端返回的数据，进行一点处理
        const processedItems = originalData.map(item => {
          // 为每个商品对象添加一个可以直接显示的图片URL
          item.displayImgUrl = this.getImageFullUrl(item.imgUrl);
          return item;
        });

        // 把处理好的数据赋值给我们data里的cartItems
        this.cartItems = processedItems;

        // 小技巧：等页面数据更新渲染完成后，再执行某些操作
        this.$nextTick(() => {
          // 默认让所有商品都处于被勾选的状态
          if (this.$refs.tableRef) {
            this.$refs.tableRef.toggleAllSelection();
          }
        });

      }).catch(error => {
        // 如果API请求失败，打印错误并提示用户
        console.error("加载购物车数据失败:", error);
        ElMessage.error("加载购物车数据失败！");
      });
    },
    // 当表格的勾选状态变化时，这个函数会被自动调用
    handleSelectionChange(selection) {
      this.selectedItems = selection;
      // 检查是否所有商品都被勾选了，来决定"全选"框的状态
      this.isSelectAll = this.cartItems.length > 0 && this.selectedItems.length === this.cartItems.length;
    },
    // 点击"全选"复选框时触发
    handleSelectAll(isSelected) {
      // isSelected 是 Element Plus 传给我们的，表示"全选"框当前是选中还是未选中
      if (isSelected) {
        this.$refs.tableRef.toggleAllSelection();
      } else {
        this.$refs.tableRef.clearSelection();
      }
    },
    // 移除单个商品
    removeItem(index) {
      // 直接从 cartItems 数组中删除指定索引的元素
      this.cartItems.splice(index, 1);
      ElMessage.success('商品已从购物车移除');
    },
    // 移除所有选中的商品
    removeSelectedItems() {
      if (this.selectedItems.length === 0) {
        ElMessage.warning('请先选择要删除的商品');
        return;
      }

      ElMessageBox.confirm('确定要删除选中的商品吗？', '提示', {
        type: 'warning',
      }).then(() => {
        // 使用 filter 方法筛选出未被选中的商品，代码更简洁
        this.cartItems = this.cartItems.filter(item => !this.selectedItems.includes(item));
        ElMessage.success('已删除选中商品');
      }).catch(() => { /* 用户点击取消，什么也不做 */ });
    },
    // 点击结算按钮
    handleCheckout() {
      // 结算前的检查
      if (this.selectedAddressIndex === -1) {
        ElMessage.warning('请选择一个收货地址');
        return;
      }
      if (this.selectedItems.length === 0) {
        ElMessage.warning('请选择要结算的商品');
        return;
      }

      // 准备要提交给后端的数据
      const checkoutData = {
        selectedProducts: this.selectedItems,
        shippingAddress: this.addresses[this.selectedAddressIndex]
      };

      console.log('准备提交给后端的数据:', checkoutData);
      ElMessage.success('正在处理订单...');
      // TODO: 在这里调用创建订单的后端API
    },
    // 跳转到商城页面
    goToMall() {
      this.$router.push('/mall');
    },
  },

  /**
   * mounted: Vue的“生命周期钩子”。
   * 当这个组件第一次被创建并显示在页面上时，这里的代码会自动执行一次。
   * 非常适合用来在页面加载时就去请求初始数据。
   */
  mounted() {
    this.loadAddresses(); // 加载地址
    this.queryAllCartItems(); // 加载购物车
  },

  /**
   * computed: 计算属性。
   * 可以理解为是带缓存的“智能”的data属性。
   * 当它依赖的数据变化时，它会自动重新计算，否则就直接用上次缓存的结果，性能很好。
   * 非常适合用来处理那些依赖于其他数据的值，比如总价。
   */
  computed: {
    // 计算已选商品的总数
    totalSelectedItems() {
      return this.selectedItems.length;
    },
    // 计算已选商品的总价
    totalPrice() {
      // 使用 reduce 方法，这是更现代、更简洁的数组求和方式
      return this.selectedItems.reduce((total, item) => {
        return total + item.unitPrice * item.quantity;
      }, 0); // 0是初始值
    },
  }
}
</script>

<style scoped>
.shopping-cart-container {
  max-width: 1200px;
  margin: 20px auto;
}
.main-card {
  border: none;
  padding: 10px;
}
.section-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 15px;
  color: #333;
}

/* 地址区域样式 */
.address-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 15px;
}
.address-item {
  border: 1px solid #e9e9eb;
  padding: 15px 20px;
  border-radius: 8px;
  cursor: pointer;
  position: relative;
  transition: all 0.2s ease-in-out;
  overflow: hidden;
  background-color: #fafafa;
}
.address-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
}
.address-item.selected {
  border-color: #fb7299;
  background-color: #fff5f7;
}
.address-details {
  display: flex;
  flex-direction: column;
  gap: 12px;
}
.address-line-one, .address-line-two {
  display: flex;
  align-items: center;
  gap: 8px;
}
.address-nickname {
  font-weight: 600;
  font-size: 15px;
  color: #303133;
}
.address-name {
  font-size: 14px;
  color: #606266;
  line-height: 1.4;
}
.el-icon {
  font-size: 16px;
  color: #909399;
}
.address-actions {
  position: absolute;
  top: 8px;
  right: 8px;
  display: none;
}
.address-item:hover .address-actions {
  display: block;
}
.selected-corner-badge {
  position: absolute;
  top: -17px;
  right: -17px;
  width: 34px;
  height: 34px;
  background-color: #fb7299;
  transform: rotate(45deg);
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
}
.product-details {
  display: flex;
  flex-direction: column;
}
.product-name {
  font-weight: 500;
  color: #333;
}
.price {
  font-size: 16px;
  color: #666;
}
.price.subtotal {
  font-weight: bold;
  color: #333;
}

/* 底部结算栏 */
.checkout-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
  padding: 15px 25px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
  position: sticky;
  bottom: 20px;
}
.left-section, .right-section {
  display: flex;
  align-items: center;
  gap: 20px;
}
.total-summary {
  display: flex;
  align-items: baseline;
  gap: 8px;
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
  width: 150px;
  height: 45px;
  font-size: 16px;
}

/* 美化表格头部 */
:deep(.el-table__header-wrapper th) {
  background-color: #f5f7fa !important;
  color: #666;
}
</style>