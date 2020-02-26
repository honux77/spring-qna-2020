$("#answer-form button").click(async (e) => {
    e.preventDefault();
    const form = new FormData(document.getElementById('answer-form'));
    const url = $("#answer-form").attr("action");
    try {
        let response = await fetch(url, {
            method: "POST",
            body: form
        });
        const answer = await response.json();
        console.log(answer);
        appendAnswer(answer);
    } catch (err) {
        console.error('fetch failed', err);
    }
});

/*    <div class="col-md-9 col-md-offset-2">
        <p>{{authorName}} {{formattedCreateDate}}</p>
        <p class="border-bottom" style="margin-bottom: 2rem">{{{contentsForRead}}}</p>
        <a class="answer-edit" href="questions/{{questionId}}/answers/{{id}}">수정</a> <a class="answer-delete" href="questions/{{questionId}}/answers/{{id}}">삭제</a>
    </div> */
function appendAnswer(answer) {
    let htmlString = `<div class="answer col-md-9 col-md-offset-2">
        <p>${answer.authorName} ${answer.formattedCreateDate}  <a class="answer-delete" href="/api/questions/${answer.questionId}/answers/${answer.id}">삭제</a></p>         
        <p class="border-bottom" style="margin-bottom: 1rem">${answer.contentsForRead}</p>
    </div>`;
    let div = document.createElement("div");
    div.innerHTML = htmlString;
    let parent = document.getElementById('answers')
    parent.insertAdjacentElement('beforeEnd', div.firstChild);
    //increase num count
    let n = parseInt($('#num-answer').text()) + 1;
    $('#num-answer').text(n);
    //clear input
    $('#answer-textarea').val('');
}


$('#answers').delegate( '.answer-delete', 'click', deleteAnswer);
async function deleteAnswer(e) {
    e.preventDefault();
    const el = $(this);
    const url = el.attr("href");
    console.log(url);
    try {
        let res = await fetch(url, {
            method: "delete"
        });
        console.log("result:", await res.json());
        el.closest('.answer').remove();
        let n = parseInt($('#num-answer').text()) - 1;
        $('#num-answer').text(n);
    } catch (err) {
        console.error('error:', err);
    }
}