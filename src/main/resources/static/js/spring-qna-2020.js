$("#answer-form button").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("Answer button clicked");
    const queryString = $("#answer-form").serialize();
    console.log("query", queryString);

    const url = $("#answer-form").attr("action");
    console.log("url", url);

    $.ajax({
        type: "post",
        url: url,
        data: queryString,
        dataType: "json",
        error: onError,
        success: onSuccess
    })
}

function onError() {
    console.error("error");
}

function onSuccess(data, status) {
    console.log(data);
    console.log(status);
}