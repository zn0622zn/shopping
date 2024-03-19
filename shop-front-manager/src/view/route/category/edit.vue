<template>

    <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="80">
        <input type="hidden" v-model="formData.id"/>

        <FormItem label="类型" prop="cname">
            <Input v-model="formData.cname"></Input>
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
        cname: ''
      },
      ruleValidate: {
        cname: [
          {required: true, message: '类型不能为空', trigger: 'blur'}
        ]
      }
    }
  }
}
</script>
