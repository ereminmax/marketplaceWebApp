function validateLogin() {
  var login = document.forms["loginForm"]["login"].value;
  var password = document.forms["loginForm"]["password"].value;

  if (login == "" || password == "") {
    alert("Fill out everything");
    return false;
  }
}
function validateBid() {
  var bidAmount = document.forms["bidForm"]["bidAmount"].value;

  if (bidAmount == "") {
    alert("Bid amount value is empty");
    return false;
  }
}
function validateRegistration() {
  var fullName = document.forms["registrationForm"]["fullName"].value;
  var billingAddress = document.forms["registrationForm"]["billingAddress"].value;
  var login = document.forms["registrationForm"]["login"].value;
  var password = document.forms["registrationForm"]["password"].value;

  if (fullName == "" || billingAddress == "" || login == "" || password == "") {
    alert("Fill out all the fields");
    return false;
  }
}
function validateSearch() {
  var keyWord = document.forms["searchForm"]["keyWord"].value;

  if (keyWord == "") {
    alert("Enter the key word please");
    return false;
  }
}
function validateEdit() {
  var title = document.forms["editForm"]["title"].value;
  var description = document.forms["editForm"]["description"].value;
  var startPrice = document.forms["editForm"]["startPrice"].value;
  var timeLeft = document.forms["editForm"]["timeLeft"].value;
  var bidIncrement = document.forms["editForm"]["bidIncrement"].value;
  var startDate = document.forms["editForm"]["startDate"].value;
  //var isBuyItNow =  = document.getElementById("buyItNow").checked;
  //var isBuyItNow = document.querySelector('.messageCheckbox:checked').value;

  if (title == "" || description == "" || startPrice == "" || startDate == "") {
    alert("Fill this fields: title, description, startPrice, startDate");
    return false;
  }

  /*if (isBuyItNow != "1" && (bidIncrement == "" || timeLeft == "")) {
    alert("You have to specify bid increment and time left for Auction type of a deal");
    return false;
  }*/
}
