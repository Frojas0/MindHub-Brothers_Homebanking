const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            data: [],
            accounts: [],
            loans: [],
            debitCards: [],
            creditCards: [],
            type: '',
            color: '',
            actualDate: ''
        }
    },
    created() {
        this.loadCards()
        this.actualDate = new Date();
        this.actualDate = this.actualDate.toISOString()
    },
    methods: {
        loadCards() {
            axios.get('/api/current/cards')
                .then(response => {
                    this.data = response.data
                    this.debitCards = this.data.filter(i => i.type === "DEBIT")
                    this.creditCards = this.data.filter(i => i.type === "CREDIT")
                    // console.log(this.data)
                    // console.log(this.creditCards);
                    // console.log(this.debitCards);
                })
                .catch(err => console.log(err))
        },
        logOut() {
            axios
                .post('/api/logout')
                .then(response => {
                    console.log('signed out!!!')
                    window.location.replace('/web/index.html');
                })
                .catch(err => console.log(err))
        },
        deleteCard(number) {
            axios
                .post('/api/current/card/delete', `number=${number}`)
                .then(response => {
                    console.log('Card deleted!!!');
                    this.loadCards()
                })
        }
    }
})
app.mount('#vueApp')
// this.accounts = this.data.accounts
// this.accounts.sort((a, b) => a.id - b.id)
// this.loans = this.data.loans
// this.loans.sort((a, b) => a.loanId - b.loanId)
// console.log(this.accounts);
// console.log(this.loans)