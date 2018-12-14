var app = new Vue({
    el: '#app',
    data: {
        /*这个数据本来应该从服务端获取的*/
        list: [
            {
                id: 1,
                name: 'iPhone7',
                price: 6188,
                count: 1
            },
            {
                id: 2,
                name: 'iPad Pro',
                price: 5888,
                count: 1
            },
            {
                id: 3,
                name: 'MacBook Pro',
                price: 21488,
                count: 1
            }
        ]
    },
    computed: {}
});