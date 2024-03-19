import Qs from 'qs'
import Vue from 'vue'
import ZkTable from 'vue-table-with-tree-grid'
import { listToTree } from '@/libs/util'
import instance from '@/libs/api/index'


import {baseList} from './base-list'
Vue.use(ZkTable)

export const baseTreeList = {
  mixins: [baseList],
  // 表格各行的数据
  data () {
    return {
      props: {
        stripe: true, // 是否显示间隔斑马纹
        border: true, // 是否显示纵向边框
        showHeader: true, // 是否显示表头
        showSummary: false, // 是否显示表尾合计行
        showRowHover: true, // 鼠标悬停时，是否高亮当前行
        showIndex: false, // 是否显示数据索引
        treeType: true, // 是否为树形表格
        isFold: true, // 树形表格中父级是否默认折叠
        expandType: false, // 是否为展开行类型表格（为 True 时，需要添加作用域插槽, 它可以获取到 row, rowIndex)
        selectionType: false// 是否显示间隔斑马纹
      },
      // 初始化信息总条数
      total: 0,
      // 每页显示多少条
      pageSize: 10,
      // 显示的数据
      rows: []
    }
  },
  methods: {
    // 查询
    query () {
      instance.post(`/${this.namespace}/${this.entityName}/list`, Qs.stringify(this.formData)).then(response => {
        this.rows = listToTree(response.data)
      }).catch(error => {
        console.log(error)
      })
    },
    addRoot () {
      this.$router.push({
        name: `edit_${this.namespace}_${this.entityName}`
      })
    },
    // 添加
    addChild (id) {
      this.$router.push({
        name: `edit_${this.namespace}_${this.entityName}`,
        query: {parentId: id}
      })
    }
  }
}
