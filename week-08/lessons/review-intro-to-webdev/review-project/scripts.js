function calculateSum() {
    let minimum_field = document.getElementById('minimum-input');
    let maximum_field = document.getElementById('maximum-input');

    inputs_valid = validateInputs();
    if (!inputs_valid) {
        return;
    }

    let min_value = parseInt(minimum_field.value);
    let max_value = parseInt(maximum_field.value);
    let difference_value = max_value - min_value;

    let sum = min_value;
    let values_string = "" + min_value;
    for (let i = 1; i <= difference_value; i++) {
        sum += min_value + i;
        values_string += (", " + (min_value + i));
    }

    let new_sum = document.createElement("div");
    new_sum.innerHTML = `<b>Sum:</b><br>${sum}<br><br><b>Values:</b><br>${values_string}`;
    

    let results = document.getElementById('results');
    results.innerHTML = "";
    results.appendChild(new_sum);
}


function validateInputs() {
    let minimum_field = document.getElementById('minimum-input');
    let maximum_field = document.getElementById('maximum-input');

    let alert = null;
    if (isNaN(minimum_field.value) || minimum_field.value.trim() === "") {
        alert = 'Minimum number is not a number.';
        minimum_field.value = "";
        minimum_field.focus();
        displayError(alert);
        return false;
    }

    if (isNaN(maximum_field.value) || maximum_field.value.trim() === "") {
        alert = 'Maximum number is not a number.';
        maximum_field.value = "";
        maximum_field.focus();
        displayError(alert);
        return false;
    }

    if (parseInt(minimum_field.value) > parseInt(maximum_field.value)) {
        alert = 'Minimum number is bigger than maximum number.'
        displayError(alert);
        return false;
    }

    return true;
}

function displayError(text) {
    let new_error = document.createElement("div");
    // let button = document.createElement("button");

    new_error.classList.add('alert', 'alert-trim', 'alert-dismissible', 'alert-danger');
    new_error.innerHTML = '\n\u2022 ' + text;

    let results = document.getElementById('results');
    results.innerHTML = "";
    results.appendChild(new_error);
}