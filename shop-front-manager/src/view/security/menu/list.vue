<template>
    <div>
        <div>
            <Form ref="form" :model="formData" :label-width="80">
                <Row style="margin-top: 10px;">
                    <Col span="8">
                    <FormItem label="标题" prop="title">
                        <Input v-model="formData.title" placeholder="名称"></Input>
                    </FormItem>
                    </Col>
                    <Col span="8">
                    <FormItem label="名称" prop="name">
                        <Input v-model="formData.name" placeholder="名称"></Input>
                    </FormItem>
                    </Col>
                    <Col span="8">
                        <Divider type="vertical" />
                        <Button type="primary" @click="addRoot">添加</Button>
                        <Button type="primary" @click="query" style="margin-left: 8px">查询</Button>
                    </Col>

                </Row>
            </Form>
        </div>

        <div>
            <zk-table
                    ref="table"
                    sum-text="sum"
                    index-text="#"
                    :data="rows"
                    :columns="columns"
                    :stripe="props.stripe"
                    :border="props.border"
                    :show-header="props.showHeader"
                    :show-summary="props.showSummary"
                    :show-row-hover="props.showRowHover"
                    :show-index="props.showIndex"
                    :tree-type="props.treeType"
                    :is-fold="props.isFold"
                    :expand-type="props.expandType"
                    :selection-type="props.selectionType">
                <!-- 原文中 scope="scope" 会警告， 2.5版本后应为slot-scope="scope"-->
                <template slot="action" slot-scope="scope">
                    <Button type="primary" size="small" @click="addChild(scope.row.id)">添加</Button>
                    <Button type="primary" size="small" @click="edit(scope.row.id)">修改</Button>
                    <Button type="primary" size="small" @click="remove(scope.row.id)">删除</Button>
                </template>
            </zk-table>

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
import {baseTreeList} from '@/libs/crud/base-tree-list'

export default {
  mixins: [baseTreeList],
  // 表格各行的数据
  data () {
    return {
      formData: {
        title: '',
        name: ''
      },
      // 表格标题数据
      columns: [
        {
          label: '标题',
          prop: 'title'
        },
        {
          label: '名称',
          prop: 'name'
        },
        {
          label: '路径',
          prop: 'path'
        },
        {
          label: '操作',
          prop: 'action',
          type: 'template',
          template: 'action'
        }
      ]
    }
  }
}
</script>
