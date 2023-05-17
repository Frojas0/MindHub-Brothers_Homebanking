const { createApp } = Vue;
const url = 'http://localhost:8080/api/clients/current';
const app = createApp({
    data() {
        return {
            data: [],
            accounts: [],
            originNumber: '',
            destinationNumber: '',
            description: '',
            amount: '',
            cont1: true,
            cont2: false
        }
    },
    created() {
        axios.get(url)
            .then(response => {
                this.data = response.data
                this.accounts = this.data.accounts
                this.accounts.sort((a, b) => a.id - b.id)
                // console.log(this.accounts);
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
        transfer() {
            axios
                .post('/api/clients/current/transactions', `originNumber=${this.originNumber}&destinationNumber=${this.destinationNumber}&description=${this.description}&amount=${this.amount}`)
                .then(response => {
                    console.log('SUCCESFULL')
                    window.location.replace('/web/accounts.html')
                })
        },
        own() {
            this.cont1 = true
            this.cont2 = false
        },
        third() {
            this.cont1 = false
            this.cont2 = true
        }
    }
})
app.mount('#vueApp')