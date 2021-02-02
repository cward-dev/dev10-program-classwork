(function () {

    const state = {};

    // <NEW CODE>
    function onCoinButtonClick() {
        // Update what we can in the single coin component.
        updateCoinComponent(this);
        // Update across all components.
        updateAllComponents();
    }

    function updateCoinComponent(btn) {

        const componentId = btn.getAttribute("data-id");
        // 1. Use the id to look up state.
        const componentState = state[componentId];

        // 2. Update component state.
        componentState.clicks++;
        componentState.coinTotal += componentState.amountPerClick;
        componentState.contributedPanel.innerText = `Contributed: $${componentState.coinTotal.toFixed(2)}`;

        // 3. If we've reached the max clicks, prevent more clicks and show a new message.
        if (componentState.clicks >= componentState.maxClicks) {
            btn.setAttribute("disabled", "disabled");
            const valueAsCents = (componentState.amountPerClick * 100).toFixed(0);
            btn.innerText = `${valueAsCents}\u00A2 - clicked ${componentState.maxClicks} times.`;
        }
    }

    function updateAllComponents() {
        // 4. No need to track a total if we can calculate it on-demand.
        let total = 0.0;
        for (let key in state) {
            total += state[key].coinTotal;
        }

        document.getElementById("total").innerText = `Total: $${total.toFixed(2)}`;

        // 5. Update child state that relies on the total.
        for (let key in state) {
            const percent = (state[key].coinTotal / total * 100).toFixed(0);
            state[key].percentPanel.innerText = `${percent}% of total`;
        }
    }
    // </NEW CODE>

    function registerCoinComponent(id, amountPerClick, maxClicks) {

        const div = document.getElementById(id);
        const button = div.querySelector("button");
        const childDivs = div.querySelectorAll("div");

        button.setAttribute("data-id", id);

        state[id] = {
            contributedPanel: childDivs[0],
            percentPanel: childDivs[1],
            maxClicks,
            amountPerClick,
            clicks: 0,
            coinTotal: 0.0
        };

        button.addEventListener("click", onCoinButtonClick);
    }

    registerCoinComponent("coinQuarter", 0.25, 10);
    registerCoinComponent("coinDime", 0.1, 10);
    registerCoinComponent("coinNickel", 0.05, 10);
    registerCoinComponent("coinPenny", 0.01, 10);

})();