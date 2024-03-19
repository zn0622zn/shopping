<template>
    <div>
        <div>
            <Form ref="formData" :model="formData" :label-width="80">
                <Row style="margin-top: 10px;">
                    <Col span="6">
                        <FormItem label="名称" prop="rname">
                            <Input v-model="formData.rname" placeholder="名称"></Input>
                        </FormItem>
                    </Col>
                    <Col span="6">
                        <FormItem label="分类" prop="cid">
                            <select-category v-model="formData.cid"></select-category>
                        </FormItem>
                    </Col>
                    <Col span="6">
                        <FormItem label="商家" prop="sid">
                            <select-seller v-model="formData.sid"></select-seller>
                        </FormItem>
                    </Col>
                    <Col span="6">
                        <Divider type="vertical"/>
                        <Button type="primary" @click="add">添加</Button>
                        <Button type="primary" @click="removeBatch">删除</Button>
                        <Button type="primary" @click="query">查询</Button>
                    </Col>
                </Row>
            </Form>
        </div>

        <Row>
            <Table stripe ref="selection" :columns="columns" :data="rows"></Table>
            <Page :total="total" :page-size="pageSize" show-sizer show-elevator show-total class="paging"
                  @on-change="changePage" @on-page-size-change="changePageSize"></Page>
        </Row>

    </div>
</template>
<style scoped>
    .paging {
        float: right;
        margin-top: 10px;
    }
</style>
<script>
    import {baseList} from '@/libs/crud/base-list'
    import selectCategory from '_c/select/selectCategory.vue'
    import selectSeller from '_c/select/selectSeller.vue'


    export default {
        components: {selectCategory, selectSeller},
        mixins: [baseList],
        data() {
            return {
                formData: {
                    rname: '',
                    cid: '',
                    sid: ''
                },
                columns: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: '线路名称',
                        key: 'rname',
                        width: 500
                    },
                    {
                        title: '分类',
                        key: 'category',
                        render: (h, params) => {
                            return h('span', params.row.category.cname);
                        }
                    },
                    {
                        title: '商家',
                        key: 'seller',
                        render: (h, params) => {
                            return h('span', params.row.seller.sname);
                        }
                    },
                    {
                        title: '价格',
                        key: 'price'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width:150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '2px'
                                    },
                                    on: {
                                        click: () => {
                                            this.edit(params.row.id)
                                        }
                                    }
                                }, '修改'),
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '2px'
                                    },
                                    on: {
                                        click: () => {
                                            this.remove(params.row.id, params.index)
                                        }
                                    }
                                }, '删除')
                            ])
                        }
                    }
                ]
            }
        }
    }
</script>
