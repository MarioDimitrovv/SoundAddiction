/**
 * 
 */
var dbError = "Sorry, an error occured while accessing the database. Please try again later!";
var noSuchProduct = "Sorry, but you've made a request for a product that does not exist.";

function buySong(songId) {
    var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 500){
            alert(dbError);
			//showAlert(dbError, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 400){
			//showAlert(noSuchProduct, 3);
            alert(noSuchProduct);
			return;
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
			return;
		}
		else if (this.readyState == 4 && this.status == 200) {
			var result = (this.responseText);
			if(result === 'true'){
				//showAlert("Successfully bought song.", 1);
                alert("Successfully bought song.");
				return;
			}
			else if (result === 'false'){
				//showAlert("Sorry, but you already own this song!", 2);
				alert("Sorry, but you already own this product!.");
				return;
			}
		}
	};
	
	
	xhttp.open("POST", "auth/buySong", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("songId="+ songId);
}

function rateProduct(songId, rating) {
	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 500){
			alert(dbError);
			return;
		}
		else if(this.readyState == 4 && this.status == 400){
			alert(noSuchProduct);
			return;
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
			return;
		}
		else if (this.readyState == 4 && this.status == 200) {
			
			alert("Product successfully rated with rating = "+rating);
			location.reload();
			return;
		}
	};
	
	
	xhttp.open("POST", "auth/rateSong", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("songId="+ songId +"&rating=" + rating);
}

function redirectToUnauthorizedPage(){
	window.location.href = "/unauthorized";
}


function addComment(commentContent, songId) {
	//clear the textarea
	document.getElementById('commentContent').value = '';
	//add the review only if it is between 3 and 480 characters
	if(commentContent.length < 3 || commentContent.length > 480){
		if(commentContent.length > 480){
			alert("The size of your comment exceeds the given limit of 480 characters. Please keep it short and simple.");
		}else if (commentContent.length < 3 ){
			alert("Your comment is too short. Please add some more flavor.");
		}
		return;
	}
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 500){
			showAlert(dbError, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 400){
			showAlert(noSuchProduct, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
			return;
		}
		else if (this.readyState == 4 && this.status == 200) {
			showAlert("Added comment:\n"+commentContent+"\n For product with id = "+songId, 1);
			window.location.reload();
			return;
		}
	};
	
	xhttp.open("POST", "auth/addComment", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("songId="+ songId +"&commentContent=" + commentContent);
}

