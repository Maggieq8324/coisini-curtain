// components/realm/index.js
// components/spu-preview/index.js
import {FenceGroup} from "../models/fence-group";
import {Judger} from "../models/judger";
import {Spu} from "../../models/spu";
import {Cell} from "../models/cell";
import {Cart} from "../../models/cart";

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    spu: Object,
    orderWay: String
  },
  /**
   * 组件的初始数据
   */
  data: {
    judger: Object,
    previewImg: String,
    currentSkuCount: Cart.SKU_MIN_COUNT
  },
  /**
   * 监听器
   */
  observers: {
    'spu': function (spu) {
      if (!spu) {
        return
      }

      if(Spu.isNoSpec(spu)){
        this.processNoSpec(spu)
      } else{
        this.processHasSpec(spu)
      }

      this.triggerSpecEvent();
    }
  },
  /**
   * 组件的方法列表
   */
  methods: {
    /**
     * 无规格spu处理
     * @param spu
     */
    processNoSpec(spu) {
      this.setData({
        noSpec: true,
      })
      this.bindSkuData(spu.sku_list[0])
      this.setStockStatus(spu.sku_list[0].stock, this.data.currentSkuCount)
    },
    /**
     * 有规格spu处理
     * @param spu
     */
    processHasSpec(spu) {
      const fenceGroup = new FenceGroup(spu);
      fenceGroup.initFences(); // 矩阵转置初始化
      // fencesGroup.initFences1(); // 循环获取矩阵元素初始化

      const judger = new Judger(fenceGroup)
      this.data.judger = judger;

      const defaultSku = fenceGroup.getDefaultSku();
      if (defaultSku) {
        // 无默认sku则绑定spu数据
        this.bindSkuData(defaultSku);
        this.setStockStatus(defaultSku.stock, this.data.currentSkuCount)
      } else {
        this.bindSpuData();
      }

      // 绑定公共数据
      this.bindTipData();
      // 绑定栅栏分组数据
      this.bindFenceGroupData(fenceGroup);
    },
    /**
     *  已选择sku规格回传detail页面
     */
    triggerSpecEvent() {
      const noSpec = Spu.isNoSpec(this.properties.spu)
      if(noSpec){
        this.triggerEvent('specchange',{
          noSpec
        })
      } else{
        this.triggerEvent('specchange',{
          noSpec:Spu.isNoSpec(this.properties.spu),
          skuIntact:this.data.judger.isSkuIntact(),
          currentValues:this.data.judger.getCurrentValues(),
          missingKeys:this.data.judger.getMissingKeys()
        })
      }
    },
    /**
     * 绑定spu数据
     */
    bindSpuData() {
      const spu = this.properties.spu
      this.setData({
        previewImg:spu.img,
        title:spu.title,
        price:spu.price,
        discountPrice:spu.discount_price,
      })
    },
    /**
     * 绑定sku数据
     * @param sku
     */
    bindSkuData(sku) {
      this.setData({
        previewImg:sku.img,
        title:sku.title,
        price:sku.price,
        discountPrice:sku.discount_price,
        stock:sku.stock //库存
      })
    },
    /**
     * 共同数据绑定
     */
    bindTipData() {
      this.setData({
        skuIntact:this.data.judger.isSkuIntact(), // 是否选择完整sku
        currentValues:this.data.judger.getCurrentValues(),
        missingKeys:this.data.judger.getMissingKeys()
      })
    },
    /**
     * 设置库存状态
     * @param stock
     * @param currentCount
     */
    setStockStatus(stock, currentCount) {
      this.setData({
        outStock:this.isOutOfStock(stock, currentCount)
      })
    },
    /**
     * 是否缺货
     * @param stock 库存
     * @param currentCount 选择数量
     * @returns {boolean}
     */
    isOutOfStock(stock, currentCount) {
      return stock < currentCount;
    },
    /**
     * 有无规格判断
     * @returns {boolean}
     */
    noSpec() {
      const spu = this.properties.spu
      return Spu.isNoSpec(spu)
    },
    /**
     * 数量选择器回调事件
     * @param event
     */
    onSelectCount(event) {
      const currentCount = event.detail.count;
      this.data.currentSkuCount = currentCount;

      // console.log("judger", this.data.judger)

      if (this.noSpec()) {
        this.setStockStatus(this.getNoSpecSku().stock, currentCount)
      } else {
        if (this.data.judger.isSkuIntact()) {
          const sku = this.data.judger.getDeterminateSku()
          this.setStockStatus(sku.stock, currentCount)
        }
      }
    },
    /**
     * 绑定fenceGroup数据
     * @param fenceGroup
     */
    bindFenceGroupData(fenceGroup) {
      this.setData({
        fences:fenceGroup.fences,
      })
    },
    /**
     * Cell组件celltap事件的处理函数
     */
    onCellTap(event) {
      const data = event.detail.cell;
      const x = event.detail.x
      const y = event.detail.y

      const cell = new Cell(data.spec)
      cell.status = data.status

      const judger = this.data.judger
      judger.judge(cell, x, y);
      const skuIntact = judger.isSkuIntact();
      if(skuIntact){
        const currentSku = judger.getDeterminateSku();
        this.bindSkuData(currentSku)
        this.setStockStatus(currentSku.stock, this.data.currentSkuCount);
      }

      this.bindTipData();
      this.bindFenceGroupData(judger.fenceGroup);
      this.triggerSpecEvent()
    },
    /**
     * 立即购买或加入购物车
     */
    onBuyOrCart(event) {
      // 无规格
      if (Spu.isNoSpec(this.properties.spu)) {
        this.shoppingNoSpec();
      } else {
        // 有规格
        this.shoppingVarious();
      }
    },
    /**
     * 购买有规格商品
     */
    shoppingVarious() {
      // 是否是完整的sku
      const intact = this.data.judger.isSkuIntact();
      if (!intact) {
        const missKeys = this.data.judger.getMissingKeys();
        wx.showToast({
          icon: "none",
          title: `请选择：${missKeys.join('，')}`,
          duration: 3000
        });
        return;
      }
      this._triggerShoppingEvent(this.data.judger.getDeterminateSku());
    },
    /**
     * 购买无规格商品
     */
    shoppingNoSpec() {
      this._triggerShoppingEvent(this.getNoSpecSku());
    },
    /**
     * 获取无规格的sku
     * @returns {*}
     */
    getNoSpecSku() {
      return this.properties.spu.sku_list[0];
    },
    /**
     * 向上抛出
     * @param sku
     * @private
     */
    _triggerShoppingEvent(sku) {
      // 抛出事件
      this.triggerEvent("shopping", {
        orderWay: this.properties.orderWay,
        spuId: this.properties.spu.id,
        sku: sku,
        skuCount: this.data.currentSkuCount
      })
    }
  }
})
