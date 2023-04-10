const { createApp } = Vue;
const url = 'http://localhost:8080/api/clients/1';
const app = createApp({
    data() {
        return {
            data: [],
            // balance: "",
        }
    },
    created() {
        axios.get(url)
            .then(response => {
                this.data = response.data
                console.log(this.data)
            })
            .catch(err => console.log(err))
    },
    // computed: {
    //     separatedBalance() {
    //         this.balance = this.data.account.balance.toLocaleString();
    //         console.log(this.balance);
    //     },
    // },
})
app.mount('#vueApp')