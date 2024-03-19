<template>
    <div>
        <div>
            <Form ref="formData" :model="formData" :label-width="80">
                <Row style="margin-top: 10px;">
                    <Col span="8">
                    <FormItem label="登录名称" prop="userName">
                        <Input v-model="formData.userName" placeholder="登录名称"></Input>
                    </FormItem>
                    </Col>
                    <Col span="8">
                    <FormItem label="真实姓名" prop="realName">
                        <Input v-model="formData.realName" placeholder="真实姓名"></Input>
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

        <Row>
            <Table stripe ref="selection" :columns="columns" :data="rows"></Table>
            <Page :total="total" :page-size="pageSize" show-sizer show-elevator show-total class="paging"
                  @on-change="changePage" @on-page-size-change="changePageSize"></Page>
        </Row>

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
import instance from '@/libs/api/index'

export default {
  mixins: [baseList],
  data () {
    return {
      formData: {
        userName: '',
        realName: ''
      },
      columns: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: '登录名称',
          key: 'userName'
        },
        {
          title: '真实姓名',
          key: 'realName'
        },
        {
          title: '部门',
          key: 'deptName'
        },
        {
          title: '岗位',
          key: 'postName'
        },
        {
          title: '负责人',
          key: 'principal',
          render: (h, params) => {
            if (params.row.principal) {
              return h('span', {
                props: {},
                style: {
                  color: 'red'
                }
              }, '是')
            } else {
              return h('span', {
                props: {},
                style: {
                  color: 'blue'
                }
              }, '否')
            }
          }

        },
        {
          title: '锁定',
          key: 'lock',
          align: 'center',
          render: (h, params) => {
            if (params.row.lock) {
              return h('div', [
                h('Icon', {
                  props: {
                    type: 'ios-lock-outline',
                    size: 18,
                    color: 'red'
                  },
                  style: {
                    cursor: 'pointer'
                  },
                  on: {
                    click: () => {
                      this.lock(params.row.id)
                    }
                  }
                })
              ])
            } else {
              return h('div', [
                h('Icon', {
                  props: {
                    type: 'ios-unlock-outline',
                    size: 18,
                    color: 'green'
                  },
                  style: {
                    cursor: 'pointer'
                  },
                  on: {
                    click: () => {
                      this.lock(params.row.id)
                    }
                  }
                })
              ])
            }
          }
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
              }, '删除')
            ])
          }
        }
      ]
    }
  },
  methods: {
    // 锁定（解锁）账号
    lock (id) {
      instance.get(`/${this.namespace}/${this.entityName}/lock/` + id).then(response => {
        this.$Message.success(response.data.msg)
        this.query()
      }).catch(error => {
        console.log(error)
      })
    }
  }
}
</script>
