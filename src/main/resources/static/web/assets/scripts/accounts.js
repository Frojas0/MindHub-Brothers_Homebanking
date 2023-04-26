const { createApp } = Vue;
const url = 'http://localhost:8080/api/clients/current';
const app = createApp({
    data() {
        return {
            data: [],
            accounts: [],
            loans: []
        }
    },
    created() {
        axios.get(url)
            .then(response => {
                this.data = response.data
                this.accounts = this.data.accounts
                this.accounts.sort((a, b) => a.id - b.id)
                this.loans = this.data.loans
                this.loans.sort((a, b) => a.loanId - b.loanId)
                console.log(this.data)
                // console.log(this.accounts);
                // console.log(this.loans)
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
        },
        createAcc() {
            axios
                .post('/api/clients/current/accounts')
                .then(response => {
                    console.log('Account created')
                    window.location.replace('/web/accounts.html')
                })
        }
    }
})
app.mount('#vueApp')