export const validateTel = (rule, value, callback) => {
  var telRegex = /^\d{3,4}-\d{7,8}$/
  if (value != '' && !telRegex.test(value)) {
    callback(new Error('请输入正确的电话号码'))
  } else {
    callback()
  }
}
