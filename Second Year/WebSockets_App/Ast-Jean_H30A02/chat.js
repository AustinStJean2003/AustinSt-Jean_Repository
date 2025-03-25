const express = require("express");
const app = express();
const http = require("http");
const fs = require("fs");
const path = require("path");
const server = http.createServer(app);
const { Server } = require("socket.io");

const io = new Server(server);
let userArray = [];
let allUsers = [];
let userColor = [];
let gameArr = [];
let gameplayerArr = [];
const PORT = 4000;
let distinctColor;
const ERRDIR = "public/errorpages";
class User {
  constructor(socket, username, color) {
    this.socket = socket;
    this.username = username;
    this.color = color;
  }
}

app.use(express.static(__dirname + "/public"));

app.get("/", (req, res) => {
  res.sendFile(__dirname + "/public/chat.html");
});

app.use("/*", (req, res) => {
  res.status(404);
  res.sendFile(path.join(__dirname, ERRDIR, "400.html"));
});

io.on("connection", (socket) => {
  let roomno = 0;
  socket.emit("displayusers", allUsers);
  socket.on("chat message", (msg) => {
    if (msg === "!!users") {
      let currentUser = getUser(socket.id);
      let formettedArray = allUsers.join(" ");
      socket.emit("chat message", {
        msg: `Users in this chat room: ${formettedArray}`,
        color: `${currentUser.color}`,
        username: `${currentUser.username}`,
      });
    } else if (msg == "!!play") {
      let currentUser = getUser(socket.id);
      io.emit("publicgame", {
        msg: `Play RPS against `,
        username: `${currentUser.username}`,
        color: `${currentUser.color}`,
      });
      socket.emit("display game");
    } else {
      //commands
      let secondPart = msg.substring(msg.indexOf(" ") + 1);
      let firstPart = msg.slice(msg.indexOf("!") + 1, msg.indexOf(" "));
      let command = msg.substring(msg.indexOf("!"), msg.indexOf(" "));
      let receiverUser = getUsernameByUsername(firstPart);

      if (firstPart == "cancel") {
        socket.emit("cancel game");
      } else if (secondPart == "accept") {
        if (allUsers.includes(firstPart)) {
          socket.emit("start game");
          socket.to(receiverUser.socket).emit("start game");
        } else {
          socket.emit("invalidcommand", {
            msg: `Invalid command`,
          });
        }
      } else if (firstPart == "play") {
        if (allUsers.includes(secondPart)) {
          let currentUser = getUser(socket.id);
          let receiverUser = getUsernameByUsername(secondPart);
          socket.to(receiverUser.socket).emit("publicgame", {
            msg: `Play RPS against `,
            username: `${currentUser.username}`,
            color: `${currentUser.color}`,
          });
          socket.emit("display game");
        } else {
          socket.emit("invalidcommand", {
            msg: `${secondPart} is not a currently in the chat, try again`,
          });
        }
      } else if (command == "!invite") {
        if (allUsers.includes(secondPart)) {
          let currentUser = getUser(socket.id);
          let receiverUser = getUsernameByUsername(secondPart);
          socket.to(receiverUser.socket).emit("private invite", {
            msg: `Invite sent by ${currentUser.username}: Type !join ${currentUser.username} to join private room`,
            username: `${currentUser.username}`,
            color: `${currentUser.color}`,
          });
          socket.emit("chat message", {
            msg: `Invite sent to ${receiverUser.username}`,
            username: `${currentUser.username}`,
            color: `${currentUser.color}`,
          });
          socket.join("room-" + roomno++);
          roomno = roomno + 1;
        }
      } else if (firstPart == "join") {
        if (allUsers.includes(secondPart)) {
          socket.join("room-" + roomno);
          io.in("room-" + roomno).emit(
            "connectToRoom",
            "You are in room no. " + roomno
          );
        } else {
          socket.emit("invalidcommand", {
            msg: `Invalid command`,
          });
        }
      } else if (msg.charAt(0) == "!") {
        if (allUsers.includes(firstPart)) {
          let currentUser = getUser(socket.id);
          let receiverUser = getUsernameByUsername(firstPart);
          msg = msg.substring(msg.indexOf(" "));
          socket.to(receiverUser.socket).emit("private message", {
            msg: `Whisper from ${currentUser.username}: ${msg}`,
            username: `${currentUser.username}`,
            color: `${currentUser.color}`,
          });
          socket.emit("chat message", {
            msg: `Whisper sent to ${receiverUser.username}`,
            username: `${currentUser.username}`,
            color: `${currentUser.color}`,
          });
          writeToFile("privatemessage", receiverUser.username, msg.length);
        } else {
          socket.emit("invalidcommand", {
            msg: "That is an invalid command",
          });
        }
      } else if (
        msg.charAt(0) == "!" &&
        (command != "!play" || command != "!join" || command != "!invite")
      ) {
        socket.emit("invalidcommand", {
          msg: "That is an invalid command, try again",
        });
      } else {
        let currentUser = getUser(socket.id);
        io.emit("chat message", {
          msg: `${currentUser.username}: ${msg}`,
          color: `${currentUser.color}`,
          username: `${currentUser.username}`,
        });
        writeToFile("chatmessage", currentUser.username, msg.length);
      }
    }
  });

  socket.on("private message", (msg) => {
    if (msg.charAt(0) == "!") {
      let secondPart = msg.substring(msg.indexOf(" ") + 1);
      let firstPart = msg.slice(msg.indexOf("!") + 1, msg.indexOf(" "));
      let command = msg.substring(msg.indexOf("!"), msg.indexOf(" "));
      if (firstPart == "leave") {
        if (allUsers.includes(secondPart)) {
          let receiverUser = getUsernameByUsername(secondPart);
          socket.leave("room-" + roomno);
          socket.emit("leaveRoom");
          socket.to(receiverUser.socket).emit("leaveRoom");
        } else {
          socket.emit("invalidcommand", {
            msg: "That is an invalid command, try again",
          });
        }
      }
    } else {
      let currentUser = getUser(socket.id);
      io.emit("private message", {
        msg: `${currentUser.username}: ${msg}`,
        color: `${currentUser.color}`,
        username: `${currentUser.username}`,
      });
      writeToFile("privateroomchat", currentUser.username, msg.length);
    }
  });

  socket.on("disconnect", () => {
    let currentUser = getUser(socket.id);
    writeToFile("userleave", currentUser.username, "");
    io.emit("goodbye", {
      msg: `${currentUser.username} has left the chat`,
    });
    if (currentUser.username != undefined) {
      let arrIndex = allUsers.indexOf(currentUser.username);
      delete allUsers[arrIndex];
    }
    socket.emit("displayusers", allUsers);
  });

  socket.on("setusername", (username) => {
    let newUser = new User(socket.id, username, color());
    userArray.push(newUser);
    if (allUsers.includes(username)) {
      socket.emit("user taken", { msg: `Taken` });
    } else {
      allUsers.push(username);
      io.emit("displayusers", allUsers);
      let currentUser = getUser(socket.id);
      socket.emit("setusername", {
        username: currentUser.username,
        color: currentUser.color,
      });
      socket.broadcast.emit("chat message", {
        msg: `${currentUser.username} joined the chat`,
        color: `${currentUser.color}`,
        username: `${currentUser.username}`,
      });
      socket.emit("hello", {
        msg: `Welcome ${currentUser.username}`,
        users: allUsers,
      });
      writeToFile("joinchat", username, "");
    }
  });
  socket.on("gameplay", (data) => {
    let currentUser = getUser(socket.id);
    gameArr.push(data);
    gameplayerArr.push(currentUser.username);
    if (gameArr.length >= 3) {
      while (gameArr.length > 0) {
        gameArr.pop();
        gameplayerArr.pop();
      }
      gameArr.push(data);
      gameplayerArr.push(currentUser.username);
    }
    let winner = "";
    if (gameArr.length == 2) {
      let opponent = getUsernameByUsername(gameplayerArr[0]);
      if (gameArr[0] == gameArr[1]) {
        socket.emit("gameresults", {
          str: `Game is a tie better luck next time<br>`,
        });
        socket.to(opponent.socket).emit("gameresults", {
          str: `Game is a tie better luck next time<br>`,
        });
        winner = "Tie game";
        socket.emit("close game");
        socket.to(opponent.socket).emit("close game");
      } else if (gameArr[0] == "rock") {
        if (gameArr[1] == "paper") {
          socket.emit("gameresults", {
            str: `${gameplayerArr[1]} Won with paper`,
          });
          socket.to(opponent.socket).emit("gameresults", {
            str: `${gameplayerArr[1]} Won with paper`,
          });
          winner = gameplayerArr[1];
          socket.emit("close game");
          socket.to(opponent.socket).emit("close game");
        } else {
          socket.emit("gameresults", {
            str: `${gameplayerArr[0]} Won with rock`,
          });
          socket.to(opponent.socket).emit("gameresults", {
            str: `${gameplayerArr[0]} Won with rock`,
          });
          winner = gameplayerArr[0];
          socket.emit("close game");
          socket.to(opponent.socket).emit("close game");
        }
      } else if (gameArr[0] == "scissors") {
        if (gameArr[1] == "rock") {
          socket.emit("gameresults", {
            str: `${gameplayerArr[1]} Won with rock`,
          });
          socket.to(opponent.socket).emit("gameresults", {
            str: `${gameplayerArr[1]} Won with rock`,
          });
          winner = gameplayerArr[1];
          socket.emit("close game");
          socket.to(opponent.socket).emit("close game");
        } else {
          socket.emit("gameresults", {
            str: `${gameplayerArr[0]} Won with scissors`,
          });
          socket.to(opponent.socket).emit("gameresults", {
            str: `${gameplayerArr[0]} Won with scissors`,
          });
          winner = gameplayerArr[0];
          socket.emit("close game");
          socket.to(opponent.socket).emit("close game");
        }
      } else if (gameArr[0] == "paper") {
        if (gameArr[1] == "scissors") {
          socket.emit("gameresults", {
            str: `${gameplayerArr[1]} Won with scissors`,
          });
          socket.to(opponent.socket).emit("gameresults", {
            str: `${gameplayerArr[1]} Won with scissors`,
          });
          winner = gameplayerArr[1];
          socket.emit("close game");
          socket.to(opponent.socket).emit("close game");
        } else {
          socket.emit("gameresults", {
            str: `${gameplayerArr[0]} Won with paper`,
          });
          socket.to(opponent.socket).emit("gameresults", {
            str: `${gameplayerArr[0]} Won with paper`,
          });
          winner = gameplayerArr[0];
          socket.emit("close game");
          socket.to(opponent.socket).emit("close game");
        }
      }
    }
    if (winner.length != 0) {
      writeToFile("gamemessage", winner, "");
    }
  });
});

