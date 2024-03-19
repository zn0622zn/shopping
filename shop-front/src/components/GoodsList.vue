<template>
  <div>
    <Search @onSearch="searchGoodList"></Search>
    <GoodsListNav></GoodsListNav>
    <div class="container">
      <div class="bread-crumb">
        <Breadcrumb>
          <BreadcrumbItem to="/">
            <Icon type="ios-home-outline"></Icon> 首页
          </BreadcrumbItem>
          <BreadcrumbItem to="/goodsList?sreachData=">
            <Icon type="bag"></Icon> {{searchKey}}
          </BreadcrumbItem>
          <Tag v-for="(value, name, index) in search.filter" :key="index" closable @on-close="closeTags(name)"><span
                  @click="selectTags(index)">{{name + ":" + value}}</span></Tag>

        </Breadcrumb>

      </div>

      <!--搜索过滤条件-->
      <div class="item-class-show">
        <Row class="item-class-group" v-for="(f, i) in filters" :key="i" v-if="i <= 5">
          <i-col class="item-class-name" span="3" v-if="f.k==='cid3'">
            分类
          </i-col>
          <i-col class="item-class-name" span="3" v-else-if="f.k==='brandId'">
            品牌
          </i-col>
          <i-col class="item-class-name" span="3" v-else>
            {{f.k}}
          </i-col>
          <i-col class="item-class-select" span="21" v-if="f.k!=='cid3'">
            <span v-for="(o, j) in f.options" :key="j" @click="selectFilter(f.k,o)">{{ o.name }}</span>
          </i-col>
          <i-col class="item-class-select" span="21" v-else>
            <span v-for="(o, j) in f.options" :key="j" @click="selectFilter(f.k,o)">{{ o.title }}</span>
          </i-col>
        </Row>

        <Row class="item-class-group" v-if="filters.length > 5">
          <i-col class="item-class-name" span="3">更多选项 : </i-col>
          <i-col class="item-class-select" span="21">
            <span v-bind:style="foldFilter.k == f.k ? 'color:red': 'color:black'" v-for="(f, i) in filters" :key="i" v-if="i > 5" @click="showFold(f)">{{ f.k }} <Icon type="ios-arrow-up" v-if="foldFilter.k != f.k" /> <Icon type="ios-arrow-down" v-if="foldFilter.k == f.k" /></span>
          </i-col>
        </Row>
        <Row >
          <i-col class="item-class-select" span="24">
            <span v-for="(o, j) in foldFilter.options" :key="j"  @click="selectFilter(foldFilter.k,o)" >{{ o.name }}</span>
          </i-col>
        </Row>
      </div>


      <!-- 商品展示容器 -->
      <div class="goods-box">
        <div class="as-box">
          <div class="item-as-title">
            <span>商品精选</span>
            <span>广告</span>
          </div>
          <div class="item-as" v-for="(item,index) in asItems" :key="index">
            <div class="item-as-img">
              <img :src="item.img" alt="">
            </div>
            <div class="item-as-price">
              <span>
                <Icon type="social-yen text-danger"></Icon>
                <span class="seckill-price text-danger">{{item.price}}</span>
              </span>
            </div>
            <div class="item-as-intro">
              <span>{{item.intro}}</span>
            </div>
            <div class="item-as-selled">
              已有<span>{{item.num}}</span>人评价
            </div>
          </div>
        </div>
        <div class="goods-list-box">
          <div class="goods-list-tool">
            <ul>
              <li v-for="(item,index) in goodsTool" :key="index" @click="orderBy(item.en, index)"><span :class="{ 'goods-list-tool-active': isAction[index]}">{{item.title}} <Icon :type="icon[index]"></Icon></span></li>
            </ul>
          </div>
          <div class="goods-list">
            <!--<div class="goods-show-info" v-for="(item, index) in orderGoodsList" :key="index">-->
            <div class="goods-show-info" v-for="(item, index) in goodsList" :key="index">
              <div class="goods-show-img">
                <router-link to="/goodsDetail"><img :src="item.selected.image" height="200"/></router-link>

                <ul class="skus">
                  <li :class="{selected : sku.id === item.selected.id}" v-for="(sku,i) in item.skus" :key="i"
                      @click="item.selected = sku">
                    <img :src="sku.image">
                  </li>
                </ul>


              </div>
              <div class="goods-show-price">
                <span>
                  <Icon type="social-yen text-danger"></Icon>
                  <span class="seckill-price text-danger">{{item.selected.price}}</span>
                </span>
              </div>
              <div class="goods-show-detail">
                <span>{{item.selected.title}}</span>
              </div>
              <div class="goods-show-num">
                已有<span>10</span>人评价
              </div>
              <div class="goods-show-seller">
                <span>自营</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="goods-page">
        <Page :total="total" :page-size="20" @on-change="changePage"></Page>
      </div>
    </div>
    <Spin size="large" fix v-if="isLoading"></Spin>
  </div>
