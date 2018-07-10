function dropDownFunction() {
    $('#myDropdown').toggle("show");

    $(document).mouseup(function(e) {
        let container = $("#myDropdown");

        // if the target of the click isn't the container nor a descendant of the container
        if (!container.is(e.target) && container.has(e.target).length === 0)
        {
            container.fadeOut();
        }
    });
}

function logInFunction(){
    $('#secondDropdown').toggle('secondShow');
    $(document).mouseup(function(e) {
        let container = $("#secondDropdown");

        // if the target of the click isn't the container nor a descendant of the container
        if (!container.is(e.target) && container.has(e.target).length === 0)
        {
            container.fadeOut();
        }
    });
}
function scrollup(){
    $('.')
}