<template>
    <div>
        <div>
            <Form ref="formData" :model="formData" :label-width="80">
                <Row style="margin-top: 10px;">
                    <Col span="8">
                    <FormItem label="名称" prop="name">
                        <Input v-model="formData.name" placeholder="名称"></Input>
                    </FormItem>
                    </Col>
                    <Col span="8">
                    <FormItem label="描述" prop="title">
                        <Input v-model="formData.title" placeholder="描述"></Input>
                    </FormItem>
                    </Col>
                    <Col span="8">
                        <Divider type="vertical" />
                        <Button type="primary" @click="add">添加</Button>
                        <Button type="primary" @click="removeBatch">删除</Button>
                        <Button type="primary" @click="query">查询</Button>
                    </Col>
                </Row>
            </Form>
        </div>

        <div>
            <Table stripe ref="selection" :columns="columns" :data="rows"></Table>
            <Page :total="total" :page-size="pageSize" show-sizer show-elevator show-total class="paging"
                  @on-change="changePage" @on-page-size-change="changePageSize"></Page>
        </div>

    </div>
</template>
<style scoped>
    .paging {
        float: right;
        margin-top: 10px;
    }
</style>
<script>
import {baseList} from '@/libs/crud/base-list'
import selectMenu from '_c/select/selectMenu.vue'
import instance from '@/libs/api/index'

export default {
  mixins: [baseList],
  data () {
    return {
      formData: {
        name: '',
        title: '',
        checkedMenuList:[]
      },
      columns: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: '名称',
          key: 'name'
        },
        {
          title: '描述',
          key: 'title'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '2px'
                },
                on: {
                  click: () => {
                    this.edit(params.row.id)
                  }
                }
              }, '修改'),
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '2px'
                },
                on: {
                  click: () => {
                    this.remove(params.row.id, params.index)
                  }
                }
              }, '删除'),
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '2px'
                },
                on: {
                  click: () => {
                    this.openModal(params.row.id)
                  }
                }
              }, '菜单')
            ])
          }
        }
      ]
    }
  },
  methods : {
    openModal: function (roleId) {
      this.$Modal.confirm({
        scrollable: true,
        render: (h) => {
          return h(selectMenu, {
            props: {
              roleId : roleId
            },
            on: {
              onCheckMenu: (list) => {
                this.checkedMenuList = list;
              }
            }
          })
        },
        onOk: () => {
          let params = new URLSearchParams()
          this.checkedMenuList.forEach((o) => {
            params.append('ids', o.id)
          })
          instance.post(`/security/menu/assign/${roleId}`, params).then(response => {
            this.$Message.info('菜单分配成功')
          })
        }
      })
    }
  }
}
</script>
