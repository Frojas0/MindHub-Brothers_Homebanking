const { createApp } = Vue
const id = new URLSearchParams(location.search).get('id')
// console.log(id);

const app = createApp({
    data() {
        return {
            data: [],
            transactions: [],
        }
    },
    created() {
        axios.get(`/api/accounts/${id}`)
            .then(response => {
                this.data = response.data
                this.transactions = this.data.transactions
                this.transactions.sort((a, b) => b.id - a.id)
                // console.log(this.data);
                // console.log(this.transactions);
            })
            .catch(err => console.log(err))
    },
    methods: {
        logOut() {
            axios
                .post('/api/logout')
                .then(response => {
                    console.log('signed out!!!')
                    window.location.replace('/web/index.html');
                })
        }
    }
})
app.mount('#vueApp')