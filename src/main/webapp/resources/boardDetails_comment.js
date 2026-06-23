/**
 * board/detail.jsp => boardDetail_comment.js
 */

console.log("boardDetail_comment.js in");
console.log(bno);

document.getElementById('cmtAddBtn').addEventListener('click', ()=>{
    const cmtWriter = document.getElementById('cmtWriter');
    const cmtText = document.getElementById('cmtText');
	
	if(cmtWriter.value.trim() == "" || cmtText.value.trim() == ""){
		alert('댓글의 내용을 입력해주세요.');
		return;
	}
	
    // 댓글 객체 생성
    const cmtData={
        bno: bno,
        writer : cmtWriter.value,
        contents : cmtText.value
    }

    console.log(cmtData);

    cmtText.value = '';
    cmtText.focus();

    postCommentToServer(cmtData).then(result =>{
        console.log(result);
        if(result == '1'){
            alert('댓글등록 성공!!!');
        }else{
            alert('댓글등록 실패!');
        }

        // 댓글 리스트를 띄우기
        printCommentList(bno);
        
    })
});

// 데이터 전송 함수 (post)
async function postCommentToServer(cmtData) {
    try {
        // post 데이터를 전송할 때 
        // url, headers(content-type), body(cmtData)
        const url = "/cmt/post";
        const config = {
            method: 'post',
            headers:{
                'content-type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
        }

        // 전송
        const response = await fetch(url, config);
        const result = await response.text();  //isOk
        return result;

    } catch (error) {
        console.log(error);
    }
}

// 리스트 호출
async function getCommentListFromServer(bno) {
    try {
        const response = await fetch(`/cmt/list?bno=${bno}`);
        const result = await response.json(); // 댓글 리스트 [{},{},{}]
        return result;
    } catch (error) {
        console.log(error);
    }
}

function printCommentList(bno){
    getCommentListFromServer(bno).then(result =>{
        console.log(result);
        const div = document.getElementById('commentLine');
        let str='';
        if(result.length > 0){
            //댓글이 있는 경우
            for(let cmt of result){
                str+=`<div>`;
                str+=`<div>${cmt.cno} / ${cmt.writer} (${cmt.regdate})</div>`;
                str+=`<div>`;
                str+=`<input type="text" value="${cmt.contents}">`;
                str+=`<button type="button">❗</button>`;
                str+=`<button type="button">❌</button>`;
                str+=`</div></div>`;
            }

            div.innerHTML = str;
        }else{
            // 댓글이 없는 경우
            div.innerHTML = `<div>댓글이 없습니다.</div>`;
        }
    })
}
