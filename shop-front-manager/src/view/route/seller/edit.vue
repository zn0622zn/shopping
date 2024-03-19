<template>

    <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="80">
        <input type="hidden" v-model="formData.id"/>

        <FormItem label="名称" prop="sname">
            <Input v-model="formData.sname"></Input>
        </FormItem>
        <FormItem label="电话" prop="consphone">
            <Input v-model="formData.consphone"></Input>
        </FormItem>
        <FormItem label="住址" prop="address">
            <Input v-model="formData.address"></Input>
        </FormItem>

        <FormItem>
            <Button type="primary" @click="handleSubmit('form')">保存</Button>
            <Button type="primary" @click="go2list()" style="margin-left: 8px">关闭</Button>
        </FormItem>

    </Form>

</template>

<script>
import {baseEdit} from '@/libs/crud/base-edit'
import {validateTel} from '@/libs/validate/base-rule.js'


export default {
  mixins: [baseEdit],
  data () {
    return {
      formData: {
          id: '',
          sname: '',
          consphone: '',
          address: ''
      },
      ruleValidate: {
        sname: [
          {required: true, message: '名称不能为空', trigger: 'blur'}
        ],
        consphone: [
          {required: true, message: '标签不能为空', trigger: 'blur'},
          {validator: validateTel, trigger: 'blur' }
        ]
      }
    }
  }
}
</script>
