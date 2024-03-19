<template>

    <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="80">
        <input type="hidden" v-model="formData.id"/>

        <FormItem label="名称" prop="name">
            <Input v-model="formData.name"></Input>
        </FormItem>
        <FormItem label="首字母" prop="letter">
            <Input v-model="formData.letter"></Input>
        </FormItem>
        <FormItem label="图片" prop="image">
            <single-img :pimage-url="formData.image" @setImgUrl="setImgUrl($event)"></single-img>
        </FormItem>
        <FormItem label="分类" prop="categoryIds">
            <select-categorys v-model="formData.categoryIds"></select-categorys>
        </FormItem>

        <FormItem>
            <Button type="primary" @click="handleSubmit('form')">保存</Button>
            <Button type="primary" @click="go2list()" style="margin-left: 8px">关闭</Button>
        </FormItem>



    </Form>

</template>

<script>
    import {baseEdit} from '@/libs/crud/base-edit'
    import selectCategorys from '_c/select/selectCategorys.vue'
    import singleImg from '_c/upload/singleImg.vue'


    export default {
        components: {selectCategorys, singleImg},
        mixins: [baseEdit],
        data() {
            return {
                formData: {
                    id: '',
                    name: '',
                    letter: '',
                    image: '',
                    categoryIds: []
                },
                ruleValidate: {
                    name: [
                        {required: true, message: '名称不能为空', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            setImgUrl(data) {
                this.formData.image = data;
            }
        }
    }
</script>
