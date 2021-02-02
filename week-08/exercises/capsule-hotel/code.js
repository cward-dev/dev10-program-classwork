const CAPSULE_COUNT = 100;
const capsules = [];

const bookGuestForm = document.getElementById("book-guest");
const checkOutGuestForm = document.getElementById("check-out");

function handleGuestFormSubmit(event) {
    // prevents default behavior of the form (keeps from refreshing)
    event.preventDefault();
    
    // get the elements
    const guestNameElement = document.getElementById("guest");
    const bookingCapsuleElement = document.getElementById("bookingCapsule");

    // get the values from the elements
    const guestName = guestNameElement.value;
    const bookingCapsuleElementValue = bookingCapsuleElement.value;

    const roomNumber = parseInt(bookingCapsuleElementValue);
    const roomIndex = roomNumber - 1;

    // TODO validate user data

    const capsule = capsules[roomIndex];
    capsule.guestName = guestName;

    renderCapsules();

    // reset the form
    guestNameElement.value = '';
    bookingCapsuleElement.value = '';
}

function handleCheckOutGuestFormSubmit(event) {
    event.preventDefault();
    
    // get the lements
    const checkOutGuestElement = document.getElementById("checkOutCapsule");

    // get the values from the elements
    const checkOutGuestElementValue = checkOutGuestElement.value;

    const roomNumber = parseInt(checkOutGuestElementValue);
    const roomIndex = roomNumber - 1;

    // TODO validate user data

    const capsule = capsules[roomIndex];
    capsule.guestName = null;

    renderCapsules();

    // reset the form
    checkOutGuestElement.value = '';
}

function renderCapsules() {
    const capsuleContainer = document.getElementById("capsules");
    let html = "";
    for (const capsule of capsules) {
        html += `<div>
        <span id="capsuleLabel${capsule.roomNumber}" class="badge badge-pill ${capsule.guestName ? 'badge-danger' : 'badge-success'}">Capsule #${capsule.roomNumber}</span>
        &nbsp;<span id="guest${capsule.roomNumber}">${capsule.guestName ? capsule.guestName : 'Unoccupied'}</span>
        </div>`
    }
    capsuleContainer.innerHTML = html;
}

function init() {

    // initialize capsules array
    for (let i = 0; i < CAPSULE_COUNT; i++) {
        capsules[i] = {
            roomNumber: i + 1,
            guestName: null // empty room
        }
    }

    renderCapsules();

    bookGuestForm.addEventListener("submit", handleGuestFormSubmit);
    checkOutGuestForm.addEventListener("submit", handleCheckOutGuestFormSubmit);

}

init();