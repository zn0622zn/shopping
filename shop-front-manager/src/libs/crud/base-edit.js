import instance from '@/libs/api/index'
import Qs from 'qs'

export const baseEdit = {
    data() {
        return {
            // 当前路由的子目录/security/post/1 -> security
            namespace: '',
            // 当前路由的最后访问路径/security/post/1-> post
            entityName: ''
        }
    },
    methods: {
        /**
         * 模板方法：提交前用来处理保存的数据
         */
        beforeSubmit() {
            alert('b')
        },


        // 提交
        handleSubmit(name) {
            this.$refs[name].validate((valid) => {
                if (valid) {
                    instance.post(`/${this.namespace}/${this.entityName}/save`, Qs.stringify(this.formData, {arrayFormat: 'repeat'})).then(response => {
                        this.$Message.success(response.data.msg);
                        this.go2list()
                    })
                } else {
                    this.$Message.error('Fail!')
                }
            })
        },
        // 重置
        handleReset(name) {
            this.$refs[name].resetFields()
        },
        // 根据ID加载数据
        get(id) {
            instance.get(`/${this.namespace}/${this.entityName}/edit/` + id).then(response => {
                this.formData = Object.assign(response.data);
            }).catch(error => {
                console.log(error)
            })
        },

        go2list() {
            this.$router.push({name: `list_${this.namespace}_${this.entityName}`})
        }
    },

    created() {
        let arrays = this.$route.name.split('_');
        this.namespace = arrays[1];
        this.entityName = arrays[2];

        let id = this.$route.query.id;
        if (id) {
            this.get(id)
        }
    }
}
