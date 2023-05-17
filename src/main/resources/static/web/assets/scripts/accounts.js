const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            data: [],
            accounts: [],
            loans: [],
            showhide: false,
            type: ""
        }
    },
    created() {
        this.getLoans()
        this.getAccounts()
        // axios.get('http://localhost:8080/api/clients/current', { headers: { 'accept': 'application/xml' } }).then(response =>
        //     console.log(response.data))
    },
    methods: {
        getLoans() {
            axios
                .get('/api/clients/current')
                .then(response => {
                    this.data = response.data
                    this.loans = this.data.loans
                    this.loans.sort((a, b) => a.loanId - b.loanId)
                })
                .catch(err => console.log(err))
        },
        getAccounts() {
            axios
                .get('/api/accounts/current')
                .then(response => {
                    this.accounts = response.data
                    this.accounts.sort((a, b) => a.id - b.id)
                    // console.log(this.accounts);
                })
        },
        logOut() {
            axios
                .post('/api/logout')
                .then(response => {
                    console.log('signed out!!!')
                    window.location.replace('/web/index.html');
                })
        },
        createAccount() {
            axios
                .post('/api/accounts/current/create', `type=${this.type}`)
                .then(response => {
                    console.log('Account created')
                    this.getAccounts()
                    this.hide()
                })
        },
        deleteAccount(number) {
            axios
                .post('/api/accounts/current/delete', `number=${number}`)
                .then(response => {
                    console.log('Account Deleted!!!')
                    this.getAccounts()
                })
        },
        show() {
            this.showhide = true
        },
        hide() {
            this.showhide = false
        }
    }
})
app.mount('#vueApp')