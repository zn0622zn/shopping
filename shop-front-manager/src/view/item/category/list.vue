<template>
    <div>
        <div>
            <Form ref="formData" :model="formData" :label-width="80">
                <Row style="margin-top: 10px;">
                    <Col span="16">
                    <FormItem label="名称" prop="title">
                        <Input v-model="formData.title" placeholder="名称"></Input>
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
            <Tree :data="rows" :render="renderContent"></Tree>
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
import {baseTree} from '@/libs/crud/base-tree'

export default {
  mixins: [baseTree],
  // 表格各行的数据
  data () {
    return {
      formData: {
        title: ''
      }
    }
  }
}
</script>