function color() {
  distinctColor =
    "#" + (Math.random() * 0xfffff * 1000000).toString(16).slice(0, 6);
  while (userColor.includes(distinctColor)) {
    distinctColor =
      "#" + (Math.random() * 0xfffff * 1000000).toString(16).slice(0, 6);
  }
  userColor.push(distinctColor);
  return distinctColor;
}

function getUser(id) {
  let currentUser;
  userArray.map((el) => {
    if (el.socket == id) {
      currentUser = el;
    }
  });
  return currentUser;
}

function getUsernameByUsername(uname) {
  let thisUser;
  userArray.map((el) => {
    if (el.username === uname) {
      thisUser = el;
    }
  });
  return thisUser;
}

server.listen(PORT, () => {
  console.log("Listening on port " + PORT);
});

function writeToFile(type, username, length) {
  let date = new Date();
  let [month, day, year, hour, minutes, seconds] = [
    date.getMonth(),
    date.getDate(),
    date.getFullYear(),
    date.getHours(),
    date.getMinutes(),
    date.getSeconds(),
  ];
  let logStr = "";
  if (type == "chatmessage") {
    logStr = `${year}/${month}/${day} ${hour}/${minutes}/${seconds} :: Chat Message :: Length:${length} \n`;
  } else if (type == "privatemessage") {
    logStr = `${year}/${month}/${day} ${hour}/${minutes}/${seconds} :: Private Message :: ${username} received, Length: ${
      length - 1
    } \n`;
  } else if (type == "gamemessage") {
    logStr = `${year}/${month}/${day} ${hour}/${minutes}/${seconds} :: Game :: Winner: ${username} \n`;
  } else if (type == "joinchat") {
    logStr = `${year}/${month}/${day} ${hour}/${minutes}/${seconds} :: User join :: ${username} joined the chat \n`;
  } else if (type == "privateroomchat") {
    logStr = `${year}/${month}/${day} ${hour}/${minutes}/${seconds} :: Private room Message :: ${username} received, Length: ${
      length - 1
    } \n`;
  } else if(type == "userleave") {
    logStr = `${year}/${month}/${day} ${hour}/${minutes}/${seconds} :: User left :: ${username} left the chat\n`;
  }

  fs.appendFile("./public/logs/dateevents.log", `${logStr}`, (err) => {
    if (err) {
      console.log(err);
    }
  });
}