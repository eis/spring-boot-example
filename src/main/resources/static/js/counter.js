var checkFunction = function () {
    var first = $('#inputFirst').val();
    var second = $('#inputSecond').val();
    if (first.length > 0 && second.length > 0) {
        var dataObj = { int1: first, int2: second };
        $.ajax({
            url: "/counter/add",
            type: 'POST',
            dataType   : 'json',
            contentType: 'application/json',
            data : JSON.stringify(dataObj),
            context: document.body,
            success: function(data) {
                $('#result').text( "Result: " + data.value );
            },
            error: function(data) {
                $('#result').text( "Request failed" );
            }
        });
    }
}
$( document ).ready(function() {
    $("form").bind('submit', function (event) {
        checkFunction();
        event.preventDefault();
        return false;
    })
});
