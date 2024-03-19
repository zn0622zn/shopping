
<template>

    <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="80">
        <input type="hidden" v-model="formData.id"/>

        <Row>
            <Col span="12">
            <FormItem label="名称" prop="name">
                <Input v-model="formData.name"></Input>
            </FormItem>
            </Col>
            <Col span="12">
            <FormItem label="描述" prop="tite">
                <Input v-model="formData.title"></Input>
            </FormItem>
            </Col>
        </Row>
        <Row>
            <Col span="12">
            <FormItem label="用户" prop="userIds">
                <select-users v-model="formData.userIds"></select-users>
            </FormItem>
            </Col>
            <Col span="12">
            <FormItem label="权限" prop="permissionIds">
                <select-permissions v-model="formData.permissionIds"></select-permissions>
            </FormItem>
            </Col>
        </Row>
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
import selectUsers from '_c/select/selectUsers.vue'
import selectPermissions from '_c/select/selectPermissions.vue'

export default {
  components: {selectUsers, selectPermissions},
  mixins: [baseEdit],
  data () {
    return {
      roleList: [],
      formData: {
        id: '',
        name: '',
        title: '',
        desc: '',
        userIds: [],
        permissionIds: []
      },
      ruleValidate: {
        name: [
          {required: true, message: '请输入登录名称', trigger: 'blur'}
        ]
      }
    }
  }
}
</script>
