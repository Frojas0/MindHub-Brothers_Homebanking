const { createApp } = Vue;
const url = 'http://localhost:8080/api/clients/1';
const app = createApp({
    data() {
        return {
            data: [],
            accounts: [],
            loans: [],
            debitCards: [],
            creditCards: []
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
                this.debitCards = this.data.cards.filter(i => i.type === "DEBIT")
                this.creditCards = this.data.cards.filter(i => i.type === "CREDIT")
                // console.log(this.data)
                // console.log(this.accounts);
                // console.log(this.loans)
                console.log(this.creditCards);
                console.log(this.debitCards);
            })
            .catch(err => console.log(err))
    }
})
app.mount('#vueApp')