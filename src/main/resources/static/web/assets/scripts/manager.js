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
            axios.post('/api/manager/loans', { name: this.name, maxAmount: this.maxAmount, payments: this.paymentsList })
                .then(response => {
                    console.log('Loan created!!!');
                    this.getLoans()
                })
                .catch(err => console.log(err))
        }
    }
})
app.mount('#vueApp')
