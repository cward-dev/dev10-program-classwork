var row = 8;
var col = 1;

function inBounds(r, c) {
    return (r === 0 && (c === 2 || c === 3)) // goal
        || (r > 0 && r <= 8 && c >= 0 && c <= 5); // everything else
}

function checkWin() {
    if (row === 0 && (col === 2 || col === 3)) {
        for (let i = 0; i < btns.length; i++) {
            btns[i].setAttribute("disabled", "disabled");
        }
        const inputs = document.querySelectorAll("input[type=text]");
        for (let i = 0; i < inputs.length; i++) {
            inputs[i].className = "goal";
            inputs[i].value = "O";
        }
    }
}

function move(evt) {
    let r = row;
    let c = col;
    switch (evt.target.value) {
        case "up":
            r -= 1;
            break;
        case "left":
            c -= 1;
            break;
        case "right":
            c += 1;
            break;
        case "down":
            r += 1;
            break;
    }

    if (inBounds(r, c)) {
        document.getElementById(`cell_${row}_${col}`).value = "";
        document.getElementById(`cell_${r}_${c}`).value = "O";
        row = r;
        col = c;
    }

    checkWin();
}

const btns = document.querySelectorAll("button[name=move]");
for (let i = 0; i < btns.length; i++) {
    btns[i].addEventListener("click", move);
}