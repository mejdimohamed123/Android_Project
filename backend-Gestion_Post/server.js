const express = require('express');
const dbConnect = require('./dbConnect');
const app = express();
app.use(express.json());
app.use('/images', express.static('public/images'), function(req, res, next) {
    console.log('Request to /images received');
    next();
});
const postRoute = require('./routes/postsRoute');


app.use('/api/posts/', postRoute);

const port = 5004
app.get('/', (req, res) => res.send('Hello'))
app.listen(port, () => console.log(`app listening on port ${port}`))