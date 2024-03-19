<template>
    <div>
        <Steps :current="step">
            <Step title="基本信息"></Step>
            <Step title="商品描述"></Step>
            <Step title="规格参数"></Step>
            <Step title="SKU属性"></Step>
            <Step title="SKU列表"></Step>
        </Steps>
        <Divider/>
        <!--商品基本信息-->
        <Form ref="form" :model="spu" :rules="ruleValidate" :label-width="80" v-show="step == 0">
            <input type="hidden" v-model="spu.id"/>
            <Row>
                <Col span="12">
                    <FormItem label="分类" prop="cid3">
                        <select-category2 v-model="spu.cid3"></select-category2>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="品牌" prop="brandId">
                        <select-brand v-model="spu.brandId"></select-brand>
                    </FormItem>
                </Col>
            </Row>
            <FormItem label="商品标题" prop="title">
                <Input v-model="spu.title"></Input>
            </FormItem>
            <FormItem label="商品买点" prop="subTitle">
                <Input type="textarea" :rows="3" v-model="spu.subTitle"></Input>
            </FormItem>
            <FormItem label="包装清单" prop="packingList">
                <Input type="textarea" :rows="3" v-model="spu.spuDetail.packingList"></Input>
            </FormItem>
            <FormItem label="售后服务" prop="afterService">
                <Input type="textarea" :rows="3" v-model="spu.spuDetail.afterService"></Input>
            </FormItem>
        </Form>
        <!--商品描述-->
        <Editor v-model="spu.spuDetail.description" v-show="step==1"/>
        <!--规格参数-->
        <Form ref="form2" :model="spu" :label-width="200" v-show="step == 2">
            <Row v-for="(value, name, index) in genericSpec" :key="index" style="margin-top: 5px;">
                <FormItem :label="name">
                    <Input v-model="genericSpec[name]"></Input>
                </FormItem>
            </Row>
        </Form>
        <!--sku属性-->
        <div v-show="step == 3">
            <Card v-for="(svalue, skey, sindex) in specialSpec" :key="sindex">
                <p slot="title">
                    {{skey}}
                </p>
                <Input v-for="(vvalue, vindex) in svalue" :key="vindex" v-model="specialSpec[skey][vindex]" style="margin-top: 5px; margin-bottom: 5px">
                    <Icon type="md-remove" slot="suffix" @click="specialSpec[skey].splice(vindex, 1)"/>
                </Input>
                <Button @click="addSpecialSpec(skey)">添加</Button>
            </Card>
        </div>
        <!--sku列表-->
        <div v-show="step == 4">
            <Row style="border-bottom: solid #c3c3c3 1px; height: 40px; line-height: 40px;">
                <Col v-for="(value, key, index) in specialSpec" :key="index" span="4">{{key}}</Col>
                <Col span="6">价格</Col>
                <Col span="6">库存</Col>
            </Row>
            <Row style="border-bottom: solid #c3c3c3 1px; height: 80px; line-height: 80px" v-for="(sku, index) in spu.skus" :key="index">
                <Col v-for="(value, key, index) in JSON.parse(sku.ownSpec)" :key="index" span="4">{{value}}</Col>
                <Col span="6">
                    <Input v-model="sku.price" style="width:200px"></Input>
                </Col>
                <Col span="6">
                    <Input v-model="sku.stock" style="width:200px"></Input>
                </Col>
            </Row>
            <Button type="primary" @click="saveSpu" style="margin-top: 10px;float: right">保存商品信息</Button>
        </div>

        <Divider/>
        <Button type="primary" @click="prev">上一步</Button>
        <Divider type="vertical"/>
        <Button type="primary" @click="next">下一步</Button>
    </div>
</template>

