<template>

    <Form ref="form" :model="formData" :rules="ruleValidate" :label-width="80">
        <input type="hidden" v-model="formData.id"/>

        <Row>
            <Col span="12">
                <FormItem label="名称" prop="rname">
                    <Input v-model="formData.rname"></Input>
                </FormItem>
            </Col>
            <Col span="12">
                <FormItem label="分类" prop="cid">
                    <select-category v-model="formData.cid"></select-category>
                </FormItem>
            </Col>
        </Row>
        <Row>
            <Col span="12">
                <FormItem label="商家" prop="sid">
                    <select-seller v-model="formData.sid"></select-seller>
                </FormItem>
            </Col>
            <Col span="12">
                <FormItem label="单价" prop="price">
                    <Input v-model="formData.price" number></Input>
                </FormItem>
            </Col>
        </Row>
        <Row>
            <Col span="24">
                <FormItem label="缩略图" prop="rimage">
                    <single-img :pimage-url="formData.rimage" @setImgUrl="setImgUrl($event)"></single-img>
                </FormItem>
            </Col>
        </Row>
        <Row>
            <Col span="24">
                <FormItem label="商品大图" prop="rimage">
                    <multi-img :pimage-url-list="bigPicList" @setImgUrlList="setBigImgUrlList($event)"></multi-img>
                </FormItem>
            </Col>
        </Row>
        <Row>
            <Col span="24">
                <FormItem label="商品小图" prop="rimage">
                    <multi-img :pimage-url-list="smallPicList" @setImgUrlList="setSmallImgUrlList($event)"></multi-img>
                </FormItem>
            </Col>
        </Row>

        <FormItem label="线路介绍" prop="routeIntroduce">
            <Input type="textarea" :rows="10" v-model="formData.routeIntroduce"></Input>
        </FormItem>

        <FormItem>
            <Button type="primary" @click="handleSubmit('form')">保存</Button>
            <Button type="primary" @click="go2list()" style="margin-left: 8px">关闭</Button>
        </FormItem>

    </Form>

</template>

<script>
    import instance from '@/libs/api/index'
    import {baseEdit} from '@/libs/crud/base-edit'
    import selectCategory from '_c/select/selectCategory.vue'
    import selectSeller from '_c/select/selectSeller.vue'
    import singleImg from '_c/upload/singleImg.vue'
    import multiImg from '_c/upload/multiImg.vue'
    import {validateTel} from '@/libs/validate/base-rule'

    export default {
        components: {selectCategory, selectSeller, multiImg, singleImg},
        mixins: [baseEdit],
        data() {
            return {
                routeImgList: [], //详细图片对象列表
                formData: {
                    id: '',
                    rname: '',
                    cid: '',
                    sid: '',
                    price: '',
                    rimage: '',
                    routeIntroduce: '',
                    bigImgList: [], //详细大图列表
                    smallImgList: []//详细小图列表
                },
                ruleValidate: {
                    rname: [
                        {required: true, message: '请输入名称', trigger: 'blur'}
                    ],
                    cid: [
                        {required: true, message: '请选择分类', trigger: 'change', type:'number'}
                    ],
                    sid: [
                        {required: true, message: '请选择商家', trigger: 'change', type:'number'}
                    ],
                    price: [
                        {required: true, message: '请输入价格', trigger: 'blur', type:'number'}
                    ]
                }
            }
        },
        methods : {
            setImgUrl(data) {
                console.log(data);
                this.formData.rimage = data;
            },
            setBigImgUrlList(data) {
                var arr = data.map((o) => o.url);
                console.log(arr);
                this.formData.bigImgList = arr;
            },
            setSmallImgUrlList(data) {
                var arr = data.map((o) => o.url);
                console.log(arr);
                this.formData.smallImgList = arr
            },

            // 根据ID加载数据(重写base-edit中get方法)
            get (id) {
                instance.get(`/${this.namespace}/${this.entityName}/edit/` + id).then(response => {
                    Object.keys(this.formData).forEach((key) => {
                        if (response.data[key]) {
                            this.formData[key] = response.data[key]
                        }
                    })
                    this.routeImgList = response.data.routeImgList; //不能放到formData中否则提交修改报错
                }).catch(error => {
                    console.log(error)
                })
            }
        },
        computed: {
            bigPicList() {
                if (this.routeImgList) {
                    return this.routeImgList.map((value) => {
                        return {url : value.bigPic}
                    })
                } else {
                    return []
                }
            },
            smallPicList() {
                if (this.routeImgList) {
                    return this.routeImgList.map((value) => {
                        return {url : value.smallPic}
                    })
                } else {
                    return []
                }
            }
        }
    }
</script>
