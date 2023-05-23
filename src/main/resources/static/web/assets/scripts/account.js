const { createApp } = Vue
const id = new URLSearchParams(location.search).get('id')
// console.log(id);

const app = createApp({
    data() {
        return {
            data: [],
            transactions: [],
            startDate: "",
            endDate: "",
            accountNumber: ""
        }
    },
    created() {
        axios.get(`/api/accounts/${id}`)
            .then(response => {
                this.data = response.data
                this.transactions = this.data.transactions
                this.transactions.sort((a, b) => b.id - a.id)
                this.accountNumber = this.data.number
                console.log(this.accountNumber);
                // console.log(this.data);
                // console.log(this.transactions);
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
        downloadPDF() {
            Swal.fire({
                icon: 'warning',
                title: 'Shure?',
                text: "you will download a pdf with the history of your transactions",
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: 'Download',
                denyButtonText: `Cancel`,
            }).then((result) => {
                if (this.startDate.length == 0 || this.endDate.length == 0) {
                    axios.get('/api/transactions', `accountNumber=${this.accountNumber}&start=all&end=all`)
                    axios({
                        method: 'GET',
                        url: '/api/transactions',
                        responseType: 'blob',
                        params: {
                            accountNumber: `${this.accountNumber}`,
                            start: 'all',
                            end: 'all'
                        }
                    })
                        .then(response => {
                            const url = window.URL.createObjectURL(new Blob([response.data]))
                            const link = document.createElement('a')
                            link.href = url
                            link.setAttribute('download', 'transaction-history.pdf')
                            document.body.appendChild(link)
                            link.click()
                            window.URL.revokeObjectURL(url)
                        })
                        .catch(error => {
                            console.error(error)
                        })
                } else {
                    window.location.replace(`/api/transactions?accountNumber=${this.accountNumber}&start=${this.startDate}&end=${this.endDate}`)
                }
            })
        }
    }
})
app.mount('#vueApp')