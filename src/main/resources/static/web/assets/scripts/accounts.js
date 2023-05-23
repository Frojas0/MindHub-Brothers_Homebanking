const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            data: [],
            accounts: [],
            loans: [],
            showhide: false,
            type: "",
            accountNumber: "",
            payments: "",
            loanName: ""
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
                .get('/api/clients/acquiredLoans')
                .then(response => {
                    this.loans = response.data
                    this.loans.sort((a, b) => a.loanId - b.loanId)
                    // console.log(this.loans);
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
        },
        payLoan() {
            Swal.fire({
                title: 'Seguro?',
                text: "Estas a punto de pagar un prestamo \n" + "Loan: " + this.loanName + " Payments: " + this.payments,
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: 'Si',
                denyButtonText: `No`,
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/payLoan', `accountNumber=${this.accountNumber}&payments=${this.payments}&name=${this.loanName}`)
                        .then(response => {
                            Swal.fire({
                                icon: 'success',
                                title: 'Approved',
                                timer: 3000,
                            })
                            window.location.replace('./accounts.html');
                        })
                        .catch(error => Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: error.response.data,
                            timer: 3000,
                        }))
                } else if (result.isDenied) {
                    Swal.fire('Payment Cancelled', '', 'info')
                }
            })
        }
    }
})
app.mount('#vueApp')