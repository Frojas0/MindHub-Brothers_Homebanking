const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            data: [],
            name: "",
            maxAmount: "",
            payments: "",
            paymentsList: []
        }
    },
    created() {
        this.getLoans()
    },
    methods: {
        getLoans() {
            axios.get('/api/loans')
                .then(response => {
                    this.data = response.data
                    console.log(this.data)
                })
                .catch(err => console.log(err))
        },
        logOut() {
            axios
                .post('/api/logout')
                .then(response => {
                    // console.log('signed out!!!')
                    window.location.replace('/web/index.html');
                })
        },
        createLoan() {
            this.paymentsList = this.payments.split(',').map(function (item) {
                return parseInt(item, 10);
            })
            console.log(this.paymentsList);
            console.log(this.maxAmount);
            Swal.fire({
                icon: 'warning',
                text: "you are about to create a new type of Loan: " + this.name + ", amount:" + this.maxAmount + ", payments:" + this.paymentsList,
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: 'Create',
                denyButtonText: `Cancel`,
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/manager/loans', { name: this.name, maxAmount: this.maxAmount, payments: this.paymentsList })
                        .then(response => {
                            Swal.fire({
                                icon: 'success',
                                title: 'Created',
                                timer: 3000,
                            })
                            this.getLoans()
                        })
                        .catch(error => Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: error.response.data,
                            timer: 3000,
                        }))
                } else if (result.isDenied) {
                    Swal.fire('Creation cancelled', '', 'info')
                }
            })
        }
    }
})
app.mount('#vueApp')
// axios.post('/api/manager/loans', { name: this.name, maxAmmount: this.maxAmount, payments: this.paymentsList })
//     .then(response => {
//         console.log('Loan created!!!');
//         this.getLoans()
//     })
//     .catch(err => console.log(err))