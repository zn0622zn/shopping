<template>

    <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="80">
        <input type="hidden" v-model="formData.id"/>

        <FormItem label="键值" prop="value">
            <Input v-model="formData.value"></Input>
        </FormItem>
        <FormItem label="标签" prop="label">
            <Input v-model="formData.label"></Input>
        </FormItem>
        <FormItem label="类型" prop="type">
            <Input v-model="formData.type"></Input>
        </FormItem>

        <FormItem label="描述" prop="description">
            <Input type="textarea" :rows="10" v-model="formData.description" ></Input>
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
        value: '',
        label: '',
        type: '',
        description: ''
      },
      ruleValidate: {
        value: [
          {required: true, message: '键值不能为空', trigger: 'blur'}
        ],
        label: [
          {required: true, message: '标签不能为空', trigger: 'blur'}
        ],
        type: [
          {required: true, message: '类型不能为空', trigger: 'blur'}
        ]
      }
    }
  }
}
</script>
