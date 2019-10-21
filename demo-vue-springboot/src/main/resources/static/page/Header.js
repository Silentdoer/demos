export default {
	template: `<div>
\t\t<div>4<span>0</span>3</div>
\t\t<div class="custom-shit">{{lala}}~ 你没有权限访问该页面哦</div>
\t</div>`,
	data() {
		return {
			lala: '就是快乐飞'
		};
	}
	// style只能写成一个css文件来实现
}