</template>

<script>
import Search from '@/components/Search';
import GoodsListNav from '@/components/nav/GoodsListNav';
import GoodsClassNav from '@/components/nav/GoodsClassNav';
import store from '@/vuex/store';
import { mapState, mapActions, mapGetters, mapMutations } from 'vuex';

import instance from '@/libs/api/index'
import Qs from 'qs'

export default {
  name: 'GoodsList',
  beforeRouteEnter (to, from, next) {
    window.scrollTo(0, 0);
    next();
  },
  data () {
    return {
      isAction: [ true, false, false ],
      icon: [ 'arrow-up-a', 'arrow-down-a', 'arrow-down-a' ],
      goodsTool: [
        {title: '综合', en: 'sale'},
        {title: '评论数', en: 'remarks'},
        {title: '价格', en: 'price'}
      ],
      goodsList: [],
      filters:[],
      total:20,
      totalPage:1,
      foldFilter: {}, //折叠显示的过滤条件
      search: {
        key: "", // 搜索页面的关键字
        page:1,
        sortBy:"", //根据谁排序
        descending:false, //升序还是降序
        filter:{} //规律条件
      }
    };
  },
  computed: {
    ...mapState(['asItems', 'isLoading', 'searchKey']),
    ...mapGetters(['orderGoodsList'])
  },
  methods: {
    ...mapActions(['loadGoodsList']),
    ...mapMutations(['SET_GOODS_ORDER_BY']),
    orderBy (data, index) {
      this.search.sortBy = data
      this.search.descending = !this.search.descending
    },
    //搜索输入框搜索
    searchGoodList(data) {
      this.search.key = data
    },
    //搜索
    searchBy() {
      instance.post(`/search/query`, this.search, {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      }).then(response => {
        //初始化skus属性，并且让商品的默认选择选第0个
        response.data.items.forEach(goods => {
          //把之际取到的字符串转换成json
          goods.skus = JSON.parse(goods.skus).sort();
          //表示选中的sku。默认选中第0个
          goods.selected = goods.skus[0];
        });
        //从响应数据中获取总的条目数以及总页数
        this.total = response.data.total;
        this.totalPage = response.data.totalPage;

        this.filters = [];
        this.filters.push({
          k:"cid3",
          options:response.data.categories
        });

        this.filters.push({
          k:"brandId",
          options:response.data.brands
        });

        response.data.specs.forEach(spec=>{
          spec.options = spec.options.map(option=>({name:option}));
          this.filters.push(spec);
        });
        //当前页面上的所有的spu
        this.goodsList = response.data.items;
      }).catch(error => {
        console.log(error)
      })
    },
    showFold(f) {
      this.foldFilter = f;
    },
    selectFilter(k, o) {
      const obj = {};
      Object.assign(obj, this.search);
      if (k === 'cid3' || k === 'brandId') {
        o = o.id;
        obj.filter[k] = o;
        this.search = obj;
      } else {
        obj.filter[k] = o.name;
        this.search = obj;
      }
    },
    closeTags(name) {
      delete this.search.filter[name];
      this.$forceUpdate();
      this.searchBy();
    },
    changePage(index) {
      this.search.page = index;
    }
  },
  watch:{
    search:{
      deep:true,
      handler(val,old){
        if(!old || !old.key){
          // 如果旧的search值为空，或者search中的key为空，证明是第一次
          return;
        }
        this.searchBy();
      }
    }
  },
  created () {
    this.loadGoodsList();
  },
  components: {
    Search,
    GoodsListNav,
    GoodsClassNav
  },
  store
};
</script>

