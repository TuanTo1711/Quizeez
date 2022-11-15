const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server);
const PORT = 3000;

app.get('/', (req, res) => {
    res.end();
});

io.on('connection', (socket) => {
    console.log("A user connected");
    socket.on('message', (msg) => {
        io.emit('message', msg)
    })
})

server.listen(PORT, () => {
    console.log("Listening on port: " + PORT);
})