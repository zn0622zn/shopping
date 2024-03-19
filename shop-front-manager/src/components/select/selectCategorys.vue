<template>
    <treeselect :value="value" @input="handleInput" :multiple="true" :flat="true" :options="categoryList" :disable-branch-nodes="true" :show-count="true" />
</template>


<script>
    import instance from '@/libs/api/index'
    import { listToTree } from '@/libs/util'
    // import the component
    import Treeselect from '@riophae/vue-treeselect'
    // import the styles
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'

    export default {
        components: { Treeselect },
        name: 'selectCategorys',
        data() {
            return {
                categoryList: []
            }
        },
        props: {value: Array}, // 接收一个 value prop
        methods: {
            handleInput(value) {
                this.$emit('input', value) // 触发 input 事件，并传入新值,v-model：使用:value读，使用@input写
            }
        },
        created() {
            instance.post(`/item/category/list`).then(response => {
                this.categoryList = listToTree(response.data)
            }).catch(error => {
                console.log(error)
            })
        }
    }
</script>
