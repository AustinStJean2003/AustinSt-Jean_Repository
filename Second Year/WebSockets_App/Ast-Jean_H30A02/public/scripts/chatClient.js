let socket = io();
const $$ = (val) => document.querySelector(val);
const $$All = (val) => document.querySelectorAll(val);
let messages = document.getElementById("messages");
let privatemessages = document.getElementById("privatemessages");
let form = document.getElementById("form");
let input = document.getElementById("input");
let privateinput = document.getElementById("privateinput")
let userform = document.querySelector("#sendusername");
let currentUsername = '';

userform.addEventListener("click", (e) => {
  if ($$("#username").value == "") {
    $$("#errdiv").innerHTML = "*Please enter a user name*";
    e.preventDefault();
  } else if ($$("#username").value.match(/\s/)) {
    $$("#errdiv").innerHTML = "*Name cannot have spaces*";
    e.preventDefault();
  } else {
    $$("#usertitle").style.display = "block";
    $$("#userdiv").style.display = "block";
    $$("#userformdiv").style.display = "none";
    $$("#chatArea").style.display = "block";
    $$("#headerDiv").style.display = "block";
    currentUsername = $$("#username").value;
    socket.emit("setusername", $$("#username").value);
    e.preventDefault();
  }
  socket.on("user taken", (data) => {
    $$("#usertitle").style.display = "none";
    $$("#userdiv").style.display = "none";
    $$("#userformdiv").style.display = "block";
    $$("#chatArea").style.display = "none";
    $$("#headerDiv").style.display = "none";
    $$("#errdiv").innerHTML = "*Name is taken please choose another one*";
    e.preventDefault();
  });
});

rockBtn.addEventListener("click", () => {
  $$("#answerDiv").innerHTML = "You have selected Rock<br>Waiting for opponent";
  $$("#rockBtn").disabled = true;
  $$("#paperBtn").disabled = true;
  $$("#scissorsBtn").disabled = true;
  socket.emit("gameplay", "rock")
})

paperBtn.addEventListener("click", () => {
  $$("#answerDiv").innerHTML = "You have selected Paper<br>Waiting for opponent";
  $$("#rockBtn").disabled = true;
  $$("#paperBtn").disabled = true;
  $$("#scissorsBtn").disabled = true;
  socket.emit("gameplay", "paper")
})

scissorsBtn.addEventListener("click", () => {
  $$("#answerDiv").innerHTML = "You have selected Scissors<br>Waiting for opponent";
  $$("#rockBtn").disabled = true;
  $$("#paperBtn").disabled = true;
  $$("#scissorsBtn").disabled = true;
  socket.emit("gameplay", "scissors")
})

socket.on("gameresults", (data) => {
  $$("#answerDiv").innerHTML = "";
  $$("#winMessage").innerHTML = `${data.str} Thank you for playing`;
})

socket.on("close game", () => {
  var timeleft = 4;
  setInterval(function () {
    timeleft--;
    if(timeleft <= 0) {
      if(timeleft == 0 ) {
        $$("#answerDiv").innerHTML= ""
        $$("#waitDiv").innerHTML = ""
        $$("#winMessage").innerHTML = ""
        $$("#gameDiv").style.display = "none"
        $$("#gameContainer").style.display = "none";
        $$("#chatArea").style.width = "100%";
        $$("#chatArea").style.marginLeft = "0";
      }
    }
  }, 1000)
}) 

form.addEventListener("submit", function (e) {
  e.preventDefault();
  if (input.value) {
    socket.emit("chat message", input.value);
    input.value = "";
  }
});

$$("#privateform").addEventListener("submit", function (e) {
  e.preventDefault();
  if (privateinput.value) {
    socket.emit("private message", privateinput.value);
    privateinput.value = "";
  }
});

socket.on("privategame", (data) => {
  let msgDisplay = document.createElement("li");
  msgDisplay.innerHTML = `${data.msg} ${data.username}? Type "!${data.username} accept" to play`;
})

socket.on("publicgame", (data) => {
  let msgDisplay = document.createElement("li");
  msgDisplay.innerHTML = `${data.msg} ${data.username}? Type "!${data.username} accept" to play`;
  messages.appendChild(msgDisplay);
});

