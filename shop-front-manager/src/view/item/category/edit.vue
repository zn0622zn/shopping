<template>

    <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="80">
        <input type="hidden" v-model="formData.id"/>
        <input type="hidden" v-model="formData.parentId"/>

        <FormItem label="名称" prop="title">
            <Input v-model="formData.title"></Input>
        </FormItem>
        <Row>
            <Col span="12">
                <FormItem label="排序" prop="order">
                    <Input v-model="formData.order"></Input>
                </FormItem>
            </Col>
            <Col span="12">
            <FormItem label="是否展开" prop="expand">
                <Checkbox v-model="formData.expand"></Checkbox>
            </FormItem>
            </Col>
        </Row>

        <FormItem>
            <Button type="primary" @click="handleSubmit('form')">保存</Button>
            <Button type="primary" @click="go2list()" style="margin-left: 8px">关闭</Button>
        </FormItem>

    </Form>

</template>

<script>

import {baseTreeEdit} from '@/libs/crud/base-tree-edit'

export default {
  mixins: [baseTreeEdit],
  data () {
    return {
      formData: {
        id: '',
        parentId: '',
        title: '',
        order: '',
        expand: false
      },
      ruleValidate: {
        title: [
          {required: true, message: '名称不能为空', trigger: 'blur'}
        ]
      }
    }
  }
}
</script>
