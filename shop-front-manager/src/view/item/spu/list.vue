<template>
    <div>
        <Row>
            <Form ref="formData" :model="formData" :label-width="80">
                <Row style="margin-top: 10px;">
                    <Col span="6">
                        <FormItem label="标题" prop="name">
                            <Input v-model="formData.title" placeholder="标题"></Input>
                        </FormItem>
                    </Col>
                    <Col span="6">
                        <FormItem label="分类" prop="cid3">
                            <select-category2 v-model="formData.cid3"></select-category2>
                        </FormItem>
                    </Col>
                    <Col span="6">
                        <FormItem label="品牌" prop="brandId">
                            <select-brand v-model="formData.brandId"></select-brand>
                        </FormItem>
                    </Col>
                    <Col span="6">
                        <Divider type="vertical"/>
                        <Button type="primary" @click="add">添加</Button>
                        <Button type="primary" @click="removeBatch" style="margin-left: 8px">删除</Button>
                        <Button type="primary" @click="query" style="margin-left: 8px">查询</Button>
                    </Col>
                </Row>
            </Form>
        </Row>

        <div>
            <Table stripe ref="selection" :columns="columns" :data="rows"></Table>
        </div>
        <div class="paging">
            <Page :total="total" :page-size="pageSize" show-sizer show-elevator show-total
                  @on-change="changePage" @on-page-size-change="changePageSize"></Page>
        </div>
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
    import selectCategory2 from '_c/select/selectCategory2.vue'
    import selectBrand from '_c/select/selectBrand.vue'


    export default {
        mixins: [baseList],
        components: {selectCategory2, selectBrand},
        data() {
            return {
                formData: {
                    title: '',
                    cid3: null,
                    brandId: null
                },
                columns: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: '商品标题',
                        key: 'title'
                    },
                    {
                        title: '分类',
                        key: 'categoryName'
                    },
                    {
                        title: '品牌',
                        key: 'brandName'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
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