<style scoped>
.container {
  margin: 15px auto;
  width: 93%;
  min-width: 1000px;
}
.text-danger {
  color: #A94442;
}
.seckill-price{
  margin-right: 5px;
  font-size: 25px;
  font-weight: bold;
}
.goods-box {
  display: flex;
}
/* ---------------侧边广告栏开始------------------- */
.as-box {
  width: 200px;
  border: 1px solid #ccc;
}
.item-as-title{
  width: 100%;
  height: 36px;
  color: #B1191A;
  line-height: 36px;
  font-size: 18px;
}
.item-as-title span:first-child{
  margin-left: 20px;
}
.item-as-title span:last-child{
  float: right;
  margin-right: 15px;
  font-size: 10px;
  color: #ccc;
}
.item-as{
  width: 160px;
  margin: 18px auto;
}
.item-as-img{
  width: 160px;
  height: 160px;
  margin: 0px auto;
}
.item-as-price span{
  font-size: 18px;
}
.item-as-intro{
  margin-top: 5px;
  font-size: 12px;
}
.item-as-selled{
  margin-top: 5px;
  font-size: 12px;
}
.item-as-selled span{
  color: #005AA0;
}
/* ---------------侧边广告栏结束------------------- */

/* ---------------商品栏开始------------------- */
.goods-list-box {
  margin-left: 15px;
  width: calc(100% - 215px);
}
.goods-list-tool{
  width: 100%;
  height: 38px;
  border: 1px solid #ccc;
  background-color: #F1F1F1;
}
.goods-list-tool ul{
  padding-left: 15px;
  list-style: none;
}
.goods-list-tool li{
  cursor: pointer;
  float: left;
}
.goods-list-tool span{
  padding: 5px 8px;
  border: 1px solid #ccc;
  border-left: none;
  line-height: 36px;
  background-color: #fff;
}
.goods-list-tool span:hover{
  border: 1px solid #E4393C;
}
.goods-list-tool i:hover{
  color: #E4393C;
}
.goods-list-tool-active {
  color: #fff;
  border-left: 1px solid #ccc;
  background-color: #E4393C !important;
}

.goods-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}
.goods-show-info{
  width: 240px;
  padding: 10px;
  margin: 15px 0px;
  border: 1px solid #fff;
  cursor: pointer;
}
.goods-show-info:hover{
  border: 1px solid #ccc;
  box-shadow: 0px 0px 15px #ccc;
}
.goods-show-price{
  margin-top: 6px;
}
.goods-show-detail{
  font-size: 12px;
  margin: 6px 0px;
}
.goods-show-num{
  font-size: 12px;
  margin-bottom: 6px;
  color: #009688;
}
.goods-show-num span{
  color: #005AA0;
  font-weight: bold;
}
.goods-show-seller{
  font-size: 12px;
  color:#E4393C;
}
.goods-page {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
/* ---------------商品栏结束------------------- */
/* ---------------商品收缩导航------------------*/
.item-class-show {
  margin: 15px auto;
  width: 100%;
}
.item-class-group {
  margin-top: 1px;
  height: 45px;
  border-bottom: 1px solid #ccc;
}
.item-class-group:first-child {
  border-top: 1px solid #ccc;
}
.item-class-name {
  padding-left: 15px;
  line-height: 44px;
  color: #666;
  font-weight: bold;
  background-color: #f3f3f3;
}
.item-class-name:first-child {
  line-height: 43px;
}
.item-class-select span {
  margin-left: 15px;
  width: 160px;
  color: #005aa0;
  line-height: 45px;
  cursor: pointer;
}
.redCls {
  color: red;
}
/* ---------------------搜索导航结束-------------------- */

.skus {
  list-style: none;
}

.skus li {
  list-style: none;
  display: inline-block;
  float: left;
  margin-left: 2px;
  border: 2px solid #f3f3f3;
}

.skus li.selected {
  border: 2px solid #dd1144;
}

.skus img {
  width: 25px;
  height: 25px;
}


</style>
