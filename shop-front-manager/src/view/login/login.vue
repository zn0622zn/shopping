<style lang="less">
  @import './login.less';
</style>

<template>
  <div class="login" @keydown.enter="handleSubmit('form')">
    <div class="login-con">
      <Card icon="log-in" title="欢迎登录" :bordered="false">
        <div class="form-con">

          <Form ref="form" :model="formData" :rules="ruleValidate">
            <FormItem prop="username">
              <Input v-model="formData.username" placeholder="请输入用户名">
              <span slot="prepend">
                <Icon :size="16" type="ios-person"></Icon>
              </span>
              </Input>
            </FormItem>
            <FormItem prop="password">
              <Input type="password" v-model="formData.password" placeholder="请输入密码">
              <span slot="prepend">
                <Icon :size="14" type="md-lock"></Icon>
              </span>
              </Input>
            </FormItem>
            <FormItem>
              <Button @click="handleSubmit('form')" type="primary" long>登录</Button>
            </FormItem>
          </Form>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
  import instance from '@/libs/api/index'
  import Qs from 'qs'
  import store from '@/store'

  export default {
    name: 'login',
    data () {
      return {
        formData: {
          username: '',
          password: ''
        },
        ruleValidate: {
          username: [
            {required: true, message: '用户名不能为空', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '密码不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      // 提交
      handleSubmit (name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            instance.post(`/doLogin`, Qs.stringify(this.formData, {arrayFormat: 'repeat'})).then(response => {
              const data = response.data
              store.commit('setToken', data.access_token)

              // //设置请求头统一携带token
              instance.defaults.headers.common['Authorization'] = 'Bearer ' + data.access_token;

              this.$router.push({
                name: 'home'
              })
            })

          } else {
            this.$Message.error('Fail!')
          }
        })
      }

    }

  }
</script>

<style>

</style>