socket.on("display game", () => {
  $$("#gameContainer").style.display = "block";
  $$("#gameDiv").style.display  = "none"
  $$("#chatArea").style.width = "73.5%";
  $$("#chatArea").style.marginLeft = "21%";
  $$("#waitDiv").style.display = "block"
  $$("#waitDiv").innerHTML = "Waiting for player";
});

socket.on("start game", () => {
  $$("#gameContainer").style.display = "block";
  $$("#countdowntimer").style.display = "block";
  $$("#countdowntimer").style.position = "absolute";
  $$("#chatArea").style.width = "73.5%";
  $$("#chatArea").style.marginLeft = "21%";
  $$("#rockBtn").disabled = false;
  $$("#paperBtn").disabled = false;
  $$("#scissorsBtn").disabled = false;
  $$("#waitDiv").style.display = "none";
  var timeleft = 4;
  setInterval(function () {
    timeleft--;

    if(timeleft > 0 && timeleft != 4)
    $$("#countdowntimer").textContent = timeleft;
    if (timeleft <= 0) {
      if(timeleft == 0) {
        $$("#countdowntimer").style.position = "unset";
        $$("#countdowntimer").style.display = "none";
        $$("#gameDiv").style.display = "block";
      }
    }
  }, 1000);
});

socket.on("chat message", function (data) {
  let item = document.createElement("li");
  item.textContent = data.msg;
  if(data.username === currentUsername) {
    item.id = 'client';
  }
  messages.appendChild(item);
  item.classList.add(`${data.username}`);
  $$All(`.${data.username}`).forEach((el) => {
    el.style.textShadow = `2px 2px 2px ${data.color}, 2px 2px 2px ${data.color}`;
  });
  window.scrollTo(0, document.body.scrollHeight);
});

socket.on("private invite", (data) => {
  let item = document.createElement("li");
  item.textContent = data.msg;
  messages.appendChild(item);
  item.classList.add(`${data.username}`);
  $$All(`.${data.username}`).forEach((el) => {
    el.style.textShadow = `2px 2px 2px ${data.color}, 2px 2px 2px ${data.color}`;
  });
  window.scrollTo(0, document.body.scrollHeight);
})

socket.on("private message", function (data) {
  let item = document.createElement("li");
  item.textContent = data.msg;
  if(data.username === currentUsername) {
    item.id = 'client';
  }
  privatemessages.appendChild(item);
  item.classList.add(`${data.username}`);
  $$All(`.${data.username}`).forEach((el) => {
    el.style.textShadow = `2px 2px 2px ${data.color}, 2px 2px 2px ${data.color}`;
  });
  window.scrollTo(0, document.body.scrollHeight);
});

socket.on("hello", (data) => {
  let msgDisplay = document.createElement("li");
  msgDisplay.textContent = data.msg;
  messages.appendChild(msgDisplay);
});
socket.on("goodbye", (data) => {
  let msgDisplay = document.createElement("li");
  msgDisplay.textContent = data.msg;
  messages.appendChild(msgDisplay);
});
socket.on("displayusers", (users) => {
  $$("#userdiv").innerHTML = ``;
  Array.from(users).map((name) => {
    $$("#userdiv").innerHTML += `${name} <br>`;
  });
});
socket.on("private message", (data) => {
  let msgDisplay = document.createElement("li");
  msgDisplay.textContent = data.msg;
  msgDisplay.classList.add(`${data.username}`);
  messages.appendChild(msgDisplay);
});
socket.on("cancel game", () => {
  $$("#gameContainer").style.display = "none";
  $$("#chatArea").style.width = "100%";
  $$("#chatArea").style.marginLeft = "0";
  $$("#waitDiv").innerHTML = "";
});

socket.on("private room", (data) => {
  let msgDisplay = document.createElement("li");
  msgDisplay.textContent = data.msg;
  messages.appendChild(msgDisplay);
})

socket.on("connectToRoom", data => {
  $$("#privateChatArea").style.display = "block";
  $$("#chatArea").style.display = "none";
})

socket.on("leaveRoom", () => {
  $$("#privateChatArea").style.display = "none";
  $$("#chatArea").style.display = "block";
})

socket.on("invalidcommand", (data) => {
  let msgDisplay = document.createElement("li");
  msgDisplay.textContent = data.msg;
  messages.appendChild(msgDisplay);
})