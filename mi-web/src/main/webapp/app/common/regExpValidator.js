Ext.define('App.common.regExpValidator',{
	singleton: true,
	cn_name: /[~!@#$%^&*()/\\|,.<>?"'();:_+-=\[\]{}]+/,
	unqiue_name: '',
	telphone: '',
	mobile: '',
	userName:/^[^\d]/,//用户名，首为不能为数字
	userNameMsg:'不能以数字开头',//用户名，首字母不能为数字
	integer:/^[+]{0,1}(\d+)$/,    //正整数
	integerMsg:'请输入正整数',
	phoneNumber:/^1\d{10}$|^\d{7,8}$/, //11位手机号或者7,8位座机号（暂时不用）
	phoneText:'请输入正确的手机号或座机号',
	telPhones:/^1\d{10}$/,
	telPhoneText:'请输入11位有效的号码',
    password: /^[^\u4e00-\u9fa5]{6,}$/, //不能输入汉字
    passwordText:'密码可输入数字或字符,最少输入6位',
    noChinese:/^[^\u4e00-\u9fa5]*$/,
	inputText:'输入格式错误',
    trim:/(^\s*)|(\s*$)/g,   //首尾去空格
    natural: /^\+?[1-9]\d*$/,   //自然数
    naturalText: '只能输入大于0的正整数',   //自然数
    NumberRexs:/^\d+$/,
    NumberTexts:'只能输入数字',
	plus:/^[1-9]\d*(\.\d+)?$|0\.\d*[1-9]\d*$/, //大于0的数
	email:/\w+@\w+(\.\w+)+$/ // 邮箱验证
});

