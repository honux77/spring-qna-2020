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
        <a class="answer-edit" href="questions/{{question.id}}/answers/{{id}}">수정</a> <a class="answer-delete" href="questions/{{question.id}}/answers/{{id}}">삭제</a>
    </div> */
function appendAnswer(answer) {
    let parent = document.getElementById('answers')
    let template = document.querySelector('#new-answer');
    let answerNode = template.content.cloneNode(true);
    let allp = answerNode.querySelectorAll('p');
    let alla = answerNode.querySelectorAll('a');
    allp[0].textContent = answer.authorName + " " + answer.formattedCreateDate;
    allp[1].textContent = answer.contentsForRead;
    alla[0].setAttribute('href', `/api/questions/${answer.questionId}/answers/${answer.id}`);
    alla[1].setAttribute('href', `/api/questions/${answer.questionId}/answers/${answer.id}`);
    parent.appendChild(answerNode);
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
    } catch (err) {
        console.error('error:', err);
    }
}