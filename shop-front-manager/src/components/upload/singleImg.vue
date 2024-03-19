<template>
    <div class="demo">
        <div class="demo-upload-list" v-if="hasImage">
            <img :src="imageUrl" />
            <div class="demo-upload-list-cover">
                <Icon type="ios-eye-outline" @click.native="handleView(imageUrl)"></Icon>
                <Icon type="ios-trash-outline" @click.native="handleRemove()"></Icon>
            </div>
        </div>
        <Upload
                :action="actionUrl"
                :format="['jpg','jpeg','png']"
                :max-size="2048"
                :on-exceeded-size="handleMaxSize"
                :on-success="handleSuccess"
                :show-upload-list="false"
                style=" width:58px;">
            <Button icon="ios-cloud-upload-outline">上传图片</Button>
        </Upload>
        <Modal title="图片预览" v-model="visible">
            <img :src="showImageUrl" v-if="visible" style="width: 100%" />
        </Modal>
    </div>

</template>

<script>

    export default {
        name: 'singleImg',
        props: {
            pimageUrl: {
                type: String
            }
        },
        data () {
            return {
                actionUrl: 'http://localhost:9004/uploadFile',
                imageUrl: '',
                hasImage: false,
                showImageUrl: '',
                visible: false
            }
        },
        methods: {
            handleMaxSize (file) {
                this.$Notice.warning({
                    title: '图片大小限制',
                    desc: '文件 ' + file.name + '太大,不能超过 2M.'
                })
            },
            upload () {
                this.loadingStatus = true
                setTimeout(() => {
                    this.file = null
                    this.loadingStatus = false
                    this.$Message.success('Success')
                }, 1500)
            },
            handleView (imageUrl) {
                this.showImageUrl = imageUrl
                this.visible = true
            },
            handleRemove () {
                this.imageUrl = ''
                this.hasImage = false
                this.$emit('setImgUrl', '')
            },
            handleSuccess (res, file) {
                this.imageUrl = `http://192.168.64.129:8888/${res}`;
                this.hasImage = true
            }
        },
        watch: {
            pimageUrl(newVal, oldVal) {
                if (newVal && newVal != oldVal) {
                    this.imageUrl = newVal;
                    this.hasImage = true
                }
            },
            imageUrl(newValue, oldVal) {
                this.$emit('setImgUrl', newValue)
            }
        }
    }

</script>

<style scoped>

    .demo-upload-list {
        display: inline-block;width: 60px;height: 60px;text-align: center;line-height: 60px;
        border: 1px solid transparent;border-radius: 4px;overflow: hidden;background: #fff;
        position: relative;box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);margin-right: 4px;
    }
    .demo-upload-list img {
        width: 100%;height: 100%;
    }
    .demo-upload-list-cover {
        display: none;position: absolute;top: 0;bottom: 0;
        left: 0;right: 0;background: rgba(0, 0, 0, 0.6);
    }
    .demo-upload-list:hover .demo-upload-list-cover {
        display: block;
    }
    .demo-upload-list-cover i {
        color: #fff;font-size: 20px;cursor: pointer;margin: 0 2px;
    }

</style>
