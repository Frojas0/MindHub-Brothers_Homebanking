const { createApp } = Vue
const id = new URLSearchParams(location.search).get('id')
const url = `http://localhost:8080/api/accounts/${id}`
// console.log(id);

const app = createApp({
    data() {
        return {
            data: [],
            transactions: [],
        }
    },
    created() {
        axios.get(url)
            .then(response => {
                this.data = response.data
                this.transactions = response.data.transactions
                this.transactions.sort((a, b) => b.id - a.id)
                console.log(this.data);
                console.log(this.transactions);
            })
            .catch(err => console.log(err))
    },
})
app.mount('#vueApp')