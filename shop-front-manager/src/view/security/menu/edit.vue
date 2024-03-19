<template>

  <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="80">
    <input type="hidden" v-model="formData.id"/>
    <input type="hidden" v-model="formData.parentId"/>

    <Row>
      <Col span="12">
        <FormItem label="名称" prop="name">
          <Input v-model="formData.name"></Input>
        </FormItem>
      </Col>
      <Col span="12">
        <FormItem label="排序" prop="order">
          <Input v-model="formData.order"></Input>
        </FormItem>
      </Col>
    </Row>
    <Row>
      <Col span="12">
        <FormItem label="标题" prop="title">
          <Input v-model="formData.title"></Input>
        </FormItem>
      </Col>
      <Col span="12">
        <FormItem label="路径" prop="path">
          <Input v-model="formData.path"></Input>
        </FormItem>
      </Col>
    </Row>

    <Row>
      <Col span="12">
        <FormItem label="图标" prop="icon">
          <Input v-model="formData.icon"></Input>
        </FormItem>
      </Col>
      <Col span="4">
        <FormItem label="是否展开" prop="expand">
          <Checkbox v-model="formData.expand"></Checkbox>
        </FormItem>
      </Col>
      <Col span="4">
        <FormItem label="关闭缓存" prop="notCache">
          <Checkbox v-model="formData.notCache"></Checkbox>
        </FormItem>
      </Col>
      <Col span="4">
        <FormItem label="隐藏菜单" prop="hideInMenu">
          <Checkbox v-model="formData.hideInMenu"></Checkbox>
        </FormItem>
      </Col>
    </Row>

    <FormItem label="组件" prop="component">
      <Input v-model="formData.component"></Input>
    </FormItem>

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
        expand: false,
        path: '',
        name: '',
        component: '',
        hideInMenu: false,
        notCache: false,
        icon: ''
      },
      ruleValidate: {
        name: [
          {required: true, message: '名称不能为空', trigger: 'blur'}
        ]
      }
    }
  }
}
</script>
