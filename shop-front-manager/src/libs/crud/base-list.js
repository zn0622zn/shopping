import instance from '@/libs/api/index'
import Qs from 'qs'

export const baseList = {


  data () {
    return {
      // 当前路由的子目录/security/post/1 -> security
      namespace: '',
      // 当前路由的最后访问路径/security/post/1-> post
      entityName: '',
      // 初始化信息总条数
      total: 0,
      // 每页显示多少条
      pageSize: 10,
      // 显示的数据
      rows: []
    }
  },
  methods: {
    // 添加
    add () {
      this.$router.push({
        name: `edit_${this.namespace}_${this.entityName}`
      })
    },

    // 删除
    remove (id, index) {
      this.$Modal.confirm({
        title: '确认删除',
        content: '确定要删除吗？',
        onOk: () => {
          instance.get(`/${this.namespace}/${this.entityName}/delete/` + id).then(response => {
            this.$Message.info('删除成功')
            this.query()
          }).catch(error => {
            console.log(error)
          })
        }
      })
    },
    // 批量删除
    removeBatch () {
      if (this.$refs.selection.getSelection().length > 0) {
        this.$Modal.confirm({
          title: '确认删除',
          content: '确定要删除吗？',
          onOk: () => {
            let params = new URLSearchParams()
            this.$refs.selection.getSelection().forEach((o) => {
              params.append('ids', o.id)
            })
            instance.post(`/${this.namespace}/${this.entityName}/delete`, params).then(response => {
              this.$Message.info('删除成功')
              this.query()
            })
          }
        })
      } else {
        this.$Message.info('请选择删除的数据')
      }
    },
    // 修改
    edit (id) {
      this.$router.push({
        name: `edit_${this.namespace}_${this.entityName}`,
        query: { id: id }
      })
    },
    // 查询
    query () {
      instance.post(`/${this.namespace}/${this.entityName}/list-page`, Qs.stringify(this.formData)).then(response => {
        this.rows = response.data.list
        this.total = response.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    // 分页
    changePage (index) {
      this.formData.page = index
      this.query()
    },
    // 设置每页行数
    changePageSize (size) {
      this.formData.page = 1
      this.formData.rows = size
      this.query()
    }
  },
  created () {
    let arrays = this.$route.path.split('/')
    this.namespace = arrays[1]
    this.entityName = arrays[2]
    this.query()
  }
}
