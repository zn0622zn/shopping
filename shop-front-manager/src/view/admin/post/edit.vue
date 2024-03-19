
<template>

    <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="80">
        <input type="hidden" v-model="formData.id"/>

        <FormItem label="名称" prop="name">
            <Input v-model="formData.name"></Input>
        </FormItem>
        <FormItem label="描述" prop="title">
            <Input v-model="formData.title"></Input>
        </FormItem>

        <FormItem label="备注" prop="desc">
            <Input type="textarea" :rows="10" v-model="formData.desc" ></Input>
        </FormItem>

        <FormItem>
            <Button type="primary" @click="handleSubmit('form')">保存</Button>
            <Button type="primary" @click="go2list()" style="margin-left: 8px">关闭</Button>
        </FormItem>

    </Form>

</template>

<script>
import {baseEdit} from '@/libs/crud/base-edit'

export default {
  mixins: [baseEdit],
  data () {
    return {
      formData: {
        id: '',
        name: '',
        title: '',
        desc: ''
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
