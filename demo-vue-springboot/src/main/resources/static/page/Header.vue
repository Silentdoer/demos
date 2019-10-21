<template>
	<div>
		<div>4<span>0</span>3</div>
		<div class="custom-shit">{{lala}}~ 你没有权限访问该页面哦</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				lala: '就是快乐飞'
			};
		}
	}
</script>

<style scoped>
	.custom-shit {
		color: #1c7430;
	}
</style>