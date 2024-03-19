<template>
    <div>
        <Tree :data="rows"></Tree>
        <Modal v-model="modal" width="800px">
            <p slot="header">
                <span>{{category.title}}规格参数</span>
            </p>
            <Card v-for="(groupItem, groupIndex) in groups" :key="groupIndex">
                <p slot="title">
                    {{groupItem.name}}
                </p>
                <a href="#" slot="extra">
                    <Icon type="ios-trash" @click="deleteGroup(groupItem.id)"></Icon>
                </a>

                <Collapse>
                    <Panel v-for="(paramItem, paramIndex) in groupItem.params" :name="`${groupIndex}-${paramIndex}`" :key="paramIndex">
                        {{paramItem.name}}
                        <Icon type="ios-trash" style="float: right;margin-top: 10px" @click="deleteParam(groupItem.id, paramItem.id)"></Icon>
                        <div slot="content" style="height: 40px;">
                                <Col span="4">
                                    <Input v-model="paramItem.unit" placeholder="单位"></Input>
                                </Col>
                                <Col span="4" style="margin-left: 20px;">
                                    <Input v-model="paramItem.segments" placeholder="搜索范围"></Input>
                                </Col>
                                <Col  span="4" style="margin-top: 5px; margin-left: 20px;">
                                    数字参数：<i-switch v-model="paramItem.numeric"/>
                                </Col>
                                <Col span="4" style="margin-top: 5px; margin-left: 20px;">
                                    搜索参数：<i-switch v-model="paramItem.searching" />
                                </Col>
                                <Col span="4" style="margin-top: 5px; margin-left: 20px;">
                                    通用规格:<i-switch v-model="paramItem.generic"/>
                                </Col>
                        </div>
                    </Panel>
                </Collapse>
                <br>
                <Button @click="addParam(groupItem.id)">添加新属性</Button>
            </Card>

            <div slot="footer">
                <Button type="primary" @click="saveGroup">保存规格模板</Button>
                <Button @click="addGroup">添加新分组</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import {baseTree} from '@/libs/crud/base-tree'
    import {listToTree} from '@/libs/util'
    import instance from '@/libs/api/index'
    import Qs from 'qs'

    export default {
        mixins: [baseTree],
        // 表格各行的数据
        data() {
            return {
                modal: false, //分组对话框弹出标记
                category:{},//当前分类,弹出对话框是，显示的分类
                groups:[] //规格分组，格式[{id:1,cid:76,name:'主体',params:[...]},{}...]
            }
        },
        methods: {
            //保存规格参数
            saveGroup() {
                instance.post(`/item/group/save-group`,this.groups ,{
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }).then(response => {
                    this.$Message.success(response.data.msg)
                    this.modal = false
                }).catch(error => {
                    console.log(error)
                })
            },
            //删除参数
            deleteParam(gid, pid) {
                this.groups.find(item => item.id == gid).params.splice(
                    this.groups.find(item => item.id == gid).params.findIndex(item => item.id == pid), 1
                )
            },
            //添加新参数
            addParam(gid) {
                let pname = ''
                this.$Modal.confirm({
                    render: (h) => {
                        return h('Input', {
                            props: {
                                value: pname,
                                autofocus: true,
                                placeholder: '请输入分组名称'
                            },
                            on: {
                                input: (val) => {
                                    pname = val;
                                }
                            }
                        })
                    },
                    onOk: () => {
                        this.groups.find(item => item.id == gid).params.push({
                            name: pname,
                            cid: this.category.id,
                            groupId: gid
                        })
                    }
                })
            },
            //删除分组
            deleteGroup(gid) {
                this.groups.splice(this.groups.findIndex(item => item.id == gid), 1)
            },
            //添加新分组
            addGroup() {
                let gname = ''
                this.$Modal.confirm({
                    render: (h) => {
                        return h('Input', {
                            props: {
                                value: gname,
                                autofocus: true,
                                placeholder: '请输入分组名称'
                            },
                            on: {
                                input: (val) => {
                                    gname = val;
                                }
                            }
                        })
                    },
                    onOk: () => {
                        let mid = 0
                        if (this.groups && this.groups.length > 0) {
                            mid = this.groups.map(o => o.id).reduce((a,b) => {
                                return b > a ? b : a;
                            }) + 1
                        }
                        console.log(mid)
                        this.groups.push({id: mid, name: gname, cid: this.category.id, params:[]})
                    }
                })
            },
            // 查询分组
            queryGroup(cid) {
                instance.post(`/item/group/list`, Qs.stringify({"cid": cid})).then(response => {
                    this.groups = response.data;
                }).catch(error => {
                    console.log(error)
                })
                instance.get(`/item/category/edit/${cid}`).then(response => {
                    this.category = response.data;
                }).catch(error => {
                    console.log(error)
                })
            },
            // 查询分类
            query() {
                instance.post(`/item/category/list`).then(response => {
                    this.rows = this.readerLeaf(listToTree(response.data));
                }).catch(error => {
                    console.log(error)
                })
            },
            readerLeaf(data) {
                data.forEach(item => {
                    if (!item.children) {
                        item = Object.assign(item, {render: this.renderContent});
                    } else {
                        this.readerLeaf(item.children)
                    }
                });
                return data;
            },
            renderContent(h, {root, node, data}) {
                return h('span', {
                    style: {
                        display: 'inline-block',
                        width: '100%'
                    }
                }, [
                    h('span', [
                        h('Icon', {
                            props: {
                                type: 'ios-paper-outline'
                            },
                            style: {
                                marginRight: '8px'
                            }
                        }),
                        h('span', data.title)
                    ]),
                    h('span', {
                        style: {
                            display: 'inline-block',
                            float: 'right',
                            marginRight: '32px'
                        }
                    }, [
                        h('Button', {
                            props: Object.assign({}, this.buttonProps, {
                                icon: 'ios-aperture-outline'
                            }),
                            style: {
                                marginRight: '8px'
                            },
                            on: {
                                click: () => {
                                    this.queryGroup(data.id);
                                    this.modal = true
                                }
                            }
                        })
                    ])
                ]);
            }
        }

    }
</script>
