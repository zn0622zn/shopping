<template>

    <Select :value="currentValue" @on-change="handleInput"  multiple >
        <Option v-for="item in permissionList" :value="item.id" :key="item.id">{{ item.title }}</Option>
    </Select>

</template>

<script>
import instance from '@/libs/api/index'

export default {
  name: 'selectPermissions',
  data () {
    return {
      permissionList: []
    }
  },
  computed: {
    currentValue: function () {
      return this.value
    }
  },
  props: ['value'], // 接收一个 value prop
  methods: {
    handleInput (value) {
      this.$emit('input', value) // 触发 input 事件，并传入新值
    }
  },
  mounted () {
    instance.post(`/security/permission/list`).then(response => {
      this.permissionList = response.data
    }).catch(error => {
      console.log(error)
    })
  }
}
</script>
