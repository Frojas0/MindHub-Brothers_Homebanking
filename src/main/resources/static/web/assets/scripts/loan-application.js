const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            data: [],
            accounts: [],
            loans: [],
            loanType: "",
            sel: false,
            sel2: false,
            sel3: false,
            actualLoan: {},
            loanId: '',
            amount: '',
            payment: '',
            destinyNumber: '',
            finalPayment: '',
        }
    },
    created() {
        axios.get('/api/accounts/current')
            .then(response => {
                this.accounts = response.data
                // this.accounts = this.data.accounts
                this.accounts.sort((a, b) => a.id - b.id)
                // console.log(this.data);
                // console.log(this.accounts);
            })
            .catch(err => console.log(err))
        axios.get('/api/loans')
            .then(response => {
                this.loans = response.data
                console.log(this.loans);
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
        selected() {
            this.sel = true;
        },
        selected2() {
            this.sel2 = true;
            this.finalPayment = this.calcularInteres(this.loans, this.payment, this.loanType) * this.amount
        },
        selected3() {
            this.sel3 = true;
        },
        paymentsByOption() {
            for (let i of this.loans) {
                if (i.name == this.loanType) {
                    this.actualLoan = i;
                }
            }
            console.log(this.actualLoan);
            // console.log(this.actualLoan.id);
            // console.log(this.amount);
            // console.log(this.destinyNumber);
        },
        adquireLoan() {
            Swal.fire({
                title: 'Sure? you are about to acquire a loan',
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: 'Yes',
                denyButtonText: `No`,
            }).then((result) => {
                /* Read more about isConfirmed, isDenied below */
                if (result.isConfirmed) {
                    axios.post('/api/loans', { loanId: this.actualLoan.id, amount: this.amount, payments: this.payment, destinyNumber: this.destinyNumber })
                        .then(response => Swal.fire({
                            icon: 'success',
                            title: 'Approved',
                            timer: 3000,
                        })).catch(error => Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: error.response.data,
                            timer: 3000,
                        }))
                    // Swal.fire('GUARDADO!', '', 'success')
                } else if (result.isDenied) {
                    Swal.fire('Loan Cancelled', '', 'info')
                }
            });
        },
        calcularInteres(lista, valor, type) {
            let sum = 1
            for (let i = 0; i < lista.length; i++) {
                if (lista[i].name === type) {
                    sum += lista[i].interest
                    for (let j = 0; j < lista[i].payments.length; j++) {
                        if (lista[i].payments[j] == valor) {
                            sum += (j + 1) * 0.05
                            return sum
                        }
                    }
                }
            }
            return 0.0;
        }
        // {
        //     axios.post('/api/loans', { loanId: this.actualLoan.id, amount: this.amount, payments: this.payment, destinyNumber: this.destinyNumber })
        //         .then(response => {
        //             console.log('APPROVED')
        //         })
        // },
    }
})
app.mount('#vueApp')