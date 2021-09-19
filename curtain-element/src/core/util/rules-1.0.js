/** ******************************************
 * 表单输入规则校验
 * dengzy
 * 2019-07-17
 *
 ******************************************* */

export default {
  // 必填项检验
  // 非空
  Null: [{
    required: true,
    pattern: /^(?=.*\S).+$/,
    trigger: ['blur', 'change'],
    message: '必填项不能为空'
  }],
  // 登录密码
  PassWord: [{
    required: true,
    pattern: /^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{8,20}$/,
    trigger: 'blur',
    message: '8-20位的数字、大小写字母和特殊字符'
  }],
  // 手机号码
  Phone: [{
    required: true,
    pattern: /^1[34578]\d{9}$/,
    message: '请输入正确的手机号码格式',
    trigger: 'blur'
  }],
  // 类似金钱,首位不为0,最多2位小数
  NumPrice: [{
    required: true,
    pattern: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
    trigger: 'blur',
    message: '请填写数字,最多2位小数'
  }],
  // 整数
  InterNum: [{
    required: true,
    pattern: /^[0-9]*[1-9][0-9]*$/,
    trigger: 'blur',
    message: '请输入整数'
  }],
  // 正整数
  NumPos: [{
    required: true,
    pattern: /^[1-9]\d*$/,
    trigger: ['blur', 'change'],
    message: '请输入正整数'
  }],
  // 邮箱
  Email: [{
    required: true,
    pattern: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
    trigger: 'blur',
    message: '请输入正确的邮箱格式'
  }],
  // 保留两位小数的正负数
  boom: [{
    required: false,
    pattern: /(^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d{1,2})?$)/,
    trigger: 'blur',
    message: '请填写数字,最多2位小数'
  }],
}
