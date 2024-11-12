const { connect } = require('http2')
const mongoose = require('mongoose')


mongoose.connect('mongodb://127.0.0.1:27017/pdm', { useNewUrlParser: true, useUnifiedTopology: true })
// mongoose.connect('mongodb+srv://ayoub1:ayoub1@pdm.vwwjbxe.mongodb.net/', { useNewUrlParser: true, useUnifiedTopology: true })

const connection = mongoose.connection
connection.on('error', err => console.log(err))
connection.on('connected', () => console.log('connection successful'))