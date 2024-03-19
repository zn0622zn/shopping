import Qs from 'qs'
import { listToTree } from '@/libs/util'
import instance from '@/libs/api/index'


import {baseList} from './base-list'

export const baseTree = {
  mixins: [baseList],
  // 表格各行的数据
  data () {
    return {
      // 初始化信息总条数
      total: 0,
      // 每页显示多少条
      pageSize: 10,
      // 显示的数据
      rows: []
    }
  },
  methods: {
    renderContent (h, { root, node, data }) {
      return h('span', {
        style: {
          display: 'inline-block',
          width: '100%'
        }
      }, [
        h('span', [
          h('Icon', {
            props: {
              type: 'ios-paper-outline'
            },
            style: {
              marginRight: '8px'
            }
          }),
          h('span', data.title)
        ]),
        h('span', {
          style: {
            display: 'inline-block',
            float: 'right',
            marginRight: '32px'
          }
        }, [
          h('Button', {
            props: Object.assign({}, this.buttonProps, {
              icon: 'md-add'
            }),
            style: {
              marginRight: '8px'
            },
            on: {
              click: () => { this.addChild(data.id) }
            }
          }),
          h('Button', {
            props: Object.assign({}, this.buttonProps, {
              icon: 'md-copy'
            }),
            style: {
              marginRight: '8px'
            },
            on: {
              click: () => { this.edit(data.id) }
            }
          }),
          h('Button', {
            props: Object.assign({}, this.buttonProps, {
              icon: 'md-remove'
            }),
            on: {
              click: () => { this.remove(data.id) }
            }
          })
        ])
      ]);
    },
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
