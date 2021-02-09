function calculate() {
    var check = parseFloat(document.getElementById("check").value);
    var percent = parseFloat(document.getElementById("percent").value);
    percent = percent / 100.0;

    document.querySelector("form").setAttribute("style", "display: none;");

    document.getElementById("spnCheck").innerText = "$" + check.toFixed(2);
    document.getElementById("spnPercent").innerText = (percent * 100).toFixed(0) + "%";
    document.getElementById("spnTip").innerText = "$" + (check * percent).toFixed(2);

    document.getElementById("results").removeAttribute("style");

}