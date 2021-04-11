function validateSearch() {
    var searchValue = document.getElementById("searchValue").value;
    var categoryValue = document.getElementById("categoryValue").value;
    var amount = document.getElementById("amount").value;

    if (searchValue === "" && categoryValue === "") {
        alert("Please input name or choose category to search!");
        return false;
    }
    if (amount === "") {
        alert("Please input amount to search!!!");
        return false;
    }
    if (!validateDate())
        return false;
    return true;
}

function validateDate() {
    var rentalDate = document.getElementById("rentalDate").value;
    var returnDate = document.getElementById("returnDate").value;
    if (rentalDate === "") {
        alert("Please input rental date to search!");
        return false;
    }
    if (returnDate === "") {
        alert("Please input return date to search!");
        return false;
    }
    var rentalTime = Date.parse(rentalDate);
    var returnTime = Date.parse(returnDate);
    var currentDate = new Date();
    var currentTime = currentDate.getTime();
    if (((currentTime - 86400000) - rentalTime) > 0) {
        alert("Rental Date must be today or greater than today");
        return false;
    }
    if (((currentTime - 86400000) - returnTime) > 0) {
        alert("Return Date must be today or greater than today");
        return false;
    }
    if ((rentalTime - returnTime) > 0) {
        alert("Rental Date must equal or greater Return Date");
        return false;
    }
    return true;
}

function validateSearchOrder() {
    var txtSearchValue = document.getElementById("txtSearchValue").value;
    var txtFromDate = document.getElementById("txtFromDate").value;
    var txtToDate = document.getElementById("txtToDate").value;
    
    if (txtSearchValue === "" && (txtFromDate === "" || txtToDate === "")) {
        alert("Please input name or choose date to search!");
        return false;
    }
    return true;
}