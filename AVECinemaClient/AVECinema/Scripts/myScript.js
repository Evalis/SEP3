function makeReservationForSchedule(scheduleID, moviePrice) {
    window.location = "/Reservation/MakeReservation?scheduleID=" + scheduleID + "&moviePrice=" + moviePrice;
}

function hasClass(el, className) {
    if (el.classList)
        return el.classList.contains(className);
    return !!el.className.match(new RegExp('(\\s|^)' + className + '(\\s|$)'));
}

function addClass(el, className) {
    if (el.classList)
        el.classList.add(className)
    else if (!hasClass(el, className))
        el.className += " " + className;
}

function removeClass(el, className) {
    if (el.classList)
        el.classList.remove(className)
    else if (hasClass(el, className)) {
        var reg = new RegExp('(\\s|^)' + className + '(\\s|$)');
        el.className = el.className.replace(reg, ' ');
    }
}

function clickOnSeat(seatNumber) {
    var el = document.getElementById(seatNumber);
    var selectColor = "seat-selected";
    var seats = JSON.parse(document.getElementById("seats").value);
    var numberOfTicket = document.getElementById("numberOfTicket").value;
    var moviePrice = document.getElementById("moviePrice").innerHTML;
   
        
    if (hasClass(el, selectColor)) {
        removeClass(el, selectColor);
        var index = seats.indexOf(seatNumber);
        if (index > -1) {
            seats.splice(index, 1);
            numberOfTicket--;
           
            
        }
       
    }
    else {
        addClass(el, selectColor);
        seats.push(seatNumber);
        numberOfTicket++;

       
     
    }
    document.getElementById("seats").value = JSON.stringify(seats);
    document.getElementById("numberOfTicket").value = numberOfTicket;
    document.getElementById("totalPrice").value = moviePrice * numberOfTicket;
   
  
}
