<template>
    <div>
        <div class="container">
            <i-input v-model="sreachData" size="large" class="sreach" placeholder="输入你想查找的商品">
                <Button slot="append" icon="ios-search" @click="sreach"></Button>
            </i-input>
            <Tag v-for="(item, index) in promotionTags" :key="index" closable @on-close="closeTags(index)"><span
                    @click="selectTags(index)">{{item}}</span></Tag>
        </div>
    </div>
</template>

<script>
    import {mapMutations} from "vuex"
    export default {
        name: 'Search',
        data() {
            return {
                sreachData: '',
                promotionTags: ['买2免1', '领200神券', '199减100', '母婴5折抢', '充100送20']
            };
        },
        methods: {
            ...mapMutations(['SET_SEARCH_KEY']),
            closeTags(index) {
                this.promotionTags.splice(index, 1);
            },
            selectTags(index) {
                this.sreachData = this.promotionTags[index];
            },
            sreach() {
                this.$emit('onSearch', this.sreachData)
                this.SET_SEARCH_KEY(this.sreachData)
                this.$router.push({path: '/goodsList', query: {sreachData: this.sreachData}}).catch(err => {
                    console.log('输出报错',err)
                })
            }
        }
    };
</script>

<style scoped>
    .container {
        padding-top: 15px;
        margin: 0px auto;
        margin-bottom: 15px;
        width: 460px;
    }

    .sreach {
        margin: 5px 0px;
    }
</style>
