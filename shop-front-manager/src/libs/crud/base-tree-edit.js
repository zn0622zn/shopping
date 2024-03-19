import { baseEdit } from './base-edit'

export const baseTreeEdit = {
  mixins: [baseEdit],
  created () {
    let arrays = this.$route.name.split('_')
    this.namespace = arrays[1]
    this.entityName = arrays[2]

    let id = this.$route.query.id
    this.formData.parentId = this.$route.query.parentId
    if (id) {
      this.get(id)
    }
  }
}
