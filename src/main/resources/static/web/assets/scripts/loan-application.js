const { createApp } = Vue;
const url01 = 'http://localhost:8080/api/clients/current';
const url02 = 'http://localhost:8080/api/loans';
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
        }
    },
    created() {
        axios.get(url01)
            .then(response => {
                this.data = response.data
                this.accounts = this.data.accounts
                this.accounts.sort((a, b) => a.id - b.id)
                // console.log(this.data);
                // console.log(this.accounts);
            })
            .catch(err => console.log(err))
        axios.get(url02)
            .then(response => {
                this.loans = response.data
                // console.log(this.loans);
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
                title: 'SEGURO',
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: 'SI',
                denyButtonText: `NO`,
            }).then((result) => {
                /* Read more about isConfirmed, isDenied below */
                if (result.isConfirmed) {
                    axios.post('/api/loans', { loanId: this.actualLoan.id, amount: this.amount, payments: this.payment, destinyNumber: this.destinyNumber })
                        .then(response => Swal.fire({
                            icon: 'success',
                            title: 'Aprobado',
                            timer: 3000,
                        })).catch(error => Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: error.response.data,
                            timer: 3000,
                        }))
                    // Swal.fire('GUARDADO!', '', 'success')
                } else if (result.isDenied) {
                    Swal.fire('PRESTAMO CANCELADO', '', 'info')
                }
            });
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