<template>

    <Select :value="currentValue" @on-change="handleInput"  multiple >
        <Option v-for="item in roleList" :value="item.id" :key="item.id">{{ item.name }}</Option>
    </Select>

</template>

<script>
import instance from '@/libs/api/index'

export default {
  name: 'selectRoles',
  data () {
    return {
      roleList: []
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
    instance.post(`/security/role/list`).then(response => {
      this.roleList = response.data
    }).catch(error => {
      console.log(error)
    })
  }
}
</script>
