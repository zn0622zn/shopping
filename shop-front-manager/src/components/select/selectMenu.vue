<template>
    <div>
      <Tree ref="menuTree" :data="menuList" multiple @on-select-change="checkMenu"></Tree>
    </div>
</template>
<script>
import instance from '@/libs/api/index'
import { listToTree } from '@/libs/util'


export default {
  name: 'selectMenu',
  props: [
    'roleId'
  ],
  data () {
    return {
      menuList: []
    }
  },
  mounted () {
    instance.post(`/security/menu/list-role/${this.roleId}`).then(response => {
      this.menuList = listToTree(response.data)
    }).catch(error => {
      console.log(error)
    })
  },
  methods:{
    checkMenu () {
      this.$emit("onCheckMenu", this.$refs.menuTree.getSelectedNodes());
    }
  }
}
</script>
