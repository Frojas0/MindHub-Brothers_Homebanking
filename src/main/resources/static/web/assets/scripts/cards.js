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
            Swal.fire({
                icon: 'warning',
                title: 'Shure?',
                text: "you are about to delete card: " + number,
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: 'Delete',
                denyButtonText: `Cancel`,
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/current/card/delete', `number=${number}`)
                        .then(response => {
                            Swal.fire({
                                icon: 'success',
                                title: 'Deleted',
                                timer: 3000,
                            })
                            this.loadCards()
                        })
                        .catch(error => Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: error.response.data,
                            timer: 3000,
                        }))
                } else if (result.isDenied) {
                    Swal.fire('Delete cancelled', '', 'info')
                }
            })
        }
    }
})
app.mount('#vueApp')
// axios.post('/api/current/card/delete', `number=${number}`)
// .then(response => {
//     console.log('Card deleted!!!');
//     this.loadCards()
// })