<script>
    import instance from '@/libs/api/index'
    import Qs from 'qs'
    import Editor from '_c/form/Editor.vue'
    import selectCategory2 from '_c/select/selectCategory2.vue'
    import selectBrand from '_c/select/selectBrand.vue'
    import {calcDescartes} from '@/libs/util'

    export default {
        components: {Editor, selectCategory2, selectBrand},
        data() {
            return {
                step: 0,
                spu: {
                    id: null,
                    title: '',
                    subTitle: '',
                    cid3: null,
                    brandId: null,
                    spuDetail: {
                        packingList: '',
                        afterService: '',
                        description: '',
                        specialSpec: '{}',
                        genericSpec: '{}'
                    },
                    skus: []
                }, //spu，格式{title:'',brandId:'', subDetail: {description:'', specialSpec:''...}, skus:[...] ...}
                genericSpec: {}, //规格参数,格式：{"品牌": "华为"...}   (这个变量，因为spu中的genericSpec为字符串)
                specialSpec: {}, //sku属性,格式： {"机身颜色": ["白色","黑色", "金色"], "内存":["16G","326"],机身存储:[]}
                ruleValidate: {
                    cid3: [
                        {required: true, message: '类别不能为空', trigger: 'change', type: 'number'},
                    ],
                    brandId: [
                        {required: true, message: '商品不能为空', trigger: 'change', type: 'number'},
                    ],
                    title: [
                        {required: true, message: '商品标题不能为空', trigger: 'blur'},
                    ]
                }
            }
        },
        methods: {
            //保存规格参数
            saveSpu() {
                instance.post(`/item/spu/save-spu`, this.spu, {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }).then(response => {
                    this.$Message.success(response.data.msg)
                    this.$router.push({name: `list-item-spu`})
                }).catch(error => {
                    console.log(error)
                })
            },
            //添加sku属性
            addSpecialSpec(key) {
                if (!this.specialSpec[key]) {
                    this.specialSpec[key] = new Array()
                }
                this.specialSpec[key].push('')
            },
            next() {
                if (this.step == 4) {
                    this.step = 0;
                } else {
                    this.step += 1;
                }
            },
            prev() {
                if (this.step == 0) {
                    this.step = 4;
                } else {
                    this.step -= 1;
                }
            },
            // 根据ID加载数据
            get(id) {
                instance.get(`/item/spu/edit/${id}`).then(response => {
                    this.spu = Object.assign(response.data);
                    this.genericSpec = JSON.parse(this.spu.spuDetail.genericSpec); //规格参数,计算属性不能双向绑定
                    this.specialSpec = JSON.parse(this.spu.spuDetail.specialSpec); //sku属性
                }).catch(error => {
                    console.log(error)
                })
            },
            //根据商品分类id查询规格参数
            getSpec(cid) {
                instance.post(`/item/param/list`, Qs.stringify({"cid": this.spu.cid3})).then(response => {
                    /**************************规格参数****************************/
                    let generic = Object.create(null);
                    //规格参数的key
                    let genericKeys = response.data.filter((item) => item.generic === true) .map((item) => item.name)
                    //修改时，取得第一次查询的规格参数,get后的spu中取出响应的属性
                    genericKeys.forEach(item => {
                        generic[item] = this.genericSpec[item]
                    });
                    this.genericSpec = generic
                    /**************************sku属性****************************/
                    let sku = Object.create(null);
                    //sku属性的key
                    let skuKeys = response.data.filter((item) => item.generic === false) .map((item) => item.name)
                    //修改时，取得第一次查询的规格参数,从get后的spu中取出响应的属性
                    skuKeys.forEach(item => {
                        sku[item] = this.specialSpec[item]
                    });
                    this.specialSpec = sku;

                }).catch(error => {
                    console.log(error)
                })
            }
        },
        created() {
            let id = this.$route.query.id;
            if (id) {
                this.get(id)
            }
        },
        computed: {
            getCid() {
                return this.spu.cid3;
            }
        },
        watch: {
            //规格参数改变，改变spu.spDetail.genericSpec
            genericSpec: {
                handler(newValue, oldValue) {
                    this.spu.spuDetail.genericSpec = JSON.stringify(newValue)
                },
                immediate: false, //第一次回调时取消侦听
                deep: true //发现对象内部值的变化
            },
            //sku属性改变时,改变spu.spuDetail.genericSpec和spu.skus
            specialSpec: {
                handler(newValue, oldValue) {
                    //修改时，在没有更改sku属性时，不进行sku计算
                    if (this.spu.spuDetail.specialSpec == JSON.stringify(newValue)) {
                        return
                    }
                    this.spu.spuDetail.specialSpec = JSON.stringify(newValue)
                    //求sku的笛卡尔积
                    let values = Object.values(this.specialSpec) //[["白色", 黑色, 玫瑰金], [3G, 8G], undefined]，没有添加完整是有可能是undefined
                    let keys = Object.keys(this.specialSpec)
                    values = values.filter((value) => value); //去掉undefined
                    let descartes = calcDescartes(values)
                    let skus = new Array()
                    descartes.forEach((ditem, dindex) => {
                        let sku = {}
                        let ownSpec = {} //sku的当前sku属性
                        if (ditem instanceof Array) { //笛卡尔积[["白色", 3G, 16G], [黑色, 3G, 16G]]
                            ditem.forEach((vitem, vindex) => {
                                ownSpec[keys[vindex]] = vitem
                            });
                        } else { //笛卡尔积["V9", "V10", "V20", ...]
                            ownSpec[keys[0]] = ditem
                        }
                        sku.ownSpec = JSON.stringify(ownSpec)
                        sku.title = this.spu.title
                        sku.spuId = this.spu.id
                        skus.push(sku)
                    })
                    this.spu.skus = skus
                },
                immediate: false,
                deep: true
            },
            //选择分类，改变规格参数
            getCid(newValue, oldValue) {
                this.getSpec(newValue);
            }
        }
    }
</script>


