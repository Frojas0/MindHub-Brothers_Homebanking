const { createApp } = Vue;
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
        axios.get('/api/accounts/current')
            .then(response => {
                this.accounts = response.data
                this.accounts.sort((a, b) => a.id - b.id)
                console.log(this.accounts);
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
            Swal.fire({
                icon: 'warning',
                title: 'Shure?',
                text: "you are about to transfer from: " + this.originNumber + " to: " + this.destinationNumber + " amount: " + this.amount,
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: 'Transfer',
                denyButtonText: `Cancel`,
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/transactions', `originNumber=${this.originNumber}&destinationNumber=${this.destinationNumber}&description=${this.description}&amount=${this.amount}`)
                        .then(response => {
                            Swal.fire({
                                icon: 'success',
                                title: 'Correct transaction',
                                timer: 3000,
                            })
                            window.location.replace('/web/accounts.html')
                        })
                        .catch(error => Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: error.response.data,
                            timer: 3000,
                        }))
                } else if (result.isDenied) {
                    Swal.fire('Transfer cancelled', '', 'info')
                }
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
// axios.post('/api/clients/current/transactions', `originNumber=${this.originNumber}&destinationNumber=${this.destinationNumber}&description=${this.description}&amount=${this.amount}`)
//     .then(response => {
//         console.log('SUCCESFULL')
//         window.location.replace('/web/accounts.html')
//     })