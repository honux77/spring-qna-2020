$("#answer-form button").click(fetchAnswer);

async function fetchAnswer(e) {
    e.preventDefault();
    const form = new FormData(document.getElementById('answer-form'));

    const url = $("#answer-form").attr("action");
    console.log(form);
    let answer = "";
    try {
        let response = await fetch(url, {
            method: "POST",
            body: form
        });
        answer = await response.json();
        console.log(answer);
    } catch (err) {
        console.error('fetch failed', err);
    }
    append(answer);
}

function append(answer) {
    let parent = document.getElementById('answers')
    let template = document.querySelector('#new-answer');
    let answerNode = template.content.cloneNode(true);
    let ps = answerNode.querySelectorAll('p');
    ps[0].textContent = answer.authorName + " " + answer.formattedCreateDate;
    ps[1].textContent = answer.contentsForRead;
    parent.appendChild(answerNode);
    //clear input
    $('#answer-textarea').val('');
}
