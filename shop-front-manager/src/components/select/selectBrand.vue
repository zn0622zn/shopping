<template>

    <Select :value="value" @on-change="handleInput" >
        <Option v-for="item in brandList" :value="item.id" :key="item.id">{{ item.name }}</Option>
    </Select>

</template>

<script>
import instance from '@/libs/api/index'
export default {
  name: 'selectBrand',
  data () {
    return {
      brandList: []
    }
  },
  props: ['value'], // 接收一个 value prop
  methods: {
    handleInput (value) {
      this.$emit('input', value) // 触发 input 事件，并传入新值,v-model：使用:value读，使用@input写
    }
  },
  mounted () {
    instance.post(`/item/brand/list`).then(response => {
      this.brandList = response.data
    }).catch(error => {
      console.log(error)
    })
  }
}
</script>
